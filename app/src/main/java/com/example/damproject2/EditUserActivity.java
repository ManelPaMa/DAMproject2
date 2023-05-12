package com.example.damproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.damproject2.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EditUserActivity extends AppCompatActivity {

    protected TextView txt1_user, txtSex_user, txtActivity_user;
    protected EditText alias_user, mail_user, oldPassword_user, newPassword_user, edad_user, altura_user, peso_user;
    protected Spinner spinnerSex_user, spinnerActivity_user;
    protected Button btn1_user;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser fUser;

    protected User usuario = new User();
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        // Instancia database
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser().getUid();

        txt1_user = (TextView) findViewById(R.id.txtV1_editUser);
        txtSex_user = (TextView) findViewById(R.id.txtSexo_editUser);
        txtActivity_user = (TextView) findViewById(R.id.txtActivdad_editUser);
        alias_user = (EditText) findViewById(R.id.alias_editUser);
        mail_user = (EditText) findViewById(R.id.mail_editUser);
        oldPassword_user = (EditText) findViewById(R.id.oldPassword_editUser);
        newPassword_user = (EditText) findViewById(R.id.newPassword_editUSer);
        edad_user = (EditText) findViewById(R.id.edad_editUser);
        altura_user = (EditText) findViewById(R.id.altura_editUser);
        peso_user = (EditText) findViewById(R.id.peso_editUser);
        spinnerSex_user = (Spinner) findViewById(R.id.spinnerSexo_editUser);
        spinnerActivity_user = (Spinner) findViewById(R.id.spinnerSexo_editUser);
        btn1_user = (Button) findViewById(R.id.btnSend_editUser);

        getUserInfo(user);


    }

    public void getUserInfo (String user) {

        CollectionReference ref = db.collection("usuarios");

        ref.whereEqualTo("id", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        usuario.setEmail(document.get("email").toString());
                        usuario.setAlias(document.get("alias").toString());
                        usuario.setSexo(document.get("sexo").toString());
                        usuario.setActivityLvl(document.get("nivelActividad").toString());
                        usuario.setEdad(Integer.parseInt(document.get("edad").toString()));
                        usuario.setPeso(Double.parseDouble(document.get("peso").toString()));
                        usuario.setImc(Double.parseDouble(document.get("imc").toString()));
                        usuario.setAltura(Double.parseDouble(document.get("altura").toString()));

                    }
                } else {
                    ;
                }

            }


        });
    }
}