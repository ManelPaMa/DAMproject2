package com.example.damproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

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

    protected String mail, password, passwordRepeat, alias, sexo, activityLvl;
    protected int altura, edad;
    protected float peso, imc;

     private ArrayList<String> listSexo;
     private ArrayList <String> listActivity;

     private ArrayAdapter<String> adapterSexo;
     private ArrayAdapter<String> adapterActivity;

     private FireBase db;

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

                db.registerUser("Jose@gmail.com", "1234", "Jose", 185, 85.0f, 23.4f, "Hombre", "Sedentario" );
                /*mail = inputMail.getText().toString();
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
                    altura = Integer.parseInt(inputAltura.getText().toString());
                } catch (Exception e){
                    resetAllAtributes();
                    txtVAlert.setText("La altura tiene que ser un número entero");
                }
                try {
                    peso = Float.parseFloat(inputPeso.getText().toString());
                } catch (Exception e){
                    resetAllAtributes();
                    txtVAlert.setText("El peso que ser un número");
                }

                spinnerSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sexo = parent.getItemAtPosition(position).toString();
                        System.out.println("--> " + sexo);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });*/


            }
        });
    }
}