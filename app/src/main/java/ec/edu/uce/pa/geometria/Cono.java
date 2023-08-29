package ec.edu.uce.pa.geometria;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;

import ec.edu.uce.pa.utilidades.Funciones;
import ec.edu.uce.pa.R;

public class Cono {

    private final FloatBuffer bufferVertices;
    private final FloatBuffer bufferColores;
    private static final int byteFlotante = 4;
    private final static int comPorVertice = 3, comPorColores = 4;
    private final int franjas;
    public int nVerticesGeneral, nColoresG;
    public float[] coloresAU, aux1;

    private final float[] matrizProyeccion;
    private final float[] matrizVista;
    private final float[] matrizModelo;
    private final Context contexto;

    public float[] coloresAU(int n) {
        this.coloresAU = new float[n * 4];
        for (int i = 0; i < this.coloresAU.length; i++) {
            coloresAU[i] = 0.5f;
            coloresAU[i + 1] =0.5f;
            coloresAU[i + 2] =0.5f;
            coloresAU[i + 3] = 1.0f;
            i += 3;
        }
        return coloresAU;
    }

    public Cono(int franjas, float radius, float height, Context contexto, float[] matrizProyeccion,
                float[] matrizVista, float[] matrizModelo) {
        this.franjas = franjas;
        this.matrizVista = matrizVista;
        this.matrizModelo = matrizModelo;
        this.matrizProyeccion = matrizProyeccion;
        this.contexto = contexto;

        float[] vertices = new float[(franjas + 2) * 3];
        int index = 0;

        // Vértice del vértice central
        vertices[index++] = 0.0f;
        vertices[index++] = 0.0f;
        vertices[index++] = height;

        // Vértices en la base del cono
        for (int i = 0; i <= franjas; ++i) {
            float theta = (float) (2.0 * Math.PI * (float) i / franjas);

            float x = (float) (radius * Math.cos(theta));
            float y = (float) (radius * Math.sin(theta));
            float z = 0.0f;

            vertices[index++] = x;
            vertices[index++] = y;
            vertices[index++] = z;
        }
        nVerticesGeneral = vertices.length;

        aux1 = this.coloresAU(this.nVerticesGeneral / comPorVertice);
        this.nColoresG = aux1.length;

        bufferVertices=Funciones.generarFloatBuffer(vertices);
        bufferColores= Funciones.generarFloatBuffer(aux1);
    }

    public void dibujar(GLES20 gl) {
        int vertexShader = 0;
        int fragmentShader = 0;

        String sourceVS = null;
        String sourceFS = null;

        sourceVS = Funciones.leerArchivo(R.raw.mvp_color_vertex_shader, contexto);
        vertexShader = Funciones.crearShader(GLES20.GL_VERTEX_SHADER, sourceVS, gl);

        sourceFS = Funciones.leerArchivo(R.raw.color_fragment_shader, contexto);
        fragmentShader = Funciones.crearShader(GLES20.GL_FRAGMENT_SHADER, sourceFS, gl);

        int programa = Funciones.crearPrograma(vertexShader, fragmentShader, gl);
        GLES20.glUseProgram(programa);

        bufferVertices.position(0);
        int idVertexShader = GLES20.glGetAttribLocation(programa, "posVertexShader");
        GLES20.glVertexAttribPointer(idVertexShader,
                comPorVertice,
                GLES20.GL_FLOAT,
                false,
                0,
                bufferVertices);
        GLES20.glEnableVertexAttribArray(idVertexShader);

        int idFragmentShader = GLES20.glGetAttribLocation(programa,"colorVertex");

        //gl.glUniform4f(idFragmentShader,1.0f,0.6f,0.5f,1); // Color uniforme
        GLES20.glVertexAttribPointer(idFragmentShader, comPorColores, GLES20.GL_FLOAT,false,
                0, bufferColores);
        GLES20.glEnableVertexAttribArray(idFragmentShader);

        // Obtener la ubicación del uniforme "matrizProjection, matrizView, matrizModel" en el shader
        int idPosMatrizProy = GLES20.glGetUniformLocation(programa, "matrizProjection");
        GLES20.glUniformMatrix4fv(idPosMatrizProy, 1, false, matrizProyeccion, 0);
        int idPosMatrizView = GLES20.glGetUniformLocation(programa, "matrizView");
        GLES20.glUniformMatrix4fv(idPosMatrizView, 1, false, matrizVista, 0);
        int idPosMatrizModel = GLES20.glGetUniformLocation(programa, "matrizModel");
        GLES20.glUniformMatrix4fv(idPosMatrizModel, 1, false, matrizModelo, 0);

        GLES20.glFrontFace(GLES20.GL_CW); //horario
        //gl.glDrawArrays(gl.GL_TRIANGLE_STRIP,0,franjas + 2);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0, franjas + 2);
        GLES20.glFrontFace(GLES20.GL_CCW); //antihorario
        GLES20.glDisableVertexAttribArray(idVertexShader);
        GLES20.glDisableVertexAttribArray(idFragmentShader);

        Funciones.liberarShader(programa, vertexShader, fragmentShader);
    }
}
