package com.example.damproject2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.damproject2.Objects.Alimento;
import com.example.damproject2.Objects.Entrenamiento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


import androidx.appcompat.app.AppCompatActivity;

public class RegistroEntrenamientosActivity extends AppCompatActivity {

    protected TextView label1;

    protected ListView list1;
    protected Button boton_ver;
    protected Button boton_registrar;
    protected Button boton_volver;
    private Intent pasarPantalla;

    private ArrayList<String> registros;
    private ArrayAdapter<String> adaptador;
    protected ArrayList<Entrenamiento> entrenoList;

    private String contenidoItem, fecha;
    private Bundle fechaHome;

    private String user;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser fUser;
    private ArrayList<String> fechas= new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entrenamientos);

        // Instancia database
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser().getUid();


        label1 = (TextView) findViewById(R.id.label1_registro_entrenamientos);
        list1 = (ListView) findViewById(R.id.list1_registro_entrenamientos);
        boton_ver = (Button) findViewById(R.id.boton_ver_registro_entrenamientos);
        boton_registrar = (Button) findViewById(R.id.boton_registrar_registro_entrenamientos);
        boton_volver = (Button) findViewById(R.id.boton_volver_registro_entrenamientos);


        fechaHome = getIntent().getExtras();
        fecha = fechaHome.getString("fecha");
        getFechasRegistros(user);



        boton_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sacar todas las fechas de la base de datos y guardarlas en array list Registros
                getInfoEntreno();
                adaptador= new ArrayAdapter<String>(RegistroEntrenamientosActivity.this, android.R.layout.simple_list_item_1, registros);
                list1.setAdapter(adaptador);
            }
        });

        boton_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPantalla = new Intent(RegistroEntrenamientosActivity.this, RegistrarEntrenamientoActivity.class);
                pasarPantalla.putExtra("fecha", fecha);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPantalla = new Intent(RegistroEntrenamientosActivity.this, HomeActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(RegistroEntrenamientosActivity.this);
                alert.setMessage("Fecha : " + entrenoList.get(i).getFecha() + "\n" + "Tipo entreno: " + entrenoList.get(i).getDeporte()
                        + "\n" + "Descripción: " + entrenoList.get(i).getDescripcion())
                        .setCancelable(false).setPositiveButton("Volver", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {


                                } catch (Exception e) {
                                    Toast.makeText(RegistroEntrenamientosActivity.this, "Algo salió mal por favor vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                AlertDialog title = alert.create();
                title.setTitle("Información entreno");
                title.show();
            }
        });



        list1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RegistroEntrenamientosActivity.this);
                alert.setMessage("Desea borrar el entrenamiento seleccionado?")
                        .setCancelable(false).setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    deleteOneTrain(position,fecha);
                                    Toast.makeText(RegistroEntrenamientosActivity.this, "El entrenamiento a sido borrado del registro", Toast.LENGTH_SHORT).show();
                                    Intent reset = new Intent(RegistroEntrenamientosActivity.this, RegistroEntrenamientosActivity.class);
                                    reset.putExtra("fecha",fecha);
                                    startActivity(reset);
                                } catch (Exception e) {
                                    Toast.makeText(RegistroEntrenamientosActivity.this, "Algo salió mal por favor vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        /**
                         * Button negative --> reset Activity
                         */
                        .setNegativeButton("Volver", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog title = alert.create();
                title.setTitle("Borrar el entrenamiento");
                title.show();

                return true;
            }
        });

    }

    public void getFechasRegistros (String user){
        entrenoList = new ArrayList<>();

        CollectionReference ref = db.collection("usuarios").document(user).collection("registroEntrenamientos");

        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Entrenamiento registroEntrenamiento = new Entrenamiento();

                        registroEntrenamiento.setFecha(document.get("fecha").toString());
                        registroEntrenamiento.setDeporte(document.get("deporte").toString());
                        registroEntrenamiento.setDescripcion(document.get("descripcion").toString());
                        entrenoList.add(registroEntrenamiento);
                    }
                } else {
                    ;
                }

            }


        });

    }

    public void getInfoEntreno(){
        String cadena = "";
        registros = new ArrayList<>();
        for (int i = 0; i<entrenoList.size(); i++){
            cadena=""+ entrenoList.get(i).getFecha()+ " - " + entrenoList.get(i).getDeporte() + "\n";
            registros.add(cadena);
        }
    }

    public void deleteOneTrain(int position, String fecha)
    {
        db.collection("usuarios").document(user).collection("registroEntrenamientos").document(entrenoList.get(position).getFecha())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

}