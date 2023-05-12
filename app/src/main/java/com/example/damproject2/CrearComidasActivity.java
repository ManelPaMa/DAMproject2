package com.example.damproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrearComidasActivity extends AppCompatActivity {

    protected TextView txt1_crearComidas, txt2_crearComidas, txt3_crearComidas, txt4_crearComidas, txt5_crearComidas;
    protected ListView list1_crearComidas, list2_crearComidas;
    protected Spinner spinnerTypeFood_crearComidas, spinnerTypeMeal_crearComidas;
    protected ProgressBar bar1_crearComidas;

    private Date dateToday = new Date();
    private String fecha, user, typeFood , nameFood, comida, typeMeal;
    private int calories, sumaCalorias;
    private Bundle fechaHome;

    protected ArrayList<Alimento> foodData, mealContent;


    private ArrayList<String> spinnerTypeFood, listFood, spinnerTypeMeal, listMeal;
    private String[] tiposComida;

    private ArrayAdapter<String> adapterTypeFood, adapterFood, adapterTypeMeal, adapterMeal;

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

        fechaHome = getIntent().getExtras();
        fecha = fechaHome.getString("fecha");

        txt1_crearComidas.setText(fecha + " Comida diaria");
        addDataDocument(fecha);

        //Array tipos comida ---> use in single Dialog
        String [] tiposComida = {"Desayuno", "Almuerzo", "Merienda","Cena", "EntreHoras"};

        //Spinner TypeFood options
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

        //Spinner TypeMeal options
        spinnerTypeMeal= new ArrayList<>();
        spinnerTypeMeal.add("");
        spinnerTypeMeal.add("Desayuno");
        spinnerTypeMeal.add("Almuerzo");
        spinnerTypeMeal.add("Merienda");
        spinnerTypeMeal.add("Cena");
        spinnerTypeMeal.add("EntreHoras");

        adapterTypeMeal = new ArrayAdapter<String>(CrearComidasActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,spinnerTypeMeal);
        spinnerTypeMeal_crearComidas.setAdapter(adapterTypeMeal);

        /**
         * Código parte añadir alimentos
         */
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
                                        adapterFood = new ArrayAdapter<String>(CrearComidasActivity.this, android.R.layout.simple_list_item_1, listFood);
                                        list1_crearComidas.setAdapter(adapterFood);


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

        /**
         * List View añadir alimentos to comidas
         * Click 1 alimento ---> AlertDialog ---> SingleItem select ---> Push in database alimento on tipo comida
         */
        list1_crearComidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CrearComidasActivity.this);

                //Create singleItemChoose
                alert.setTitle("Escoge una opción")
                        .setSingleChoiceItems(tiposComida, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Select value singleItem
                        comida = tiposComida[which];
                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        nameFood = foodData.get(position).getNombreFood();
                        typeFood = foodData.get(position).getTypeFood();
                        calories = foodData.get(position).getCalories();

                        try{
                            //Add in database
                            addFood(nameFood,calories,typeFood,comida);
                            Toast.makeText(CrearComidasActivity.this, "El alimento se ha añadido a la comida correctamente", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(CrearComidasActivity.this, "Algo salió mal, vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                        }

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

        /**
         * Spinner selección comida ---> Mostrar contenido list View
         */

        spinnerTypeMeal_crearComidas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeMeal = parent.getItemAtPosition(position).toString();
                if (position > 0) {
                    getInfoMeal(user, typeMeal, fecha);
                    AlertDialog.Builder alert = new AlertDialog.Builder(CrearComidasActivity.this);
                    alert.setMessage("Mostrar el contenido: " + typeMeal)
                            .setCancelable(false).setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        sumaCalorias = sumarCaloriasComida();
                                        txt4_crearComidas.setText("Contenido comida : "+ sumaCalorias + " calorías");
                                        getMealName();
                                        adapterMeal = new ArrayAdapter<String>(CrearComidasActivity.this, android.R.layout.simple_list_item_1, listMeal);
                                        list2_crearComidas.setAdapter(adapterMeal);


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
        /**
         * Lista comidas ---> Short Click = Info food ---> LongClick = Posibility delete item
         */

        list2_crearComidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CrearComidasActivity.this);
                alert.setMessage("Nombre: " + mealContent.get(position).getNombreFood()+"\n" + "Tipo alimento: " + mealContent.get(position).getTypeFood() +"\n"
                                + "Calorias: " + mealContent.get(position).getCalories())
                        .setCancelable(false).setPositiveButton("Volver", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {


                                } catch (Exception e) {
                                    Toast.makeText(CrearComidasActivity.this, "Algo salió mal por favor vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                AlertDialog title = alert.create();
                title.setTitle("Información alimento");
                title.show();
            }
        });

        list2_crearComidas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CrearComidasActivity.this);
                alert.setMessage("Desea borrar el alimento seleccionado?")
                        .setCancelable(false).setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    deleteOneFood(position,fecha,typeMeal);
                                    Toast.makeText(CrearComidasActivity.this, "El alimento se a eliminado correctamente de la comida", Toast.LENGTH_SHORT).show();
                                    Intent reset = new Intent(CrearComidasActivity.this, CrearComidasActivity.class);
                                    startActivity(reset);
                                } catch (Exception e) {
                                    Toast.makeText(CrearComidasActivity.this, "Algo salió mal por favor vuelva a intentarlo", Toast.LENGTH_SHORT).show();
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
                title.setTitle("Borrar el alimento de la comida");
                title.show();

                return true;
            }
        });


    }

    /**
     * Método get info alimentos ---> almacenado en un ArrayList foodData
     * @param user
     * @param typeFood
     */
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
        listFood = new ArrayList<>();
        for (int i = 0; i<foodData.size(); i++){
            cadena=""+ foodData.get(i).getNombreFood()+ "\n";
            listFood.add(cadena);
        }
    }

    /**
     * Método crear fecha evitar tener 2 carpetas con la misma fecha
     * @param fecha
     */
    private void addDataDocument(String fecha) {

        Map<String, Object> data = new HashMap<>();
        data.put("fecha", fecha);


        db.collection("usuarios/" + user + "/comidas").document(fecha)
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
     * Método registrar alimentos en un tipo de comida database
     * @param nameFood
     * @param calories
     * @param typeFood
     * @param comida
     */
        private void addFood(String nameFood, int calories, String typeFood, String comida) {
            user = mAuth.getCurrentUser().getUid();

            Map<String, Object> data = new HashMap<>();
            data.put("nombre", nameFood);
            data.put("tipo", typeFood);
            data.put("calorias", calories);

            db.collection("usuarios/" + user + "/comidas/" + fecha + "/" + comida)
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("", "DocumentSnapshot written with ID: " + documentReference.getId());
                            String id = documentReference.getId();
                            data.put("id", id);
                            db.collection("usuarios/" + user + "/comidas/" + fecha + "/" +comida).document(id)
                                    .set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("", "Error adding document", e);
                        }
                    });

        }

    /**
     * Método obetener info contenido tipo comida Usuario/Fecha/TipoComida
     * @param user
     * @param typeMeal
     * @param fecha
     */
    public void getInfoMeal (String user,String typeMeal,String fecha){

        mealContent = new ArrayList<>();
        CollectionReference ref = db.collection("usuarios").document(user).collection("comidas").document(fecha).collection(typeMeal);

        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Alimento alimento = new Alimento();

                        alimento.setId(document.get("id").toString());
                        alimento.setNombreFood(document.get("nombre").toString());
                        alimento.setCalories(Integer.parseInt(document.get("calorias").toString()));
                        alimento.setTypeFood(document.get("tipo").toString());
                        mealContent.add(alimento);
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
    public void getMealName(){
        String cadena = "";
        listMeal = new ArrayList<>();
        for (int i = 0; i<mealContent.size(); i++){
            cadena=""+ mealContent.get(i).getNombreFood()+ "\n";
            listMeal.add(cadena);
        }
    }
    public int sumarCaloriasComida(){
        int result = 0;
        for(int i = 0; i<mealContent.size(); i++){
            result += mealContent.get(i).getCalories();
        }
        System.out.println("--- > salidaDeLafunción: " + result);
        return result;
    }

    /**
     * Delete one document to collection alimentos
     * @param position
     */
    public void deleteOneFood(int position, String fecha, String typeMeal)
    {
        db.collection("usuarios").document(user).collection("comidas").document(fecha).collection(typeMeal).document(mealContent.get(position).getId())
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

    /**
     * Creación menu_listado.xml
     *
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_crearcomidas, menu);
        return true;

    }

    /**
     * Item CONFIG menú == Return homeActivity or Go to DeleteFoodActivity
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.menuItem1_crearComidas:

                Intent pasarPantalla = new Intent(CrearComidasActivity.this, HomeActivity.class);
                startActivity(pasarPantalla);
                this.finish();

                return true;

        }

        return false;
    }

}
