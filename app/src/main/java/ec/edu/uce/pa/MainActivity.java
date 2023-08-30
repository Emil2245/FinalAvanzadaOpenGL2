package ec.edu.uce.pa;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ec.edu.uce.pa.renderes.RendererFigurasPrueba;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView view;
    private GLSurfaceView.Renderer render = null;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = new GLSurfaceView(this);
        view.setEGLContextClientVersion(2);
        render = new RendererFigurasPrueba(this);

        view.setRenderer(render);
        setContentView(view);
    }
}