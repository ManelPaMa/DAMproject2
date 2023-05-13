package com.example.damproject2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.damproject2.Objects.Entrenamiento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegistrarEntrenamientoActivity extends AppCompatActivity {

    protected TextView label1;
    protected EditText caja1;
    protected EditText caja2;
    protected Button boton_registrar;
    protected Button boton_volver;
    private Intent pasarPantalla;

    protected String fecha;
    protected String deporte;
    protected String descripcion;
    private Bundle fechaHome;

    private String user;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser fUser;
    private Entrenamiento registro1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_entrenamiento);



        // Instancia database
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser().getUid();

        registro1 = new Entrenamiento();

        label1 = (TextView) findViewById(R.id.label1_registrar_entrenamiento);
        caja1 = (EditText) findViewById(R.id.caja1_registrar_entrenamiento);
        caja2 = (EditText) findViewById(R.id.caja2_registrar_entrenamiento);
        boton_registrar = (Button) findViewById(R.id.boton_registrar_registrar_entrenamiento);
        boton_volver = (Button) findViewById(R.id.boton_volver_registrar_entrenamiento);

        fechaHome = getIntent().getExtras();
        fecha = fechaHome.getString("fecha");

        checkRegistroExist(fecha, registro1);

        /**
         * Button registrar entrenamiento ---> Registra un entrenamiento en la base de datos particular del usuario
         */
        boton_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deporte = caja1.getText().toString();
                descripcion = caja2.getText().toString();

                if (deporte.isEmpty()|| descripcion.isEmpty()) {
                    Toast.makeText(RegistrarEntrenamientoActivity.this, "Debe rellenar ambos campos", Toast.LENGTH_SHORT).show();
                } else {
                    /**
                     * Check registro --> registro exists in database
                     */
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    /**
                     * Alert dialog confirmar registrar entrenamiento
                     */
                    AlertDialog.Builder alert = new AlertDialog.Builder(RegistrarEntrenamientoActivity.this);
                    alert.setMessage("Está seguro que quiere registrar el entrenamiento?")
                            .setCancelable(false).setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    /**
                                     * Si el registro ya existe no permite crearlo
                                     */
                                    if (fecha.equals(registro1.getFecha())) {
                                        Toast.makeText(RegistrarEntrenamientoActivity.this, "El entrenamiento ya esta registrado en la base de datos, para registrarlo de nuevo debes eliminar el antiguo", Toast.LENGTH_SHORT).show();
                                    }
                                    /**
                                     * Si el registro no existe se guarda en la base de datos
                                     */
                                    else {

                                        try {
                                            addDataDocument(user, fecha, deporte, descripcion);
                                            Toast.makeText(RegistrarEntrenamientoActivity.this, "El entrenamiento se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            Toast.makeText(RegistrarEntrenamientoActivity.this, "Ocurrió algún error al registrar por favor vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            })
                            /**
                             * Button negative --> reset Activity
                             */
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent reset = new Intent(RegistrarEntrenamientoActivity.this, RegistrarEntrenamientoActivity.class);
                                    startActivity(reset);
                                }
                            });
                    AlertDialog title = alert.create();
                    title.setTitle("Confirmar");
                    title.show();
                }
            }
        });

        boton_volver.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view){
                pasarPantalla = new Intent(RegistrarEntrenamientoActivity.this, RegistroEntrenamientosActivity.class);
                pasarPantalla.putExtra("fecha",fecha);
                finish();
                startActivity(pasarPantalla);
            }
        });


    }

    /**
     * Método agregar un entrenamiento a la base de datos ---> cada usuario tendrá integrado una colección de registros de entrenamientos
     *
     * @param fecha
     * @param deporte
     * @param descripcion
     */
    private void addDataDocument(String user,String fecha , String deporte, String descripcion) {

        Map<String, Object> data = new HashMap<>();
        data.put("fecha", fecha);
        data.put("deporte", deporte);
        data.put("descripcion", descripcion);


        db.collection("usuarios/" + user + "/registroEntrenamientos").document(fecha)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("", "Error writing document", e);
                    }
                });
    }



    /**
     * Método comprobar si la fecha facilitada por el usuario tiene un registro ya --> evitamos la duplicación de los registros de entrenamientos
     *
     * @param fecha
     * @param registro1
     */
    public void checkRegistroExist(String fecha, Entrenamiento registro1) {

        user = mAuth.getCurrentUser().getUid();
        CollectionReference ref = db.collection("usuarios").document(user).collection("registroEntrenamientos");

        ref.whereEqualTo("fecha", fecha).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        registro1.setFecha(document.get("fecha").toString());
                        registro1.setDeporte(document.get("deporte").toString());
                        registro1.setDeporte(document.get("descripcion").toString());
                    }
                } else {
                    Log.d("---->Salida else", "Error getting documents: ", task.getException());
                }

            }

        });

    }
}