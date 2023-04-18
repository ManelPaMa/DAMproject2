package com.example.damproject2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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
    protected RecyclerView recycled1_home, recycled2_home;
    protected TextView txtV1_home, txtV2_home, txtV3_home, txtV4_home;
    protected Button btn1_home, btn2_home, btn3_home, btn4_home;
    protected ImageView image1_home;
    protected ProgressBar progress1_home;

    //Variables database
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser fUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Instancia database
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Instancia elementos interfaz
        recycled1_home = (RecyclerView) findViewById(R.id.recyclerView1_home);
        recycled2_home = (RecyclerView) findViewById(R.id.recyclerView2_home);
        txtV1_home = (TextView) findViewById(R.id.textView1_home);
        txtV2_home = (TextView) findViewById(R.id.textView2_home);
        txtV3_home = (TextView) findViewById(R.id.textView3_home);
        txtV4_home = (TextView) findViewById(R.id.textView4_home);
        btn1_home = (Button) findViewById(R.id.button1_home);
        btn2_home = (Button) findViewById(R.id.button2_home);
        btn3_home = (Button) findViewById(R.id.button3_home);
        btn4_home = (Button) findViewById(R.id.button4_home);
        image1_home = (ImageView) findViewById(R.id.imageView1_home);
        progress1_home = (ProgressBar) findViewById(R.id.progressBar1_home);

        // Button to CrearComidas
        btn3_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pasarPantalla = new Intent(HomeActivity.this, CrearComidasActivity.class);
                startActivity (pasarPantalla);
            }
        });


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
