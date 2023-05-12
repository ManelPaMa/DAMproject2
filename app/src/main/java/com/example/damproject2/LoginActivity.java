package com.example.damproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    /**
     * Atributos pantalla login
     */
    protected ImageView image1_main;
    protected TextView txtV1_main;
    protected EditText mail_main;
    protected EditText password_main;
    protected Button btnLogin_main;
    protected TextView txtV2_main;
    protected Button btnRegister_main;
    protected TextView txtV3_main;

    protected String mail;
    protected String password;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        /**
         * Instanciación de atributos interfaz
         */
        image1_main = (ImageView) findViewById(R.id.imageView1_main);
        mail_main = (EditText) findViewById(R.id.inputMail_main);
        password_main = (EditText) findViewById(R.id.inputPassword_main);
        btnLogin_main = (Button) findViewById(R.id.btnLogin_main);
        txtV2_main = (TextView) findViewById(R.id.textView2_main);
        btnRegister_main = (Button) findViewById(R.id.btnRegister_main);
        txtV3_main = (TextView) findViewById(R.id.textView3_main);

        mAuth = FirebaseAuth.getInstance();


        this.setTitle("On your track LOGIN");


        /**
         * Actividad Botón Login == OK --> HomeActivity
         * (EN CONSTRUCCIÓN) --> FALTA QUERY GET ELEMENTS
         */
        btnLogin_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail = mail_main.getText().toString();
                password = password_main.getText().toString();


                if (mail.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Las cajas de text están vacías", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(mail,password);
                }



            }
        });
        /**
         * Botón Register --> intent to RegisterActivity
         */
        btnRegister_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pasarPantalla = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(pasarPantalla);
            }
        });



    }

    /**
     * Función para comprobar el login --> Busca usuario en FireBaseAUTH
     * @param mail
     * @param password
     */
    private void loginUser (String mail, String password){
        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    Toast.makeText(LoginActivity.this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Error, los datos no son correctos", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Los datos introducidos no son correctos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}