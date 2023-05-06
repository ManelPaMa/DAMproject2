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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damproject2.Objects.Alimento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DeleteFoodActivity extends AppCompatActivity {

    //Variables clase DeleteFood
    protected TextView txt1;
    protected ListView list1;
    protected Button btn1;

    protected ArrayList <Alimento> foodData = new ArrayList<Alimento>();

    private ArrayList<String> alimentos = new ArrayList<>();
    private ArrayAdapter<String> adapter;


    private String user, contenidoList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_food);

        //Asignación interfaz y datos Database
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser().getUid();

        txt1 = (TextView) findViewById(R.id.txtView_deleteFood);
        list1 = (ListView) findViewById(R.id.list1_deleteFood);
        btn1 = (Button) findViewById(R.id.btn1_deleteFood);

        getInfoFood(user);

        /**
         * Buttón import datos and print List1
         */
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFoodName();
                adapter = new ArrayAdapter<String>(DeleteFoodActivity.this, android.R.layout.simple_list_item_1, alimentos);
                list1.setAdapter(adapter);
                btn1.setEnabled(false);
            }
        });


        /**
         * Pulsado corto item ListView === resultado pulsado largo
         */
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Get position item + database
                contenidoList = parent.getItemAtPosition(position).toString();

                AlertDialog.Builder alert = new AlertDialog.Builder(DeleteFoodActivity.this);
                alert.setMessage("Está seguro que es este el alimento (Nombre:"+ foodData.get(position).getNombreFood()+ " - Tipo alimento:"
                                + foodData.get(position).getTypeFood() + " - Calorias:" + foodData.get(position).getCalories() + ")que quiere borrar?, si lo hace no será reversible...")
                        .setCancelable(false).setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    deleteOneFood(position);
                                    Toast.makeText(DeleteFoodActivity.this, "El alimento a sido eliminado correctamente del registro", Toast.LENGTH_SHORT).show();
                                    Intent reset = new Intent(DeleteFoodActivity.this, DeleteFoodActivity.class);
                                    startActivity(reset);

                                } catch (Exception e){
                                    Toast.makeText(DeleteFoodActivity.this, "Algo salió mal por favor vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        /**
                         * Button negative --> reset Activity
                         */
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent reset = new Intent(DeleteFoodActivity.this, DeleteFoodActivity.class);
                                startActivity(reset);
                            }
                        });
                AlertDialog title = alert.create();
                title.setTitle("Confirmar");
                title.show();


            }
        });

    }

    /**
     * Get info all documents --> asignar info ArrayList foodData
     * @param user
     */
    public void getInfoFood (String user){


        CollectionReference ref = db.collection("usuarios").document(user).collection("alimentos");

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
                        foodData.add(alimento);
                    }
                } else {
                    ;
                }

            }


        });


    }

    /**
     * Delete one document to collection alimentos
     * @param position
     */
    public void deleteOneFood(int position)
    {
        db.collection("usuarios").document(user).collection("alimentos").document(foodData.get(position).getId())
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
     * Asign foodData to alimentos
     */
    public void getFoodName(){
        String cadena = "";
        for (int i = 0; i<foodData.size(); i++){
            cadena=""+ foodData.get(i).getNombreFood()+ "\n";
            alimentos.add(cadena);
        }
    }
    /**
     * Creación menu_listado.xml
     *
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_deletefood, menu);
        return true;

    }

    /**
     * Item CONFIG menú == Return homeActivity
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            /**
             * Delete all Food --> Delete all documents database
             */
            case R.id.menuItem1_deleteFood:

                AlertDialog.Builder alert = new AlertDialog.Builder(DeleteFoodActivity.this);
                alert.setMessage("Está seguro que quiere eliminar todos los alimentos de la lista, esto no es reversible y deberá volver a introducir-los uno a uno")
                        .setCancelable(false).setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {

                                    for (int i= 0; i<foodData.size(); i++)
                                    {
                                     deleteOneFood(i);
                                    }
                                    Toast.makeText(DeleteFoodActivity.this, "Los alimentos han sido eliminados correctamente del registro", Toast.LENGTH_SHORT).show();
                                    Intent reset = new Intent(DeleteFoodActivity.this, DeleteFoodActivity.class);
                                    startActivity(reset);

                                } catch (Exception e){
                                    Toast.makeText(DeleteFoodActivity.this, "Algo salió mal por favor vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        /**
                         * Button negative --> reset Activity
                         */
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent reset = new Intent(DeleteFoodActivity.this, DeleteFoodActivity.class);
                                startActivity(reset);
                            }
                        });
                AlertDialog title = alert.create();
                title.setTitle("Confirmar");
                title.show();


                return true;

            /**
             * Return to addFood
             */
            case R.id.menuItem2_deleteFood:

                Intent pasarPantalla = new Intent(DeleteFoodActivity.this, AddFoodActivity.class);
                startActivity(pasarPantalla);

                return true;

        }

        return false;
    }

}