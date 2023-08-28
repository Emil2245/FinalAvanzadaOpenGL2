package ec.edu.uce.pa.renderes;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ec.edu.uce.pa.geometria.HexagonopProy0;

public class RenderHexagonoProyFP implements GLSurfaceView.Renderer {

    private HexagonopProy0 hex;

    private Context contexto;

    private float[] matrizProyeccion = new float[16];
    private float[] matrizModelo = new float[16];
    private float[] matrizTemp = new float[16];
    public float relacionAspecto;
    public float varRotacion=0f;

    public RenderHexagonoProyFP(Context contexto) {
        this.contexto = contexto;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
        gl.glClearColor(0.5F, 0.5F, 0.5F, 1.0F);
        hex = new HexagonopProy0(contexto, matrizProyeccion);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        relacionAspecto = (float) width / height;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        posicionarObjeto(0,0,-2,varRotacion);
        hex.dibujar(new GLES20());
        varRotacion++;
        //punto.dibujar(gl);
    }
    private void posicionarObjeto(float x, float y, float z,float angulo){
        Matrix.frustumM(matrizProyeccion, 0,
                -relacionAspecto, relacionAspecto, -1, 1, 1, 30);

        Matrix.setIdentityM(matrizModelo, 0);
        Matrix.translateM(matrizModelo, 0, x, y, z);
        Matrix.rotateM(matrizModelo, 0, angulo, 0, 1, 0);

        Matrix.multiplyMM(matrizTemp, 0,
                matrizProyeccion, 0,
                matrizModelo, 0);
        System.arraycopy(matrizTemp, 0, matrizProyeccion, 0, matrizTemp.length);

    }
}
