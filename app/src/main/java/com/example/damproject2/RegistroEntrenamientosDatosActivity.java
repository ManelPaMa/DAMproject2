package com.example.damproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroEntrenamientosDatosActivity extends AppCompatActivity {

    protected TextView label1;
    protected TextView label2;
    protected Button  boton_volver;

    private Intent pasarDePantalla;
    private Bundle fecha;
    private String paquete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entrenamientos_datos);

        label1 = (TextView) findViewById(R.id.label1_registro_entrenamientos_datos);
        label2 = (TextView) findViewById(R.id.label2_registro_entrenamientos_datos);
        boton_volver = (Button) findViewById(R.id.boton_volver_registro_entrenamientos_datos);

        fecha = getIntent().getExtras();
        paquete = fecha.getString("FECHA");

        if (paquete.equals(null)) {
            //ver todos los registros
        } else {
            //ver registros hasta la fecha indicada
        }

        boton_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarDePantalla = new Intent(RegistroEntrenamientosDatosActivity.this, InicioEntrenos.class);
                finish();
                startActivity(pasarDePantalla);
            }

        });
    }
}