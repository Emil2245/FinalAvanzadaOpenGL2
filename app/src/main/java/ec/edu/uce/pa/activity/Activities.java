package ec.edu.uce.pa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ec.edu.uce.pa.R;

public class Activities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        Button btnOpenGL2=findViewById(R.id.openGL2);
        btnOpenGL2.setOnClickListener(v -> {
            Intent intent=new Intent(v.getContext(), ActivityFiguras2.class);
            startActivity(intent);
            finish();
        });

        Button btnSalir = findViewById(R.id.btnRegresar);
        btnSalir.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Principal.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"Regresemos",Toast.LENGTH_SHORT).show();
            finish();
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Principal.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Regresemos",Toast.LENGTH_SHORT).show();
    }
}