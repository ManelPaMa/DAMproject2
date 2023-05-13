package com.example.damproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.damproject2.Objects.Alimento;
import com.example.damproject2.Objects.Entrenamiento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RegistroEntrenamientosDatosActivity extends AppCompatActivity {

    protected TextView label1;
    protected TextView label2;
    protected Button  boton_volver;

    private Intent pasarDePantalla;
    private Bundle fecha;
    private String paquete;
    private String contenidoBaseDatos = "";

    private String user;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entrenamientos_datos);

        // Instancia database
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        label1 = (TextView) findViewById(R.id.label1_registro_entrenamientos_datos);
        label2 = (TextView) findViewById(R.id.label2_registro_entrenamientos_datos);
        boton_volver = (Button) findViewById(R.id.boton_volver_registro_entrenamientos_datos);

        fecha = getIntent().getExtras();
        paquete = fecha.getString("contenido");

        /* leer el contenido de la base de datos del registro con esa fecha
        contenidoBaseDatos=;
        label2.setText();*/


        boton_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarDePantalla = new Intent(RegistroEntrenamientosDatosActivity.this, RegistroEntrenamientosActivity.class);
                finish();
                startActivity(pasarDePantalla);
            }

        });
    }

    public void getInfoRegistro (String user){


        CollectionReference ref = db.collection("usuarios").document(user).collection("registroEntrenamientos");

        ref.whereEqualTo("fecha", fecha).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Entrenamiento registroEntrenamiento = new Entrenamiento();

                        registroEntrenamiento.setFecha(document.get("fecha").toString());
                        registroEntrenamiento.setDeporte(document.get("deporte").toString());
                        registroEntrenamiento.setDescripcion(document.get("descripcion").toString());
                    }
                } else {
                    ;
                }

            }


        });
    }
}