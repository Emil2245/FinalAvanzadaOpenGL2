package ec.edu.uce.pa.geometria;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import ec.edu.uce.pa.R;
import ec.edu.uce.pa.utilidades.Funciones;

public class Dona {
    private final FloatBuffer bufferVertices;
    private final FloatBuffer bufferColores;
    private FloatBuffer bufferNormales;
    private final FloatBuffer bufferTexturas;

    private final static int byteFlotante = 4;
    private final static int comPorVertices = 3;
    private final static int compPorTexturas = 2;
    private final int franjas;
    private final int cortes;
    private int[] arrayTexturas;
    private final Context context;
    private final float[] mProyeccion;
    private final float[] mVista;
    private final float[] mModelo;
    private final float[] colorDona;

    private ByteBuffer bufferIndice;

    // Necesitamos las franjas y cortes que vamos a dibujar
    public Dona(int franjas, int cortes, float radioMayor, float radioMenor, Context context, float[] mProyeccion,
                float[] mVista, float[] mModelo, float[] colorDona) {
        this.context = context;
        this.mProyeccion = mProyeccion;
        this.mVista = mVista;
        this.mModelo = mModelo;
        this.franjas = franjas;
        this.cortes = cortes;
        this.colorDona = colorDona;

        float[] vertices;
        float[] colores;
        float[] normales;

        float[] texturas;

        int iVertice = 0;
        int iColor = 0;
        int iNormal = 0;

        int iTextura = 0;

        // Tamaño de los vertices
        vertices = new float[3 * ((cortes * 2 + 2) * franjas)];
        colores = new float[4 * ((cortes * 2 + 2) * franjas)];
        normales = new float[3 * ((cortes * 2 + 2) * franjas)];
        texturas = new float[2 * ((cortes * 2 + 2) * franjas)];

        int i, j;

        // Bucle para construir las franjas de la dona
        for (i = 0; i < franjas; i++) {
            float phi0 = (float) (2.0 * Math.PI * (float) i / franjas);
            float cosPhi0 = (float) Math.cos(phi0);
            float sinPhi0 = (float) Math.sin(phi0);

            float phi1 = (float) (2.0 * Math.PI * (float) (i + 1) / franjas);
            float cosPhi1 = (float) Math.cos(phi1);
            float sinPhi1 = (float) Math.sin(phi1);

            for (j = 0; j < cortes; j++) {
                float theta = (float) (2.0 * Math.PI * (float) j / cortes);
                float cosTheta = (float) Math.cos(theta);
                float sinTheta = (float) Math.sin(theta);

                // Punto en el círculo mayor (radioMayor)
                vertices[iVertice] = (radioMayor + radioMenor * cosPhi0) * cosTheta;
                vertices[iVertice + 1] = (radioMayor + radioMenor * cosPhi0) * sinTheta;
                vertices[iVertice + 2] = radioMenor * sinPhi0;

                // Punto en el círculo menor (radioMenor)
                vertices[iVertice + 3] = (radioMayor + radioMenor * cosPhi1) * cosTheta;
                vertices[iVertice + 4] = (radioMayor + radioMenor * cosPhi1) * sinTheta;
                vertices[iVertice + 5] = radioMenor * sinPhi1;

                // Coordenadas de textura
                texturas[iTextura] = (float) j / cortes;
                texturas[iTextura + 1] = (float) i / franjas;
                texturas[iTextura + 2] = (float) j / cortes;
                texturas[iTextura + 3] = (float) (i + 1) / franjas;

                iVertice += 2 * 3;
                iNormal += 2 * 3;
                iColor += 2 * 4;
                iTextura += 2 * 2;
            }
        }

        bufferVertices = Funciones.generarFloatBuffer(vertices);
        bufferColores = Funciones.generarFloatBuffer(colores);
        bufferTexturas = Funciones.generarFloatBuffer(texturas);
    }

    public void dibujar(GLES20 gl) {
        // Configuración Vertex Shader
        int vertexShader ;
        int fragmentShader ;
        String sourceVs ;
        String sourceFs ;

        sourceVs = Funciones.leerArchivo(R.raw.colr_vertex_shader, context);
        vertexShader = Funciones.crearShader(GLES20.GL_VERTEX_SHADER, sourceVs, gl);

        // Configuración Fragment Shader
        sourceFs = Funciones.leerArchivo(R.raw.color_dona_fragment_shader, context);
        fragmentShader = Funciones.crearShader(GLES20.GL_FRAGMENT_SHADER, sourceFs, gl);

        int programa = Funciones.crearPrograma(vertexShader, fragmentShader, gl);
        GLES20.glUseProgram(programa);

        int idVertexShader = GLES20.glGetAttribLocation(programa, "posVertexShader");
        GLES20.glVertexAttribPointer(idVertexShader,
                comPorVertices,
                GLES20.GL_FLOAT,
                false,
                0,
                bufferVertices);
        GLES20.glEnableVertexAttribArray(idVertexShader);

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

        int idColorUniform = GLES20.glGetUniformLocation(programa, "colorUniform");
        GLES20.glUniform4fv(idColorUniform, 1, colorDona, 0); // Establecer el color uniforme

        GLES20.glFrontFace(GLES20.GL_CW);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, franjas * cortes * 2);

        GLES20.glFrontFace(GLES20.GL_CCW);
        GLES20.glDisableVertexAttribArray(idVertexShader);

        GLES20.glUseProgram(0);
        Funciones.liberarShader(programa, vertexShader, fragmentShader);

    }

}
