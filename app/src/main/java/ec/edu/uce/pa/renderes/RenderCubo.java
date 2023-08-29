package ec.edu.uce.pa.renderes;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ec.edu.uce.pa.geometria.CuboMulticolor;
import ec.edu.uce.pa.geometria.PuntosAstro;

public class RenderCubo implements GLSurfaceView.Renderer {
    private CuboMulticolor cuboMulticolor;
    private Context context;

    private PuntosAstro puntosAstro;
    private float[] matrizProyeccion = new float[16];
    private float[] matrizModelo = new float[16];

    private float[] matrizVista = new float[16];

    private float[] matrizTemp = new float[16];
    private float relacionAspecto, rotacion = 0.0f;

    int[] arrayTextura = new int[10];
    public RenderCubo(Context contexto) {
        this.context = contexto;
        //circulo = new Circulo();
        cuboMulticolor = new CuboMulticolor(contexto,matrizProyeccion,matrizVista,matrizModelo);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        gl.glEnable(gl.GL_DEPTH_TEST);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int ancho, int alto) {
        gl.glViewport(0, 0, ancho, alto);
        relacionAspecto = (float) ancho / (float) alto;
        Matrix.frustumM(matrizProyeccion, 0, -relacionAspecto, relacionAspecto,
                -1, 1, 1, 800);

        Matrix.setLookAtM(matrizVista, 0, 0, 1f, -1,
                0, 0, 0,
                0, 1, 0);
        Matrix.multiplyMM(matrizTemp, 0, matrizProyeccion, 0, matrizVista, 0);
        System.arraycopy(matrizTemp, 0, matrizProyeccion, 0, matrizTemp.length);



    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);


        // sol
        Posicion0();

        translate(0,0,-5f);
        rotate(0,1,0,rotacion);

        cuboMulticolor.dibujar(new GLES20());


        rotacion += 0.8f;
    }
    private void Posicion0() {
        Matrix.setIdentityM(matrizModelo, 0);
        Matrix.multiplyMM(matrizTemp, 0, matrizProyeccion, 0, matrizModelo, 0);
        System.arraycopy(matrizTemp, 0, matrizProyeccion, 0, matrizTemp.length);
    }

    private void rotate(float x, float y, float z, float anguloRot){

        Matrix.rotateM(matrizModelo, 0, anguloRot, x, y, z);
    }
    private void scale(float x, float y, float z){

        Matrix.scaleM(matrizModelo,0,x,y,z);
    }

    private void translate(float x, float y, float z){

        Matrix.translateM(matrizModelo, 0, x, y, z);
    }
    private void translateProyeccion(float x, float y, float z){

        Matrix.translateM(matrizProyeccion, 0, x, y, z);
    }
    private void translateCamera(float x, float y, float z){

        Matrix.translateM(matrizVista, 0, x, y, z);

    }


}
