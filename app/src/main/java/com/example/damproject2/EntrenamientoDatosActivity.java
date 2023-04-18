package com.example.damproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EntrenamientoDatosActivity extends AppCompatActivity {

    protected TextView label1;
    protected TextView label2;
    protected Button boton_atras;

    private Intent pasarDePantalla;
    private Bundle deporte;
    private String paquete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento_datos);

        label1 = (TextView) findViewById(R.id.label1_entrenamiento_datos);
        label2 = (TextView) findViewById(R.id.label2_entrenamiento_datos);
        boton_atras = (Button) findViewById(R.id.boton_volver_entrenamiento_datos);

        deporte = getIntent().getExtras();
        paquete = deporte.getString("DEPORTE");

        switch (paquete) {
            case "escalada":
                //mostrar datos entrenamiento
            case "crossfit":
                //mostrar datos entrenamiento
            case "remo":
                //mostrar datos entrenamiento
            case "padel":
                //mostrar datos entrenamiento
            case "tenis":
                //mostrar datos entrenamiento
            case "palas":
                //mostrar datos entrenamiento
            case "natacion":
                //mostrar datos entrenamiento
            case "gimnasio":
                //mostrar datos entrenamiento
            case "skate":
                //mostrar datos entrenamiento
            case "correr_pista":
                //mostrar datos entrenamiento
            case "correr_monte":
                //mostrar datos entrenamiento
            case "trx":
                //mostrar datos entrenamiento
            case "ciclismo_monte":
                //mostrar datos entrenamiento
            case "ciclismo":
                //mostrar datos entrenamiento
            case "barrenar":
                //mostrar datos entrenamiento
            case "surf":
                //mostrar datos entrenamiento
            case "patinaje":
                //mostrar datos entrenamiento
            case "triatlon":
                //mostrar datos entrenamiento

        }


        boton_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarDePantalla = new Intent(EntrenamientoDatosActivity.this, EntrenamientosActivity.class);
                finish();
                startActivity(pasarDePantalla);
            }
        });

    }
}