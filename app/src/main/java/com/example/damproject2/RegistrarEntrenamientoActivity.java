package com.example.damproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrarEntrenamientoActivity extends AppCompatActivity {

    protected TextView label1;
    protected EditText caja1;
    protected EditText caja2;
    protected EditText caja3;
    protected Button boton_registrar;
    protected Button boton_volver;
    private Intent pasarPantalla;
    private String contenidoCaja1 = "";
    private String contenidoCaja2 = "";
    private String contenidoCaja3 = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_entrenamiento);



        label1 = (TextView) findViewById(R.id.label1_registrar_entrenamiento);
        caja1 = (EditText) findViewById(R.id.caja1_registrar_entrenamiento);
        caja2 = (EditText) findViewById(R.id.caja2_registrar_entrenamiento);
        caja3 = (EditText) findViewById(R.id.caja3_registrar_entrenamiento);
        boton_registrar = (Button) findViewById(R.id.boton_registrar_registrar_entrenamiento);
        boton_volver = (Button) findViewById(R.id.boton_volver_registrar_entrenamiento);

        contenidoCaja1 = caja1.getText().toString();
        contenidoCaja2 = caja2.getText().toString();
        contenidoCaja3 = caja3.getText().toString();

        boton_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //insertar registro
            }
        });

        boton_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPantalla = new Intent(RegistrarEntrenamientoActivity.this, RegistroEntrenamientosActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

    }
}