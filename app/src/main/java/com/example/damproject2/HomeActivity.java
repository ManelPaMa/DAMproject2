package com.example.damproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.damproject2.Objects.Alimento;
import com.example.damproject2.Objects.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HomeActivity extends AppCompatActivity {

    //Variables interfaz
    protected TextView txtV1_home, txtV2_home;
    protected Button btn1_home, btn2_home, btn3_home, btn4_home, btn5_home,btn6_home;
    protected ImageView image1_home;
    protected ProgressBar progress1_home;

    //Variables database
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser fUser;

    private Date dateToday = new Date();
    private String fecha, user;
    private int num1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Instancia database
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser().getUid();

        // Instancia elementos interfaz

        txtV1_home = (TextView) findViewById(R.id.textView1_home);
        txtV2_home = (TextView) findViewById(R.id.textView2_home);
        btn1_home = (Button) findViewById(R.id.button1_home);
        btn2_home = (Button) findViewById(R.id.button2_home);
        btn3_home = (Button) findViewById(R.id.button3_home);
        btn4_home = (Button) findViewById(R.id.button4_home);
        btn5_home = (Button) findViewById(R.id.button5_home);
        btn6_home = (Button) findViewById(R.id.button6_home);
        image1_home = (ImageView) findViewById(R.id.imageView1_home);
        progress1_home = (ProgressBar) findViewById(R.id.progressBar1_home);

        fecha = new SimpleDateFormat("dd-MM-yyyy").format(dateToday);
        txtV1_home.setText(fecha);


            AlertDialog.Builder alert = new AlertDialog.Builder(HomeActivity.this);
            alert.setMessage("¡¡Bienvenid@ de nuevo, que tengas un buen día!!")
                    .setCancelable(false).setPositiveButton("Ir al menú", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });

            AlertDialog title = alert.create();
            title.setTitle("Mensaje");
            title.show();


        //Mover la fecha dia -1
        btn1_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1 = -1;
                fecha = new SimpleDateFormat("dd-MM-yyyy").format(moverDia1(num1,fecha));
                txtV1_home.setText(fecha);
            }
        });
        //Mover la fecha dia +1
        btn2_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            num1 = 1;
            fecha = new SimpleDateFormat("dd-MM-yyyy").format(moverDia1(num1,fecha));
            txtV1_home.setText(fecha);
            }
        });
        // Button to CrearComidas
        btn3_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pasarPantalla = new Intent(HomeActivity.this, CrearComidasActivity.class);
                pasarPantalla.putExtra("fecha", fecha);
                startActivity (pasarPantalla);
            }
        });

        btn4_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pasarPantalla = new Intent(HomeActivity.this, InicioEntrenos.class);
                startActivity(pasarPantalla);
            }
        });

        btn5_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pasarPantalla = new Intent(HomeActivity.this, AddFoodActivity.class);
                startActivity(pasarPantalla);
            }
        });
        btn6_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pasarPantalla = new Intent(HomeActivity.this, EditUserActivity.class);
                startActivity(pasarPantalla);
            }
        });


    }


    public Date moverDia1 (int num1, String fecha)
    {
        DateFormat form = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaAlm = null;
        try {
            fechaAlm = form.parse(fecha);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(fechaAlm);
        calendar.add(Calendar.DAY_OF_YEAR,num1);
        return calendar.getTime();
    }


        /**
         * Creación menu_home.xml
         *
         * @return
         */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;

    }

    /**
     * Item CONFIG menú == go to AddFood or Return to Login
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {


            case R.id.menuItem2_home:

                Intent pasarPantalla = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(pasarPantalla);
                this.finish();
                return true;

        }

        return false;
    }


}
