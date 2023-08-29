package ec.edu.uce.pa.geometria;


import android.content.Context;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import ec.edu.uce.pa.R;
import ec.edu.uce.pa.utilidades.Funciones;


public class CuboUnicolor {
    private final FloatBuffer bufferVertices;
    private final FloatBuffer bufferColores;

    private final static int comPorVertices = 3;

    private final Context context;
    private final float[] mProyeccion;
    private final float[] mVista;
    private final float[] mModelo;

    private ByteBuffer bufferIndice;

    //Necesitamos las franjas y cortes que vamos a dibujar
    public CuboUnicolor(Context context, float[] mProyeccion, float[] mVista, float[] mModelo, float[] colorCubo) {
        this.context = context;
        this.mProyeccion = mProyeccion;
        this.mVista = mVista;
        this.mModelo = mModelo;


        float[] texturas;

        int iVertice = 0;
        int iColor = 0;
        int iNormal = 0;

        int iTextura = 0;

        //Tamaño de los vertices


        float[] vertices = {
                -0.5f, 0.5f, 0.5f, //0
                0.5f, 0.5f, 0.5f,  //1
                0.5f, -0.5f, 0.5f, //2
                -0.5f, -0.5f, 0.5f, //3
                -0.5f, 0.5f, 0.5f, //0
                0.5f, -0.5f, 0.5f, //2

                0.5f, 0.5f, -0.5f, //4
                -0.5f, 0.5f, -0.5f, //5
                -0.5f, -0.5f, -0.5f, //6
                0.5f, -0.5f, -0.5f, //7
                0.5f, 0.5f, -0.5f, //4
                -0.5f, -0.5f, -0.5f, //6


                -0.5f, 0.5f, -0.5f, //8
                0.5f, 0.5f, -0.5f, //9
                0.5f, 0.5f, 0.5f, //10
                -0.5f, 0.5f, 0.5f, //11
                -0.5f, 0.5f, -0.5f, //8
                0.5f, 0.5f, 0.5f, //10


                -0.5f, -0.5f, 0.5f, //12
                0.5f, -0.5f, 0.5f, //13
                0.5f, -0.5f, -0.5f,//14
                -0.5f, -0.5f, -0.5f,//15
                -0.5f, -0.5f, 0.5f, //12
                0.5f, -0.5f, -0.5f,//14


                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, -0.5f, -0.5f,


                -0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f,
                -0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,

        };
        float[] colores = new float[144];
        for (int i = 0; i < colores.length; i += 4) {
            colores[i] = colorCubo[0];
            colores[i + 1] = colorCubo[1];
            colores[i + 2] = colorCubo[2];
            colores[i + 3] = colorCubo[3];
        }

        bufferVertices = Funciones.generarFloatBuffer(vertices);
        bufferColores = Funciones.generarFloatBuffer(colores);
    }

    public void dibujar(GLES20 gl) {
//        Configuracion Vertex Shader
        int vertexShader = 0;
        int fragmentShader = 0;
        String sourceVs = null;
        String sourceFs = null;

        sourceVs = Funciones.leerArchivo(R.raw.colr_vertex_shader, context);
        vertexShader = Funciones.crearShader(GLES20.GL_VERTEX_SHADER, sourceVs, gl);

        //        Configuracion Fragment Shader
        sourceFs = Funciones.leerArchivo(R.raw.color_fragment_shader, context);
        fragmentShader = Funciones.crearShader(GLES20.GL_FRAGMENT_SHADER, sourceFs, gl);

        int programa = Funciones.crearPrograma(vertexShader, fragmentShader, gl);
        GLES20.glUseProgram(programa);

//        11. Lectura de parametros desde el renderer
        int idVertexShader = GLES20.glGetAttribLocation(programa, "posVertexShader");
        GLES20.glVertexAttribPointer(idVertexShader,
                comPorVertices,
                GLES20.GL_FLOAT,
                false,
                0,
                bufferVertices);
        GLES20.glEnableVertexAttribArray(idVertexShader);

//        12. Lectura de parametros desde el renderer Fragment Shader

        int idFragmentShader = GLES20.glGetAttribLocation(programa, "colorVertex");
        GLES20.glVertexAttribPointer(idFragmentShader,
                4,
                GLES20.GL_FLOAT,
                false,
                0,
                bufferColores);
        GLES20.glEnableVertexAttribArray(idFragmentShader);

        int idPosMatrixProy = GLES20.glGetUniformLocation(programa,
                "matrizProjection");
        GLES20.glUniformMatrix4fv(idPosMatrixProy, 1,
                false, mProyeccion, 0);

        int idPosMatrixview = GLES20.glGetUniformLocation(programa,
                "matrizView");
        GLES20.glUniformMatrix4fv(idPosMatrixview, 1,
                false, mVista, 0);

        int idPosMatrixModel = GLES20.glGetUniformLocation(programa,
                "matrizModel");
        GLES20.glUniformMatrix4fv(idPosMatrixModel, 1,
                false, mModelo, 0);


        GLES20.glFrontFace(GLES20.GL_CW);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 36);

        GLES20.glFrontFace(GLES20.GL_CCW);
        GLES20.glDisableVertexAttribArray(idVertexShader);
        GLES20.glDisableVertexAttribArray(idFragmentShader);

        Funciones.liberarShader(programa, vertexShader, fragmentShader);

    }


}