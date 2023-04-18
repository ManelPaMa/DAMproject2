package com.example.damproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InicioEntrenos extends AppCompatActivity {

    protected TextView label1;
    protected EditText caja1;
    protected Button boton_planes_entrenamiento;
    protected Button boton_registro_entrenamientos;
    private Intent pasarPantalla;
    private String contenidoCaja1 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicioentreno);

        label1 = (TextView) findViewById(R.id.label1_parteEntrenamiento);
        caja1 = (EditText) findViewById(R.id.caja1_parteEntrenamiento);
        boton_planes_entrenamiento = (Button) findViewById(R.id.boton_planes_entrenamiento_parteEntrenamiento);
        boton_registro_entrenamientos = (Button) findViewById(R.id.boton_registro_entrenamientos_parteEntrenamiento);

        boton_planes_entrenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = label1.getText().toString();
                if(label1.getText().equals(null)){
                    Toast.makeText(InicioEntrenos.this, "Debe introducir un deporte", Toast.LENGTH_SHORT).show();
                } else {
                    contenidoCaja1 = label1.getText().toString();
                    pasarPantalla = new Intent(InicioEntrenos.this,EntrenamientosActivity.class);
                    pasarPantalla.putExtra("DEPORTE", contenidoCaja1);
                    startActivity(pasarPantalla);
                }

            }
        });

        boton_registro_entrenamientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPantalla = new Intent(InicioEntrenos.this,RegistroEntrenamientosActivity.class);
                startActivity(pasarPantalla);
            }
        });
        
    }
}