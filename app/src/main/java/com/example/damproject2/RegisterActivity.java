package com.example.damproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


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

     private FirebaseFirestore db;
     private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /**
         * Declaración atributos interfaz
         */

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
        db= FirebaseFirestore.getInstance();

        /**
         * Asignación valores Spinners Sexo + Actividad
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


        /**
         * Spinner sexo item selected
         */
        spinnerSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexo = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        /**
         * Spinner activity level item selected
         */
        spinnerActivityLvl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                activityLvl = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        /**
         * Botón reset
         */
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //sexo = parent.getItemAtPosition(position).toString();
                        sexo = parent.getItemAtPosition(position).toString();
                        Toast.makeText(parent.getContext(), "Ha seleccionado: " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                        System.out.println("BuenosDias" + sexo);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });
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


                /**
                 * Condicionales para evitar el incorrecto relleno de los datos
                 */
                if (mail.isEmpty() || password.isEmpty() || alias.isEmpty() || altura<1.20 || peso<30.00 || edad<=0 || !password.equals(passwordRepeat) || password.length()<6){
                    if (mail.isEmpty()){
                        Toast.makeText(RegisterActivity.this, "El campo email esta vacío", Toast.LENGTH_SHORT).show();
                    } else if (password.isEmpty()){
                        Toast.makeText(RegisterActivity.this, "El campo contraseña esta vacío", Toast.LENGTH_SHORT).show();
                    } else if (!password.equals(passwordRepeat)){
                        Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    } else if (password.length()<6){
                        Toast.makeText(RegisterActivity.this, "La contraseña no puede contener menos de 6 letras", Toast.LENGTH_SHORT).show();
                    } else if (alias.isEmpty()){
                        Toast.makeText(RegisterActivity.this, "El campo alias esta vacío", Toast.LENGTH_SHORT).show();
                    } else if (altura<1.20) {
                        Toast.makeText(RegisterActivity.this, "La atura no puede ser menos de 120 cm", Toast.LENGTH_SHORT).show();
                    } else if (peso<30.00) {
                        Toast.makeText(RegisterActivity.this, "El peso no pueden ser menos de 30.0 Kg", Toast.LENGTH_SHORT).show();
                    } else if (edad<=0) {
                        Toast.makeText(RegisterActivity.this, "La edad no puede ser menor que 1", Toast.LENGTH_SHORT).show();
                    }
                }
                /**
                 * Si todos los datos son correctos pasamos a crear usuario
                 */
                else {
                    registerUser(mail,password,alias,edad,altura,peso,imc,sexo,activityLvl);
                }




            }
        });
    }

    /**
     * Función registro ---> Introduce datos, en el usuario y en la base de datos.
     * @param mail
     * @param password
     * @param alias
     * @param edad
     * @param altura
     * @param peso
     * @param imc
     * @param sexo
     * @param activityLvl
     */
    private void registerUser (String mail, String password, String alias, int edad, double altura, double peso, double imc, String sexo, String activityLvl){
        mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            //Obtenemos la id del usuario y registramos en un documento sus datos
            public void onComplete(@NonNull Task<AuthResult> task){

                String id = mAuth.getCurrentUser().getUid();
                Map<String , Object> user = new HashMap<>();
                user.put("email" , mail);
                user.put("alias", alias);
                user.put("edad" , edad);
                user.put("id" , id);
                user.put("altura" , altura);
                user.put("peso" , peso);
                user.put("imc", imc);
                user.put("sexo", sexo);
                user.put("nivelActividad", activityLvl);

                Map<String,Object> alimentos = new HashMap<>();



                db.collection("usuarios").document(id).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        Toast.makeText(RegisterActivity.this, "La cuenta a sido creada correctamente", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Error, vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "Error, vuelva a hacerlo", Toast.LENGTH_SHORT).show();
            }
        });
    }
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
     * Creación menu_home.xml
     *
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_register, menu);
        return true;

    }

    /**
     * Item CONFIG menú == Reset activity or Return to login
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {


            case R.id.menuItem1_register:

                Intent pasarPantalla = new Intent(RegisterActivity.this, RegisterActivity.class);
                startActivity(pasarPantalla);

                return true;

            case R.id.menuItem2_register:

                pasarPantalla = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(pasarPantalla);

                return true;

        }

        return false;
    }


}

