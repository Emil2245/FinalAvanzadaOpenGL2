package ec.edu.uce.pa.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ec.edu.uce.pa.R;


public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_portada);

        Button btnEjercicios=findViewById(R.id.btnActividad);
        btnEjercicios.setOnClickListener(v -> {
            Intent intent=new Intent(v.getContext(), Activities.class);
            startActivity(intent);
            finish();
        });

        Button btnSalir=findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(v -> {
            System.exit(0);
            Toast.makeText(getApplicationContext(),"¡Adiós!",Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Toast.makeText(getApplicationContext(),"¡Hola de Nuevo!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(getApplicationContext(),"¡Adiós!",Toast.LENGTH_SHORT).show();
    }


}

