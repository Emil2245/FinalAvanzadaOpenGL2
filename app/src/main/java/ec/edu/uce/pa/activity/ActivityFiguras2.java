package ec.edu.uce.pa.activity;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ec.edu.uce.pa.R;
import ec.edu.uce.pa.renderes.RenderHexagono;
import ec.edu.uce.pa.renderes.RenderHexagonoProyO;
import ec.edu.uce.pa.renderes.RenderHexagonoStride;
import ec.edu.uce.pa.renderes.RenderHexagonoTextura;

public class ActivityFiguras2 extends AppCompatActivity {
    private GLSurfaceView view;

    private GLSurfaceView.Renderer renderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_figuras_gl2);

        view = new GLSurfaceView(this);
        view.setEGLContextClientVersion(2);
        renderer = null;

        Button btnDibujar = findViewById(R.id.button_pintar);
        btnDibujar.setOnClickListener(v -> {
            int opcionSel;
            RadioGroup rgOpcines = findViewById(R.id.RgPantalla);
            opcionSel = rgOpcines.getCheckedRadioButtonId();

            if (opcionSel > 0) {
                if (opcionSel == R.id.rb_hexagonoFijo) {
                    renderer = new RenderHexagono(this);
                } else if (opcionSel == R.id.rb_hexagonoStride) {
                    renderer = new RenderHexagonoStride(this);
                } else if (opcionSel == R.id.rb_proyeccion) {
                    renderer = new RenderHexagonoProyO(this);
                } else if (opcionSel == R.id.rb_textura) {
                    renderer = new RenderHexagonoTextura(this);
                }
                view.setRenderer(renderer);
                setContentView(view);

            } else
                Toast.makeText(ActivityFiguras2.this, "Seleccione una opcion", Toast.LENGTH_LONG).show();
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Activities.class);
        startActivity(intent);
    }

}
