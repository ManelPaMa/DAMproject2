package com.example.damproject2;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FireBase {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void registerUser (String email, String password, String alias,int edad, double altura, double peso, double imc , String sexo, String activityLevel){

        Map <String, Object> usuarios = new HashMap<>();
        usuarios.put("email", email);
        usuarios.put("password", password);
        usuarios.put("alias", alias);
        usuarios.put("edad", edad);
        usuarios.put("altura", altura);
        usuarios.put("peso", peso);
        usuarios.put("imc", imc);
        usuarios.put("sexo", sexo);
        usuarios.put("nivelActividad",activityLevel);
        db.collection("usuarios").document(email).set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    /*public void getDocumentID(String email){

        ArrayList<String> id = new ArrayList<>();

        db.collection("usuarios").whereEqualTo("email", email).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("--->", document.getId());

                                id.add(document.getId());

                            }

                        }

                    }
                });

    }

    public void getUserDatos(String email){
        CollectionReference user = db.collection("usuarios");
        Query query = user.whereEqualTo("email", email);
        System.out.println("--->" + query);
    }*/

    public String getDocId(String email){
        final String[] id = {""};
        DocumentReference docRef = db.collection("usuarios").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        id[0] = document.get("email").toString();
                        System.out.println("---> dentro del if + " + id[0]);
                        String idDoc= id[0];
                        System.out.println("---> dentro del if String + " + idDoc);
                    } else {
                        Log.d("--->", "No such document");
                    }
                }
            }
        });
        return id[0];
    }



}

    //String email, String password, String alias, double altura, double peso, double imc , String sexo, String activityLevel