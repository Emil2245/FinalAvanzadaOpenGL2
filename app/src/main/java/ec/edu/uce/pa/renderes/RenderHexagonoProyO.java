package ec.edu.uce.pa.renderes;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ec.edu.uce.pa.geometria.HexagonopProy0;

public class RenderHexagonoProyO implements GLSurfaceView.Renderer {

        private HexagonopProy0 hex;

        private Context contexto;

        private  float[] matrizProyeccion = new float[16];

        public RenderHexagonoProyO(Context contexto){
            this.contexto = contexto;
        }
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
            gl.glClearColor(0.5F,0.5F,0.5F,1.0F);

            hex = new HexagonopProy0(contexto,matrizProyeccion);

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0,0,width,height);
            float relacionAspecto = (float) width/height;
            Matrix.orthoM(matrizProyeccion,0,-relacionAspecto,relacionAspecto,-1,1,-1,1);

        }

        @Override
        public void onDrawFrame(GL10 gl) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            hex.dibujar(new GLES20());

            //punto.dibujar(gl);
        }
    }
