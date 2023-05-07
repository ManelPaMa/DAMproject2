package com.example.damproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damproject2.Objects.Alimento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CrearComidasActivity extends AppCompatActivity {

    protected TextView txt1_crearComidas, txt2_crearComidas, txt3_crearComidas, txt4_crearComidas, txt5_crearComidas;
    protected ListView list1_crearComidas, list2_crearComidas;
    protected Spinner spinnerTypeFood_crearComidas, spinnerTypeMeal_crearComidas;
    protected ProgressBar bar1_crearComidas;

    private Date dateToday = new Date();
    private String fecha, user, typeFood , nombreFood, calories;

    protected ArrayList<Alimento> foodData;

    private ArrayList<String> alimentos;
    private ArrayList<String> spinnerTypeFood, listFood, spinnerTypeMeal, listMeal;
    private String[] tiposComida;

    private ArrayAdapter<String> adapterTypeFood, adapterAlimentos;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_comidas);

        //Asignación interfaz y datos Database
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser().getUid();

        txt1_crearComidas = (TextView) findViewById(R.id.txtV1_crearComidas);
        txt2_crearComidas = (TextView) findViewById(R.id.txtV2_crearComida);
        txt3_crearComidas = (TextView) findViewById(R.id.txtV3_crearComida);
        txt4_crearComidas = (TextView) findViewById(R.id.txtV4_crearComida);
        txt5_crearComidas = (TextView) findViewById(R.id.txtV5_crearComida);
        list1_crearComidas = (ListView) findViewById(R.id.listVFood_crearComida);
        list2_crearComidas = (ListView) findViewById(R.id.listVMeal_crearComida);
        spinnerTypeFood_crearComidas = (Spinner) findViewById(R.id.spinnerTypeAlimento_crearComida);
        spinnerTypeMeal_crearComidas = (Spinner) findViewById(R.id.spinnerTypeComida_crearComida);
        bar1_crearComidas = (ProgressBar) findViewById(R.id.progressCalories_crearComida);

        fecha = new SimpleDateFormat("dd-MM-yyyy").format(dateToday);
        txt1_crearComidas.setText(fecha + " Calorías Diarias");

        String [] tiposComida = {"Cena", "Desayuno", "Almuerzo", "Merienda", "EntreHoras"};

        spinnerTypeFood = new ArrayList<>();
        spinnerTypeFood.add("");
        spinnerTypeFood.add("Carne");
        spinnerTypeFood.add("Pescado");
        spinnerTypeFood.add("Láctico");
        spinnerTypeFood.add("Fruta");
        spinnerTypeFood.add("Verdura");
        spinnerTypeFood.add("Cereales");
        spinnerTypeFood.add("Otro");

        adapterTypeFood = new ArrayAdapter<String>(CrearComidasActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,spinnerTypeFood);
        spinnerTypeFood_crearComidas.setAdapter(adapterTypeFood);

        spinnerTypeFood_crearComidas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeFood = parent.getItemAtPosition(position).toString();
                getInfoFood(user, typeFood);
                if (position > 0) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(CrearComidasActivity.this);
                    alert.setMessage("Mostrar los alimentos del tipo " + typeFood)
                            .setCancelable(false).setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        getFoodName();
                                        adapterAlimentos = new ArrayAdapter<String>(CrearComidasActivity.this, android.R.layout.simple_list_item_1, alimentos);
                                        list1_crearComidas.setAdapter(adapterAlimentos);


                                    } catch (Exception e) {
                                        Toast.makeText(CrearComidasActivity.this, "Algo salió mal por favor vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            })
                            /**
                             * Button negative --> reset Activity
                             */
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent reset = new Intent(CrearComidasActivity.this, CrearComidasActivity.class);
                                    startActivity(reset);
                                }
                            });
                    AlertDialog title = alert.create();
                    title.setTitle("Confirmar");
                    title.show();
                }
                else{

                }
                }

                @Override
                public void onNothingSelected (AdapterView < ? > parent){

                }

        });

        list1_crearComidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CrearComidasActivity.this);
                alert.setTitle("Escoge una opción")
                        .setSingleChoiceItems(tiposComida, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String comida = tiposComida[position];

                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

    }

    public void getInfoFood (String user,String typeFood){

        foodData = new ArrayList<>();
        CollectionReference ref = db.collection("usuarios").document(user).collection("alimentos");

        ref.whereEqualTo("tipo", typeFood).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Alimento alimento = new Alimento();

                        alimento.setId(document.get("id").toString());
                        alimento.setNombreFood(document.get("nombre").toString());
                        alimento.setCalories(Integer.parseInt(document.get("calorias").toString()));
                        alimento.setTypeFood(document.get("tipo").toString());
                        foodData.add(alimento);
                    }
                } else {
                    ;
                }

            }


        });

    }

    /**
     * Asign foodData to alimentos
     */
    public void getFoodName(){
        String cadena = "";
        alimentos = new ArrayList<>();
        for (int i = 0; i<foodData.size(); i++){
            cadena=""+ foodData.get(i).getNombreFood()+ "\n";
            alimentos.add(cadena);
        }
    }
}