package com.example.damproject2;

import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FireBase {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void registerUser (String email, String password, String alias, int altura, float peso, float imc , String sexo, String activityLevel){


        Map <String, Object> usuarios = new HashMap<>();
        usuarios.put("email", "email");
        usuarios.put("password", "password");
        usuarios.put("alias", "alias");
        usuarios.put("altura", 142);
        usuarios.put("peso", 422);
        usuarios.put("imc", 13);
        usuarios.put("sexo", "sexo");
        usuarios.put("nivelActividad","activityLevel");
        db.collection("usuarios").add(usuarios).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }




}
