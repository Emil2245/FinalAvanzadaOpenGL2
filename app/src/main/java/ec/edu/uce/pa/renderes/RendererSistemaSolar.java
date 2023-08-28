package ec.edu.uce.pa.renderes;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ec.edu.uce.pa.R;
import ec.edu.uce.pa.geometria.Estrellas;
import ec.edu.uce.pa.geometria.PuntosAstro;
import ec.edu.uce.pa.utilidades.Funciones;
import ec.edu.uce.pa.geometria.Astros;

public class RendererSistemaSolar implements GLSurfaceView.Renderer {
    private Astros astro;
    private Estrellas estrellas;
    private Context context;

    private PuntosAstro puntosAstro;
    private float[] matrizProyeccion = new float[16];
    private float[] matrizModelo = new float[16];

    private float[] matrizVista = new float[16];

    private float[] matrizTemp = new float[16];
    private float relacionAspecto, rotacion = 0.0f;

    int[] arrayTextura = new int[10];
    public RendererSistemaSolar(Context contexto) {
        this.context = contexto;
        //circulo = new Circulo();
        astro = new Astros(50, 50, 1.0f, 1.0f,contexto,matrizProyeccion,matrizVista,matrizModelo);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        gl.glEnable(gl.GL_DEPTH_TEST);
        GLES20.glGenTextures(2,arrayTextura,0);
        estrellas = new Estrellas();
        puntosAstro = new PuntosAstro();
        arrayTextura = Funciones.habilitarTexturas(new GLES20(),10);
        Funciones.cargarImagenesTexturas(new GLES20(),context, R.drawable.sun,0,arrayTextura);
        Funciones.cargarImagenesTexturas(new GLES20(),context, R.drawable.mercury,1,arrayTextura);
        Funciones.cargarImagenesTexturas(new GLES20(),context, R.drawable.venus,2,arrayTextura);
        Funciones.cargarImagenesTexturas(new GLES20(),context, R.drawable.tierra,3,arrayTextura);
        Funciones.cargarImagenesTexturas(new GLES20(),context, R.drawable.moon,4,arrayTextura);
        Funciones.cargarImagenesTexturas(new GLES20(),context, R.drawable.mars,5,arrayTextura);
        Funciones.cargarImagenesTexturas(new GLES20(),context, R.drawable.jupiter,6,arrayTextura);
        Funciones.cargarImagenesTexturas(new GLES20(),context, R.drawable.saturn,7,arrayTextura);
        Funciones.cargarImagenesTexturas(new GLES20(),context, R.drawable.uranus,8,arrayTextura);
        Funciones.cargarImagenesTexturas(new GLES20(),context, R.drawable.neptune,9,arrayTextura);


    }

    @Override
    public void onSurfaceChanged(GL10 gl, int ancho, int alto) {
        gl.glViewport(0, 0, ancho, alto);
        relacionAspecto = (float) ancho / (float) alto;
        Matrix.frustumM(matrizProyeccion, 0, -relacionAspecto, relacionAspecto,
                -1, 1, 1, 800);

        Matrix.setLookAtM(matrizVista, 0, 0, 15f, -1,
                0, 0, 0,
                0, 3, 0);
        Matrix.multiplyMM(matrizTemp, 0, matrizProyeccion, 0, matrizVista, 0);
        System.arraycopy(matrizTemp, 0, matrizProyeccion, 0, matrizTemp.length);



    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);


        // sol
        Posicion0();

        translate(0,0,-50f);
        rotate(0,1,0,rotacion);
        scale(8f,8f,8f);
        astro.dibujar(new GLES20(),arrayTextura,0);


        Posicion0();
        translate(0,0,-50f);
        translate((float)((Math.sin(rotacion/3))*10),0,(float)Math.cos(rotacion/3)*10);
        rotate(0,1,0,rotacion);
        astro.dibujar(new GLES20(),arrayTextura,1);
        rotate(1,0,0,90);
        puntosAstro.dibujar(new GLES20());

        Posicion0();
        translate(0,0,-50f);
        translate((float)((Math.sin(rotacion/5.5))*15),0,(float)Math.cos(rotacion/5.5)*15);
        rotate(0,1,0,rotacion);
        scale(1.2f, 1.2f, 1.2f);
        astro.dibujar(new GLES20(),arrayTextura,2);
        rotate(1,0,0,90);
        puntosAstro.dibujar(new GLES20());

        Posicion0();
        translate(0,0,-50f);
        translate((float)((Math.sin(rotacion/7))*20),0,(float)Math.cos(rotacion/7)*20);
        rotate(0,1,0,rotacion);
        scale(1.5f, 1.5f, 1.5f);
        astro.dibujar(new GLES20(),arrayTextura,3);
        rotate(1,0,0,90);
        puntosAstro.dibujar(new GLES20());

        Posicion0();
        translate(0,0,-50f);
        translate((float)((Math.sin(rotacion/8))*26),0,(float)Math.cos(rotacion/8)*26);
        rotate(0,1,0,rotacion);
        scale(1.45f, 1.45f, 1.45f);
        astro.dibujar(new GLES20(),arrayTextura,5);
        rotate(1,0,0,90);
        puntosAstro.dibujar(new GLES20());

        Posicion0();
        translate(0,0,-50f);
        translate((float)((Math.sin(rotacion/10))*32),0,(float)Math.cos(rotacion/10)*32);
        rotate(0,1,0,rotacion);
        scale(1.6f, 1.6f, 1.6f);
        astro.dibujar(new GLES20(),arrayTextura,6);

        Posicion0();
        translate(0,0,-50f);
        translate((float)((Math.sin(rotacion/12))*35),0,(float)Math.cos(rotacion/12)*35);
        rotate(0,1,0,rotacion);
        scale(1.8f, 1.8f, 1.8f);
         astro.dibujar(new GLES20(),arrayTextura,7);

        Posicion0();
        translate(0,0,-50f);
        translate((float)((Math.sin(rotacion/13))*39),0,(float)Math.cos(rotacion/13)*39);
        rotate(0,1,0,rotacion);
        scale(2f, 2f, 2f);
        astro.dibujar(new GLES20(),arrayTextura,8);

        Posicion0();
        translate(0,0,-50f);
        translate((float)((Math.sin(rotacion/14))*44),0,(float)Math.cos(rotacion/14)*44);
        rotate(0,1,0,rotacion);
        scale(2.2f, 2.2f, 2.2f);
        astro.dibujar(new GLES20(),arrayTextura,9);

        Posicion0();
        translate(0,0,-60);
        estrellas.dibujar(new GLES20 ());



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
