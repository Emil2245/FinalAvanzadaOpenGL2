package ec.edu.uce.pa;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ec.edu.uce.pa.renderes.RenderCubo;
import ec.edu.uce.pa.renderes.RenderPrisma;
import ec.edu.uce.pa.renderes.RendererFigurasPruebas;

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

        //render = new RenderCono(this);
        //render = new RenderCuboColores(this);
        render = new RendererFigurasPruebas(this);
        //render = new RenderCubo(this);
        //render = new RenderEsfera(this);
        //render = new RenderHexagonoProyFP(this);

//        render = new RenderHexagonoProyFP(this);
        //render = new RenderHexagonoTextura(this);
       // render = new RenderHexagonoStride(this);
       // render = new RenderHexagonoProyO(this);
        //render = new RenderHexagonoColor(this);
        //render = new RenderHexagono(this);
       // render = new RenderPunto();

        view.setRenderer(render);
        setContentView(view);
    }
}