package ec.edu.uce.pa.renderes;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ec.edu.uce.pa.geometria.HexagonoColor;

public class RenderHexagonoColor implements GLSurfaceView.Renderer {

        private HexagonoColor hex;

        private Context contexto;

        public RenderHexagonoColor(Context contexto){
            this.contexto = contexto;
        }
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
            gl.glClearColor(0.5F,0.5F,0.5F,1.0F);

            hex = new HexagonoColor(contexto);

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0,0,width,height);

        }

        @Override
        public void onDrawFrame(GL10 gl) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            hex.dibujar(new GLES20());

            //punto.dibujar(gl);
        }
    }
