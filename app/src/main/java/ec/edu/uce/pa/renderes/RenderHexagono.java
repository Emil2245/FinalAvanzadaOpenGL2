package ec.edu.uce.pa.renderes;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ec.edu.uce.pa.geometria.Hexagono;
import ec.edu.uce.pa.geometria.Punto;

public class RenderHexagono implements GLSurfaceView.Renderer {

    private Hexagono hexagono;
    private Context context;

    public RenderHexagono (Context context) {
        this.context=context;
    };

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.5f,0.5f,0.5f,1.0f);
        hexagono = new Hexagono(context);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        gl.glViewport(0,0,width,height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(gl.GL_COLOR_BUFFER_BIT);
        //gl.glColor4f(1.0f,0.0f,0.0f,1.0f);
        hexagono.dibujar(new GLES20 ());

    }
}
