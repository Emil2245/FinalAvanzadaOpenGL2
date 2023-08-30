package ec.edu.uce.pa.geometria;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import ec.edu.uce.pa.utilidades.Funciones;
import ec.edu.uce.pa.R;

public class CuadradoTextura {

    private final FloatBuffer bufferVertices;
    private final ByteBuffer bufferIndice;
    private final FloatBuffer bufferTexturas;
    private static final int byteFlotante = 4;
    private static final int compPorVertice = 2;
    private static final int compPorText = 2;
    private final static int STRIDE = (compPorVertice + compPorText) * byteFlotante;
    private int[] arrayTexturas;

    private final float[] matrizProyeccion;
    private final float[] matrizVista;
    private final float[] matrizModelo;

    private final Context contexto;

    public CuadradoTextura(Context contexto, float[] matrizProyeccion, float[] matrizVista,
                           float[] matrizModelo) {
        this.matrizProyeccion = matrizProyeccion;
        this.matrizVista = matrizVista;
        this.matrizModelo = matrizModelo;
        this.contexto = contexto;

        float[] vertices = {
                -1, -1,
                1, -1,
                -1, 1,
                1, 1
        };

        float[] texturas = {
                0.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f
        };

        byte[] indices = {
                0, 2, 3,
                0, 3, 1
        };

        bufferVertices = Funciones.generarFloatBuffer(vertices);
        bufferTexturas = Funciones.generarFloatBuffer(texturas);
        bufferIndice = Funciones.generarByteBuffer(indices);
    }

    public void dibujar(GLES20 gl, int indiceTextura) {
        int vertexShader = 0;
        int fragmentShader = 0;

        String sourceVS = Funciones.leerArchivo(R.raw.mvp_textura_vertex_shader, contexto);
        vertexShader = Funciones.crearShader(GLES20.GL_VERTEX_SHADER, sourceVS, gl);

        String sourceFS = Funciones.leerArchivo(R.raw.textura_fragment_shader, contexto);
        fragmentShader = Funciones.crearShader(GLES20.GL_FRAGMENT_SHADER, sourceFS, gl);

        int programa = Funciones.crearPrograma(vertexShader, fragmentShader, gl);
        GLES20.glUseProgram(programa);

        bufferVertices.position(0);
        int idVertexShader = GLES20.glGetAttribLocation(programa, "posVertexShader");
        GLES20.glVertexAttribPointer(idVertexShader,
                compPorVertice,
                GLES20.GL_FLOAT,
                false,
                0,
                bufferVertices);
        GLES20.glEnableVertexAttribArray(idVertexShader);

        bufferTexturas.position(0);
        int idFragmentShader = GLES20.glGetAttribLocation(programa, "texturaVertex");
        GLES20.glVertexAttribPointer(idFragmentShader,
                compPorText,
                GLES20.GL_FLOAT,
                false,
                0,
                bufferTexturas);
        GLES20.glEnableVertexAttribArray(idFragmentShader);

        int idPosMatrizProy = GLES20.glGetUniformLocation(programa, "matrizProjection");
        GLES20.glUniformMatrix4fv(idPosMatrizProy, 1, false, matrizProyeccion, 0);

        int idPosMatrizView = GLES20.glGetUniformLocation(programa, "matrizView");
        GLES20.glUniformMatrix4fv(idPosMatrizView, 1, false, matrizVista, 0);

        int idPosMatrizModel = GLES20.glGetUniformLocation(programa, "matrizModel");
        GLES20.glUniformMatrix4fv(idPosMatrizModel, 1, false, matrizModelo, 0);

        GLES20.glFrontFace(GLES20.GL_CW);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,arrayTexturas[indiceTextura]);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_BYTE, bufferIndice);


        GLES20.glFrontFace(GLES20.GL_CCW);

        GLES20.glDisableVertexAttribArray(idVertexShader);
        GLES20.glDisableVertexAttribArray(idFragmentShader);

        Funciones.liberarShader(programa, vertexShader, fragmentShader);
    }
    public void habilitarTexturasFondo(GLES20 gl,int indice){
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        arrayTexturas = new int[indice];
        GLES20.glGenTextures(indice, arrayTexturas, 0);
    }

    public void cargarImagenesTexturaFondo(GLES20 gl, Context contexto, int idImagen, int indice){
        Bitmap bitmap = BitmapFactory.decodeResource(contexto.getResources(), idImagen);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, arrayTexturas[indice]);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        bitmap.recycle();
    }
}

