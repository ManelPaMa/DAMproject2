package com.example.damproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.damproject2.Objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditUserActivity extends AppCompatActivity {

    //Interfaz gráfica
    protected TextView txt1_user, txtSex_user, txtActivity_user, txt2_user;
    protected EditText alias_user, oldPassword_user, newPassword_user, edad_user, altura_user, peso_user;
    protected Spinner spinnerSex_user, spinnerActivity_user;
    protected Button btn1_user, btnChange_editText;
    protected Switch sw1_editUser, sw2_editUser, sw3_editUser, sw4_editUser, sw5_editUser;

    //Firebase
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser fUser;

    //Variables uso
    protected User usuario = new User();
    private String user , alias,oldPassword, newPassword, sexo, activityLvl;
    private int edad  = 0;
    private double altura, peso, imc = 0.00;


    private ArrayList<String> listSexo;
    private ArrayList <String> listActivity;

    private ArrayAdapter<String> adapterSexo;
    private ArrayAdapter<String> adapterActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        // Instancia database
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser userChange = FirebaseAuth.getInstance().getCurrentUser();
        user = mAuth.getCurrentUser().getUid();

        // Elementos de la interfaz gráfica instancias
        txt1_user = (TextView) findViewById(R.id.txtV1_editUser);
        txt2_user = (TextView) findViewById(R.id.txtV2_editUser);
        txtSex_user = (TextView) findViewById(R.id.txtSexo_editUser);
        txtActivity_user = (TextView) findViewById(R.id.txtActivdad_editUser);
        alias_user = (EditText) findViewById(R.id.alias_editUser);
        oldPassword_user = (EditText) findViewById(R.id.oldPassword_editUser);
        newPassword_user = (EditText) findViewById(R.id.newPassword_editUSer);
        edad_user = (EditText) findViewById(R.id.edad_editUser);
        altura_user = (EditText) findViewById(R.id.altura_editUser);
        peso_user = (EditText) findViewById(R.id.peso_editUser);
        spinnerSex_user = (Spinner) findViewById(R.id.spinnerSexo_editUser);
        spinnerActivity_user = (Spinner) findViewById(R.id.spinnerActividad_editUser);
        btn1_user = (Button) findViewById(R.id.btnSend_editUser);
        sw1_editUser = (Switch) findViewById(R.id.switch1_editUser);
        sw2_editUser = (Switch) findViewById(R.id.switch2_editUser);
        sw3_editUser = (Switch) findViewById(R.id.switch3_editUser);
        sw4_editUser = (Switch) findViewById(R.id.switch4_editUser);
        sw5_editUser = (Switch) findViewById(R.id.switch5_editUser);
        btnChange_editText = (Button) findViewById(R.id.btnChangePass_editUser);


        // Barras not enabled
        alias_user.setEnabled(false);
        oldPassword_user.setEnabled(false);
        newPassword_user.setEnabled(false);
        edad_user.setEnabled(false);
        altura_user.setEnabled(false);
        peso_user.setEnabled(false);



        getUserInfo(user);

        /**
         * Asignación valores Spinners Sexo + Actividad
         */
        listSexo = new ArrayList<>();
        listSexo.add(" ");
        listSexo.add("Hombre");
        listSexo.add("Mujer");

        adapterSexo = new ArrayAdapter<String>(EditUserActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listSexo);
        spinnerSex_user.setAdapter(adapterSexo);

        listActivity = new ArrayList<>();
        listActivity.add(" ");
        listActivity.add("Sedentario");
        listActivity.add("Atlético");

        adapterActivity = new ArrayAdapter<String>(EditUserActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listActivity);
        spinnerActivity_user.setAdapter(adapterActivity);

        /**
         * Spinner sexo item selected
         */
        spinnerSex_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexo = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sexo = " ";
            }

        });
        /**
         * Spinner activity level item selected
         */
        spinnerActivity_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                activityLvl = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                activityLvl = " ";
            }

        });

        /**
         * Switches ---> is true barra relacionada is enabled
         */
        sw1_editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw1_editUser.isChecked()){
                    alias_user.setEnabled(true);
                } else {
                    alias_user.setEnabled(false);
                }

            }
        });

        sw2_editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw2_editUser.isChecked()){
                    oldPassword_user.setEnabled(true);
                    newPassword_user.setEnabled(true);
                } else {
                    oldPassword_user.setEnabled(false);
                    newPassword_user.setEnabled(false);
                }

            }
        });

        sw3_editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw3_editUser.isChecked()){
                    edad_user.setEnabled(true);
                } else {
                    edad_user.setEnabled(false);
                }

            }
        });

        sw4_editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw4_editUser.isChecked()){
                    altura_user.setEnabled(true);
                } else {
                    altura_user.setEnabled(false);
                }

            }
        });

        sw5_editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw5_editUser.isChecked()){
                    peso_user.setEnabled(true);
                } else {
                    peso_user.setEnabled(false);
                }

            }
        });

        /**
         * Button confirmar cambios, añade los datos al objeto usuario y llama al método updateUser
         */
        btn1_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alias = alias_user.getText().toString();


                try {
                    edad = Integer.parseInt(edad_user.getText().toString());
                } catch (Exception e){

                }

                try {
                    altura = Double.parseDouble(altura_user.getText().toString());
                } catch (Exception e ) {

                }
                try {
                    peso = Double.parseDouble(peso_user.getText().toString());
                } catch (Exception e){

                }


                // Solo realiza el set de los datos que no están vacios
                if (!alias.isEmpty()){
                    usuario.setAlias(alias);
                } if (edad!=0){
                    usuario.setEdad(edad);
                } if (altura !=0.00){
                    usuario.setAltura(altura);
                } if (peso !=0.00){
                    usuario.setPeso(peso);
                } if (!sexo.equals(" ")){
                    usuario.setSexo(sexo);
                } if (!activityLvl.equals(" ")){
                    usuario.setActivityLvl(activityLvl);
                }
                imc = usuario.getPeso()/ (usuario.getAltura()*usuario.getAltura());
                usuario.setImc(imc);
                try {
                    //Realiza el update y vuelve a Home
                    updateUser();
                    Intent pasarpantalla = new Intent(EditUserActivity.this, HomeActivity.class);
                    startActivity(pasarpantalla);
                    finish();
                    Toast.makeText(EditUserActivity.this, "Los cambios se han realizado correctamente", Toast.LENGTH_SHORT).show();

                } catch (Exception e){
                    Toast.makeText(EditUserActivity.this, "Algo salio mal, porfavor vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /**
         * Button cambiar contraseña acción --> usa método changePassword
         */
        btnChange_editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oldPassword = oldPassword_user.getText().toString();
                newPassword = newPassword_user.getText().toString();

                if (!oldPassword.isEmpty() && !newPassword.isEmpty())
                {
                    if (newPassword.length()>= 6 ){

                        try {
                            changePassword(userChange, oldPassword, newPassword);
                            Intent pasarPantalla = new Intent(EditUserActivity.this, HomeActivity.class);
                            startActivity(pasarPantalla);
                            finish();
                            Toast.makeText(EditUserActivity.this, "La contraseña a sido correctamente cambiada", Toast.LENGTH_SHORT).show();
                        } catch (Exception e){
                            Toast.makeText(EditUserActivity.this, "No se pudo cambiar la contraseña, la contraseña actual no es correcta o la nueva es menor a 6 carácteres", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
    }

    /**
     * Método getInfoUser --> asigna info de la base de datos al objeto usuario, info antes del cambio
     * @param user
     */
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

    /**
     * Método cambiar contraseña compara la vieja contraseña y si coincide la cambia por la nueva
     * @param userChange
     * @param oldPassword
     * @param newPassword
     */
    private void changePassword (FirebaseUser userChange, String oldPassword, String newPassword)
    {
        AuthCredential credential = EmailAuthProvider.getCredential(usuario.getEmail(), oldPassword );

        userChange.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    userChange.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                Toast.makeText(EditUserActivity.this, "No se pudo cambiar la contraseña, alguno de los datos no es correcto", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(EditUserActivity.this, "No se pudo cambiar la contraseña, alguno de los datos no es correcto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Vuelve a introducir los datos del usuario a la base de datos, los datos son después de los inputs que ha hecho el usuario
     */
    private void updateUser (){
        DocumentReference docRef = db.collection("usuarios").document(user);
        docRef.update("alias", usuario.getAlias(),
                "edad", usuario.getEdad(),
                "altura", usuario.getAltura(),
                "peso", usuario.getPeso(),
                "imc", usuario.getImc(),
                "sexo", usuario.getSexo(),
                "nivelActividad", usuario.getActivityLvl())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    /**
     * Creación menu_edituser.xml
     *
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edituser, menu);
        return true;

    }

    /**
     * Item CONFIG menú == Return to Home or InfoActivity
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.menuItem1_editUser:

                /**
                 * Alert dialog con información para el uso de la activity
                 */
                AlertDialog.Builder alert = new AlertDialog.Builder(EditUserActivity.this);
                alert.setMessage("Para editar los campos de usuario hay que activar el dato que quieras cambiar, los que no sean rellenado cuando pusles el botón enivar van a seguir con los anteriores valores")
                        .setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });

                AlertDialog title = alert.create();
                title.setTitle("Información de uso, editar Usuario");
                title.show();

                return true;

            /**
             * Return to home
             */
            case R.id.menuItem2_editUser:

                Intent pasarPantalla = new Intent(EditUserActivity.this, HomeActivity.class);
                startActivity(pasarPantalla);
                this.finish();
                return true;

        }

        return false;
    }



}