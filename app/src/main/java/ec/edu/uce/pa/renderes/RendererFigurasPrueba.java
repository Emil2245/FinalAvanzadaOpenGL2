package ec.edu.uce.pa.renderes;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.Stack;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ec.edu.uce.pa.R;
import ec.edu.uce.pa.geometria.Astros;
import ec.edu.uce.pa.geometria.Cilindro;
import ec.edu.uce.pa.geometria.CilindroTextura;
import ec.edu.uce.pa.geometria.Cono;
import ec.edu.uce.pa.geometria.ConoTextura;
import ec.edu.uce.pa.geometria.Cuadrado;
import ec.edu.uce.pa.geometria.CuadradoColor;
import ec.edu.uce.pa.geometria.CuadradoTextura;
import ec.edu.uce.pa.geometria.CuboUnicolor;
import ec.edu.uce.pa.geometria.Dona;
import ec.edu.uce.pa.geometria.EsferaColor;
import ec.edu.uce.pa.geometria.Piramide;
import ec.edu.uce.pa.geometria.PrismaCuadrangular;
import ec.edu.uce.pa.geometria.PrismaTriangular;
import ec.edu.uce.pa.utilidades.Funciones;

public class RendererFigurasPrueba implements GLSurfaceView.Renderer {
    private Cilindro cilindro;
    private CilindroTextura cilindroTextura;
    private Cono cono;
    private ConoTextura conoTextura;
    private CuboUnicolor cubo;
    private CuboUnicolor cubo2;
    private Cuadrado cuadrado;
    private CuadradoColor cuadrado2;
    private CuadradoTextura cuadradoTextura;
    private Dona dona;
    private EsferaColor elipsoide, esfera;
    private Piramide piramide;
    private PrismaCuadrangular prismaCuadrangular;
    private PrismaTriangular prismaTriangular;
    private Astros astro;

    private Context context;
    private int[] arrayTextura = new int[3];
    private Stack<float[]> matrizModeloStack = new Stack<>();
    private float[] matrizProyeccion = new float[16];
    private float[] matrizModelo = new float[16];
    private float[] matrizVista = new float[16];
    private float[] matrizTemp = new float[16];
    private float relacionAspecto, rotacion = 0.0f;
    private float[] colorRojo = {1.0f, 0.0f, 0.0f, 1.0f};
    private float[] color = {0.5f, 0.0f, 0.2f, 1.0f};
    private float[] colorManecillas = {0.1f, 0.1f, 0.1f, 1.0f};

    public RendererFigurasPrueba(Context contexto) {
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
        cubo = new CuboUnicolor(context, matrizProyeccion, matrizVista, matrizModelo,colorRojo);
        cubo2 = new CuboUnicolor(context, matrizProyeccion, matrizVista, matrizModelo,colorManecillas);
        cuadrado = new Cuadrado(contexto, matrizProyeccion, matrizVista, matrizModelo);
        cuadrado2 = new CuadradoColor(contexto, matrizProyeccion, matrizVista, matrizModelo);
        cuadradoTextura = new CuadradoTextura(contexto, matrizProyeccion, matrizVista, matrizModelo);
        prismaCuadrangular = new PrismaCuadrangular(context, matrizProyeccion, matrizVista, matrizModelo);
        prismaTriangular = new PrismaTriangular(context, matrizProyeccion, matrizVista, matrizModelo);
        dona = new Dona(20, 20, 1.0f, 0.7f, contexto, matrizProyeccion, matrizVista, matrizModelo, color);
        astro = new Astros(50, 50, 1.0f, 1.0f,contexto,matrizProyeccion,matrizVista,matrizModelo);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        gl.glEnable(gl.GL_DEPTH_TEST);
        arrayTextura = Funciones.habilitarTexturas(new GLES20(),1);
        Funciones.cargarImagenesTexturas(new GLES20(),context, R.drawable.mercury,0,arrayTextura);

        cuadradoTextura.habilitarTexturasFondo(new GLES20(), 3);
        cuadradoTextura.cargarImagenesTexturaFondo(new GLES20(), context, R.drawable.iso, 0);
        cuadradoTextura.cargarImagenesTexturaFondo(new GLES20(), context, R.drawable.textura, 1);
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

        transladar(-3, -5, -8);
//        transladar(0, -9, -14);
//        rotar(0, 1, 0, (float) (60+8*Math.cos(rotacion/20)));
        rotar(0, 1, 0, (float) (60));

        //piso
        pushMatrix();
        transladar(1, 0, 0);
        rotar(1, 0, 0, 90);
        escalar(7, 5, 1);
        cuadradoTextura.dibujar(new GLES20(), 0);
        popMatrix();

        //pared lateral
        pushMatrix();
        transladar(8, 3, 0);
        rotar(0, 1, 0, 90);
        escalar(5, 3, 3);
        cuadrado.dibujar(new GLES20());
        popMatrix();

        pushMatrix();
        transladar(1, 3, -5);
        rotar(0, 1, 0, -0);
        escalar(7, 3, 3);
        cuadrado2.dibujar(new GLES20());
        popMatrix();

        pushMatrix();
        transladar(1, 3, -4.999f);
        rotar(0, 0, 1, 180);
        escalar(5, 2, 2);
        cuadradoTextura.dibujar(new GLES20(), 1);
        popMatrix();

        pushMatrix();
        escalar(4, 2, 3);
        cubo.dibujar(new GLES20());
        popMatrix();

        pushMatrix();
        transladar(0,2.5f,0);
        rotar(0,1,0,rotacion);
        astro.dibujar(new GLES20(),arrayTextura,0);
        popMatrix();


        pushMatrix();
        rotar(0,1,0,-90);
        transladar(0,3,-7);
        escalar(0.6f,0.6f,0.6f);
        //circulo del reloj
        pushMatrix();
        transladar(3,2.5f,0);
        escalar(1,1,0.05f);
        dona.dibujar(new GLES20());
        popMatrix();

        pushMatrix();
        transladar(2.3f,2.7f,0.01f);
        escalar(1.5f,0.2f,0.05f);
        cubo2.dibujar(new GLES20());
        popMatrix();

        pushMatrix();
        transladar(3f,3.3f,0.01f);
        rotar(0,0,1,90);
        escalar(1f,0.2f,0.05f);
        cubo2.dibujar(new GLES20());
        popMatrix();

        popMatrix();


        rotacion += 2.5f;
    }

    private void invocarFrustrum() {
        Matrix.frustumM(matrizProyeccion, 0, -relacionAspecto, relacionAspecto,
                -1, 1, 1, 80);

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
