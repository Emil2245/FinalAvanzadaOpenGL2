package ec.edu.uce.pa.renderes;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ec.edu.uce.pa.geometria.Estrellas;

public class RenderEstrellas implements GLSurfaceView.Renderer {

    private Estrellas estrellas;
    private  float[] matrizProyeccion = new float[16];

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f,0.0f,0.0f,1.0f);
        estrellas = new Estrellas();

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        gl.glViewport(0,0,width,height);
        float relacionAspecto = (float) width/height;
        Matrix.orthoM(matrizProyeccion,0,-relacionAspecto,relacionAspecto,-1,1,-1,1);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT);
        //gl.glColor4f(1.0f,0.0f,0.0f,1.0f);
        estrellas.dibujar(new GLES20 ());

    }
}
