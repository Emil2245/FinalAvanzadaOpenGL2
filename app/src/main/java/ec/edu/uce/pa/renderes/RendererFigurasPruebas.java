package ec.edu.uce.pa.renderes;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.Stack;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ec.edu.uce.pa.R;
import ec.edu.uce.pa.geometria.Cilindro;
import ec.edu.uce.pa.geometria.CilindroTextura;
import ec.edu.uce.pa.geometria.Cono;
import ec.edu.uce.pa.geometria.ConoTextura;
import ec.edu.uce.pa.geometria.Cuadrado;
import ec.edu.uce.pa.geometria.CuboMulticolor;
import ec.edu.uce.pa.geometria.EsferaColor;
import ec.edu.uce.pa.geometria.Piramide;
import ec.edu.uce.pa.geometria.PrismaCuadrangular;
import ec.edu.uce.pa.geometria.PrismaTriangular;
import ec.edu.uce.pa.utilidades.Funciones;

public class RendererFigurasPruebas implements GLSurfaceView.Renderer {
    private Cilindro cilindro;
    private CilindroTextura cilindroTextura;
    private Cono cono;
    private ConoTextura conoTextura;
    private CuboMulticolor cuboMulticolor;
    private Cuadrado cuadrado;
    private EsferaColor elipsoide, esfera;
    private Piramide piramide;
    private PrismaCuadrangular prismaCuadrangular;
    private PrismaTriangular prismaTriangular;
    private Context context;
    private int[] arrayTextura = new int[1];
    private Stack<float[]> matrizModeloStack = new Stack <>();
    private float[] matrizProyeccion = new float[16];
    private float[] matrizModelo = new float[16];
    private float[] matrizVista = new float[16];
    private float[] matrizTemp = new float[16];
    private float relacionAspecto, rotacion = 0.0f;

    public RendererFigurasPruebas(Context contexto) {
        this.context = contexto;
        cilindro = new Cilindro(20, 20, 1, 2, contexto, matrizProyeccion,
                matrizVista, matrizModelo);
        cilindroTextura = new CilindroTextura(20, 20, 1, 2, contexto,
                matrizProyeccion, matrizVista, matrizModelo);
        cono = new Cono(20, 1, 2, contexto, matrizProyeccion, matrizVista, matrizModelo);
        conoTextura = new ConoTextura(10, 1, 2, contexto, matrizProyeccion, matrizVista,
                matrizModelo);
        piramide = new Piramide(context, matrizProyeccion, matrizVista, matrizModelo);
        elipsoide = new EsferaColor(20, 20, 1, 2.5f, contexto,
                matrizProyeccion, matrizVista, matrizModelo);
        esfera = new EsferaColor(20, 20, 1, 1, contexto,
                matrizProyeccion, matrizVista, matrizModelo);
        cuboMulticolor = new CuboMulticolor(context, matrizProyeccion, matrizVista, matrizModelo);
        cuadrado = new Cuadrado(contexto, matrizProyeccion, matrizVista, matrizModelo);
        prismaCuadrangular = new PrismaCuadrangular(context, matrizProyeccion, matrizVista, matrizModelo);
        prismaTriangular = new PrismaTriangular(context, matrizProyeccion, matrizVista, matrizModelo);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        gl.glEnable(gl.GL_DEPTH_TEST);
        arrayTextura= Funciones.habilitarTexturas(new GLES20(),1);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int ancho, int alto) {
        gl.glViewport(0, 0, ancho, alto);
        relacionAspecto = (float) ancho / (float) alto;
        invocarFrustrum();
        invocarMatrices();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        GLES20 gles20 = new GLES20();
        Matrix.setIdentityM(matrizModelo, 0);


        transladar(0, 0, -2);
        pushMatrix();
        rotar(1, 1, 1, rotacion);
        escalar(0.5f, 0.5f, 0.5f);
//        Funciones.cargarImagenesTexturas(new GLES20(),context,
//                R.drawable.callisto,0,arrayTextura);
        conoTextura.dibujar(gles20);
        popMatrix();

        pushMatrix();
        transladar(0, 2, 0);
        rotar(-1, 1, -1, rotacion);
        escalar(0.5f, 0.5f, 0.5f);
        cuboMulticolor.dibujar(gles20);
        popMatrix();
        pushMatrix();
        transladar(0, 4, 0);
        rotar(-1, 1, -1, rotacion);
        escalar(0.5f, 0.5f, 0.5f);
        piramide.dibujar(gles20);
        popMatrix();
        pushMatrix();
        transladar(0, -2, 0);
        rotar(-1, 1, -1, rotacion);
        cuadrado.dibujar(gles20);
        popMatrix();
        rotacion += 2.5f;
    }

    private void invocarFrustrum() {
        Matrix.frustumM(matrizProyeccion, 0, -relacionAspecto, relacionAspecto,
                -1, 1, 1, 30);

        Matrix.setLookAtM(matrizVista, 0,
                0, 0, 2,
                0, 0, 0,
                0, 1, 0);

        Matrix.multiplyMM(matrizTemp, 0, matrizProyeccion, 0, matrizVista, 0);
        System.arraycopy(matrizTemp, 0, matrizProyeccion, 0, matrizTemp.length);
    }

    private void invocarMatrices() {
        Matrix.setIdentityM(matrizModelo, 0);
        Matrix.multiplyMM(matrizTemp, 0, matrizProyeccion, 0, matrizModelo, 0);
        System.arraycopy(matrizTemp, 0, matrizProyeccion, 0, matrizTemp.length);
    }

    private void resetearMatrices() {
        invocarFrustrum();
        invocarMatrices();
        Matrix.setIdentityM(matrizModelo, 0);
    }
    private void pushMatrix() {
        float[] matrizCopia = new float[16];
        System.arraycopy(matrizModelo, 0, matrizCopia, 0, 16);
        matrizModeloStack.push(matrizCopia);
    }

    private void popMatrix() {
        if (!matrizModeloStack.isEmpty()) {
            float[] matrizCopia = matrizModeloStack.pop();
            System.arraycopy(matrizCopia, 0, matrizModelo, 0, 16);
        }
    }

    private void rotar(float x, float y, float z, float anguloRot) {
        Matrix.rotateM(matrizModelo, 0, anguloRot, x, y, z);
    }

    private void escalar(float x, float y, float z) {
        Matrix.scaleM(matrizModelo, 0, x, y, z);
    }

    private void transladar(float x, float y, float z) {
        Matrix.translateM(matrizModelo, 0, x, y, z);
    }

}
