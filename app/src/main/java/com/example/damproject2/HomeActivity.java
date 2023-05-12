package com.example.damproject2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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
    private String fecha;
    private int num1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Instancia database
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Instancia elementos interfaz

        txtV1_home = (TextView) findViewById(R.id.textView1_home);
        txtV2_home = (TextView) findViewById(R.id.textView2_home);
        btn1_home = (Button) findViewById(R.id.button1_home);
        btn2_home = (Button) findViewById(R.id.button2_home);
        btn3_home = (Button) findViewById(R.id.button3_home);
        btn4_home = (Button) findViewById(R.id.button4_home);
        btn3_home = (Button) findViewById(R.id.button5_home);
        btn4_home = (Button) findViewById(R.id.button6_home);
        image1_home = (ImageView) findViewById(R.id.imageView1_home);
        progress1_home = (ProgressBar) findViewById(R.id.progressBar1_home);

        fecha = new SimpleDateFormat("dd-MM-yyyy").format(dateToday);
        txtV1_home.setText(fecha);


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


            case R.id.menuItem1_home:

               Intent pasarPantalla = new Intent(HomeActivity.this, AddFoodActivity.class);
               startActivity(pasarPantalla);

                return true;

            case R.id.menuItem2_home:

                pasarPantalla = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(pasarPantalla);

                return true;

        }

        return false;
    }


}
