package com.example.damproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damproject2.Objects.Alimento;
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

public class AddFoodActivity extends AppCompatActivity {

    //Variables interfaz gráfica
    protected TextView txt1_addFood;
    protected EditText inputName_addFood, inputCalories_addFood, inputType_addFood;
    protected Button btnSend_addFood;

    //Variables clase
    protected String user, nameFood,typeFood;
    protected int calories;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser fUser;
    private Alimento food1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        // Instancia database
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        food1 = new Alimento();


        //Instancia interfaz
        txt1_addFood = (TextView) findViewById(R.id.txtV1_addFood);
        inputName_addFood = (EditText) findViewById(R.id.inputName_addFood);
        inputCalories_addFood = (EditText) findViewById(R.id.inputCalories_addFood);
        inputType_addFood = (EditText) findViewById(R.id.inputType_addFood);
        btnSend_addFood = (Button) findViewById(R.id.btnCreate_addFood);

        /**
         * Button crear alimento ---> Registra un alimento en la base de datos particular del usuario
         */
        btnSend_addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameFood = inputName_addFood.getText().toString();
                typeFood = inputType_addFood.getText().toString();

                try{
                    calories = Integer.parseInt(inputCalories_addFood.getText().toString());
                } catch (Exception e){
                    Toast.makeText(AddFoodActivity.this, "Uno de los campos esta sin rellenar", Toast.LENGTH_SHORT).show();
                }

                /**
                 * Text field empty
                 */
                if(nameFood.isEmpty() || calories == 0 || typeFood.isEmpty()){
                    Toast.makeText(AddFoodActivity.this, "Uno de los campos esta sin rellenar", Toast.LENGTH_SHORT).show();
                } else{
                    /**
                     * Check food --> alimento exists in database
                     */
                    checkFoodExist(nameFood, food1);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    /**
                     * Alert dialog confirmar crear alimento
                     */
                    AlertDialog.Builder alert = new AlertDialog.Builder(AddFoodActivity.this);
                    alert.setMessage("Está seguro que quiere registrar el alimento?")
                    .setCancelable(false).setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    /**
                                     * Si el alimento ya existe no permite crearlo
                                     */
                                    if (nameFood.equalsIgnoreCase(food1.getNombreFood())){
                                        Toast.makeText(AddFoodActivity.this, "El alimento ya esta registrado en la base de datos, para registrarlo de nuevo debes eliminar el antiguo", Toast.LENGTH_SHORT).show();
                                    }
                                    /**
                                     * Si el alimento no existe se guarda en la base de datos
                                     */
                                    else {

                                        try{
                                            addFood(nameFood,calories,typeFood);
                                            Toast.makeText(AddFoodActivity.this, "El alimento se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                                        } catch (Exception e){
                                            Toast.makeText(AddFoodActivity.this, "Ocurrió algún error al registrar por favor vuelva a intentarlo", Toast.LENGTH_SHORT).show();
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
                                Intent reset = new Intent(AddFoodActivity.this, AddFoodActivity.class);
                                startActivity(reset);
                                }
                            });
                    AlertDialog title = alert.create();
                    title.setTitle("Confirmar");
                    title.show();
                }


            }
        });



    }

    /**
     * Método agregar un alimento a la base de datos ---> cada usuario tendrá integrado una colección de alimentos particulares y añadirá los que prefiera
     * @param nameFood
     * @param calories
     * @param typeFood
     */
    private void addFood(String nameFood, int calories, String typeFood) {
        user = mAuth.getCurrentUser().getUid();

        Map<String, Object> data = new HashMap<>();
        data.put("nombre", nameFood);
        data.put("tipo", typeFood);
        data.put("calorias", calories);

        db.collection("usuarios/" + user + "/alimentos")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("", "DocumentSnapshot written with ID: " + documentReference.getId());
                        String id = documentReference.getId();
                        data.put("id", id);
                        db.collection("usuarios/" + user + "/alimentos").document(id)
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
     * Método comprobar si el nombre facilitado por el usuario tiene un registro ya --> evitamos la repetición de alimentos
     * @param nameFood
     * @param food1
     */
    public void checkFoodExist (String nameFood, Alimento food1){

        user = mAuth.getCurrentUser().getUid();
        CollectionReference ref = db.collection("usuarios").document(user).collection("alimentos");

        ref.whereEqualTo("nombre", nameFood).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        food1.setId(document.get("id").toString());
                        food1.setNombreFood(document.get("nombre").toString());
                        food1.setCalories(Integer.parseInt(document.get("calorias").toString()));
                        food1.setTypeFood(document.get("tipo").toString());

                    }
                } else {
                    Log.d("---->Salida else", "Error getting documents: ", task.getException());
                }

            }

        });
    }


}

