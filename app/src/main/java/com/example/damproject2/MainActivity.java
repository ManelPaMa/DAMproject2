package com.example.damproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.ktx.Firebase;

import java.util.Collection;

public class MainActivity extends AppCompatActivity {

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

    private FirebaseFirestore db;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * Instanciación de atributos interfaz
         */
        image1_main = (ImageView) findViewById(R.id.imageView1_main);
        txtV1_main = (TextView) findViewById(R.id.textView1_main);
        mail_main = (EditText) findViewById(R.id.inputMail_main);
        password_main = (EditText) findViewById(R.id.inputPassword_main);
        btnLogin_main = (Button) findViewById(R.id.btnLogin_main);
        txtV2_main = (TextView) findViewById(R.id.textView2_main);
        btnRegister_main = (Button) findViewById(R.id.btnRegister_main);
        txtV3_main = (TextView) findViewById(R.id.textView3_main);

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


                /*if (mail.equals("mail") && password.equals("password")){
                    Intent pasarPantalla= new Intent(MainActivity.this, HomeActivity.class);
                    finish();
                    startActivity(pasarPantalla);
                }
                else {
                    mail_main.setText("");
                    password_main.setText("");
                    txtV3_main.setText("El email o la contraseña no son correctos");

                }*/



            }
        });
        /**
         * Botón Register --> intent to RegisterActivity
         */
        btnRegister_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pasarPantalla = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(pasarPantalla);
            }
        });



    }
}