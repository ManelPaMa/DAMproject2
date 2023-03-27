package com.example.damproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    /**
     * Método resetAllAtributes
     */
    public void resetAllAtributes (){
        inputMail.setText("");
        inputPassword.setText("");
        inputPasswordRpt.setText("");
        inputAlias.setText("");
        inputEdad.setText("");
        inputAltura.setText("");
        inputPeso.setText("");
    }

    /**
     * Atributos + arrays
     */
    protected TextView txtVMail, txtVPassword, txtVPasswordRpt, txtVAlias, txtVAltura, txtVPeso, txtVEdad, txtVSexo, txtVActivityLvl, txtVAlert;
    protected EditText inputMail, inputPassword, inputPasswordRpt, inputAlias, inputEdad, inputAltura, inputPeso;
    protected Spinner spinnerSexo, spinnerActivityLvl;
    protected Button btnReset,btnSend;

    protected String mail, password, passwordRepeat, alias, sexo, activityLvl, mail2;
    protected int edad;
    protected double peso, imc, altura;

     private ArrayList<String> listSexo;
     private ArrayList <String> listActivity;

     private ArrayAdapter<String> adapterSexo;
     private ArrayAdapter<String> adapterActivity;

     private FireBase db;
     private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /**
         * Declaración atributos interfaz
         */
        txtVMail = (TextView) findViewById(R.id.textViewEmail_register);
        txtVPassword = (TextView) findViewById(R.id.textViewPassword_register);
        txtVPasswordRpt = (TextView) findViewById(R.id.textViewRepeatPassword_register);
        txtVAlias= (TextView) findViewById(R.id.textViewAlias_register);
        txtVAltura = (TextView) findViewById(R.id.textViewAltura_register);
        txtVPeso = (TextView) findViewById(R.id.textViewPeso_register);
        txtVEdad = (TextView) findViewById(R.id.textViewEdad_register);
        txtVSexo = (TextView) findViewById(R.id.textViewSexo_register);
        txtVActivityLvl = (TextView) findViewById(R.id.textViewActivityLevel_register);
        txtVAlert = (TextView) findViewById(R.id.textViewAlert_register);

        inputMail = (EditText) findViewById(R.id.inputEmail_register);
        inputPassword = (EditText) findViewById(R.id.inputPassword_register);
        inputPasswordRpt = (EditText) findViewById(R.id.inputRepeatPassword_register);
        inputAlias = (EditText) findViewById(R.id.inputAlias_register);
        inputEdad = (EditText) findViewById(R.id.inputEdad_register);
        inputAltura = (EditText) findViewById(R.id.inputAltura_register);
        inputPeso = (EditText) findViewById(R.id.inputPeso_register);

        spinnerSexo = (Spinner) findViewById(R.id.spinnerSexo_register);
        spinnerActivityLvl = (Spinner) findViewById(R.id.spinnerActivityLevel_register);

        btnReset = (Button) findViewById(R.id.btnReset_register);
        btnSend = (Button) findViewById(R.id.btnRegister_register);

        mAuth = FirebaseAuth.getInstance();
        db= new FireBase();

        /**
         * Asignación valores Spinners Sexo + Atividad
         */
        listSexo = new ArrayList<>();
        listSexo.add("Hombre");
        listSexo.add("Mujer");

        adapterSexo = new ArrayAdapter<String>(RegisterActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listSexo);
        spinnerSexo.setAdapter(adapterSexo);

        listActivity = new ArrayList<>();
        listActivity.add("Sedentario");
        listActivity.add("Atlético");

        adapterActivity = new ArrayAdapter<String>(RegisterActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listActivity);
        spinnerActivityLvl.setAdapter(adapterActivity);

       /* @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                reload();
            }
        }*/

        /**
         * Botón reset
         */
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetAllAtributes();
            }
        });
        /**
         * Botón send --> Almacenar info All editText + comprobar que es correcta --> Crear un archivo DataBase con la info
         * (EN CONSTRUCCIÓN)
         */
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mail = inputMail.getText().toString();
                password = inputPassword.getText().toString();
                passwordRepeat = inputPasswordRpt.getText().toString();
                alias = inputAlias.getText().toString();
                try {
                    edad = Integer.parseInt(inputEdad.getText().toString());
                } catch (Exception e){
                    resetAllAtributes();
                    txtVAlert.setText("La Edad tiene que ser un número entero");
                }
                try {
                    altura = Double.parseDouble(inputAltura.getText().toString());
                } catch (Exception e){
                    resetAllAtributes();
                    txtVAlert.setText("La altura tiene que ser en metros ej: 1.85");
                }
                try {
                    peso = Double.parseDouble(inputPeso.getText().toString());
                } catch (Exception e){
                    resetAllAtributes();
                    txtVAlert.setText("El peso que ser un número");
                }

                imc = peso / (altura*altura);

                spinnerSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sexo = parent.getItemAtPosition(position).toString();
                        System.out.println("--> " + sexo);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });

                //db.getDocumentID(mail);
                Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT).show();

                if (mail.equals("") || password.equals("") || alias.equals("") || altura<1.20 || peso<30.00 || edad<=0 || !password.equals(passwordRepeat) ){
                    if (mail.equals("")){
                        Toast.makeText(RegisterActivity.this, "El campo email esta vacío", Toast.LENGTH_SHORT).show();
                    } else if (password.equals("")){
                        Toast.makeText(RegisterActivity.this, "El campo contraseña esta vacío", Toast.LENGTH_SHORT).show();
                    } else if (!password.equals(passwordRepeat)){
                        Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    } else if (alias.equals("")){
                        Toast.makeText(RegisterActivity.this, "El campo alias esta vacío", Toast.LENGTH_SHORT).show();
                    } else if (altura<1.20) {
                        Toast.makeText(RegisterActivity.this, "La atura no puede ser menos de 120 cm", Toast.LENGTH_SHORT).show();
                    } else if (peso<30.00) {
                        Toast.makeText(RegisterActivity.this, "El peso no pueden ser menos de 30.0 Kg", Toast.LENGTH_SHORT).show();
                    } else if (edad<=0) {
                        Toast.makeText(RegisterActivity.this, "La edad no puede ser menor que 1", Toast.LENGTH_SHORT).show();
                    }
                } /*else if (mail.equals(mail2)) {
                    Toast.makeText(RegisterActivity.this, "El mail introducido ya esta registrado, por favor use otro", Toast.LENGTH_SHORT).show(); }*/
                /*else {

                mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnSuccessListener<AuthResult>() {

                    public void onSuccess(AuthResult authResult){

                        HashMap<String , Object> user = new HashMap<>();
                        user.put("email" , mail);
                        user.put("alias", alias);
                        user.put("edad" , edad);
                        user.put("id" , mAuth.getCurrentUser().getUid());
                        user.put("altura" , altura);
                        user.put("peso" , peso);
                        user.put("imc", imc);
                        user.put("sexo", "hombre");
                        user.put("nivelActividad", "sedentario");

                        mRootRef.child("usuarios").child(mAuth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccesful()) {

                                }
                            }
                        });
                    }

                    }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                }*/

            }
        });
    }
}

