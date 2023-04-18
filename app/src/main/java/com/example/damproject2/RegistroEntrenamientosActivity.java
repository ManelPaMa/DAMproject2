package com.example.damproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroEntrenamientosActivity extends AppCompatActivity {

    protected EditText caja1;
    protected Button boton_entrenamientos_anteriores;
    protected Button boton_entrenamientos_todos;
    protected Button boton_registrar;
    protected Button boton_volver;
    private Intent pasarPantalla;


    private String contenidoCaja1 = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entrenamientos);



        caja1 = (EditText) findViewById(R.id.caja1_registro_entrenamientos);
        boton_entrenamientos_anteriores = (Button) findViewById(R.id.boton_entrenamientos_anteriores_registro_entrenamientos);
        boton_entrenamientos_todos = (Button) findViewById(R.id.boton_entrenamientos_todos_registro_entrenamientos);
        boton_registrar = (Button) findViewById(R.id.boton_registrar_registro_entrenamientos);
        boton_volver = (Button) findViewById(R.id.boton_volver_registro_entrenamientos);

        contenidoCaja1 = caja1.getText().toString();

        boton_entrenamientos_anteriores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPantalla = new Intent(RegistroEntrenamientosActivity.this, RegistroEntrenamientosDatosActivity.class);
                pasarPantalla.putExtra("FECHA", contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_entrenamientos_todos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPantalla = new Intent(RegistroEntrenamientosActivity.this, RegistroEntrenamientosDatosActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPantalla = new Intent(RegistroEntrenamientosActivity.this, RegistrarEntrenamientoActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPantalla = new Intent(RegistroEntrenamientosActivity.this, InicioEntrenos.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

    }
}