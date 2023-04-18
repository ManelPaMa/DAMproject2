package com.example.damproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EntrenamientosActivity extends AppCompatActivity {

    protected EditText caja1;
    protected Button boton_entrenamiento_seleccionado;
    protected Button boton_escalada;
    protected Button boton_crossfit;
    protected Button boton_remo;
    protected Button boton_padel;
    protected Button boton_tenis;
    protected Button boton_palas;
    protected Button boton_natacion;
    protected Button boton_gimnasio;
    protected Button boton_skate;
    protected Button boton_correr_monte;
    protected Button boton_correr_pista;
    protected Button boton_trx;
    protected Button boton_ciclismo_monte;
    protected Button boton_ciclismo;
    protected Button boton_barrenar;
    protected Button boton_surf;
    protected Button boton_patinaje;
    protected Button boton_triatlon;
    protected Button boton_volver;

    private String contenidoCaja1;
    private Intent pasarPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamientos);

        caja1 = (EditText) findViewById(R.id.caja1_entrenamientos);
        boton_entrenamiento_seleccionado = (Button) findViewById(R.id.boton_entrenamiento_seleccionado_entrenamientos);
        boton_escalada = (Button) findViewById(R.id.boton_escalada_entrenamientos);
        boton_crossfit = (Button) findViewById(R.id.boton_crossfit_entrenamientos);
        boton_remo = (Button) findViewById(R.id.boton_remo_entrenamientos);
        boton_padel = (Button) findViewById(R.id.boton_padel_entrenamientos);
        boton_tenis = (Button) findViewById(R.id.boton_tenis_entrenamientos);
        boton_palas = (Button) findViewById(R.id.boton_palas_entrenamientos);
        boton_natacion = (Button) findViewById(R.id.boton_natacion_entrenamientos);
        boton_gimnasio = (Button) findViewById(R.id.boton_gimnasio_entrenamientos);
        boton_skate = (Button) findViewById(R.id.boton_skate_entrenamientos);
        boton_correr_pista = (Button) findViewById(R.id.boton_correr_pista_entrenamientos);
        boton_correr_monte = (Button) findViewById(R.id.boton_correr_monte_entrenamientos);
        boton_trx = (Button) findViewById(R.id.boton_trx_entrenamientos);
        boton_ciclismo_monte = (Button) findViewById(R.id.boton_ciclismo_monte_entrenamientos);
        boton_ciclismo = (Button) findViewById(R.id.boton_ciclismo_entrenamientos);
        boton_barrenar = (Button) findViewById(R.id.boton_barrenar_entrenamientos);
        boton_surf = (Button) findViewById(R.id.boton_surf_entrenamientos);
        boton_patinaje = (Button) findViewById(R.id.boton_patinaje_entrenamientos);
        boton_triatlon = (Button) findViewById(R.id.boton_triatlon_entrenamientos);
        boton_volver = (Button) findViewById(R.id.boton_volver_entrenamientos);

        contenidoCaja1 = caja1.getText().toString();

        boton_entrenamiento_seleccionado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPantalla = new Intent(EntrenamientosActivity.this, EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_escalada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "escalada";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_crossfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "crossfit";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_remo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "remo";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_padel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "padel";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_tenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "tenis";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_palas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "palas";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_natacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "natacion";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_gimnasio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "gimnasio";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_skate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "skate";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_correr_pista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "correr_pista";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_correr_monte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "correr_monte";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_trx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "trx";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_ciclismo_monte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "ciclismo_monte";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_ciclismo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "ciclismo";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_barrenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "barrenar";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_surf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "surf";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_patinaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "patinaje";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_triatlon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = "triatlon";
                pasarPantalla = new Intent(EntrenamientosActivity.this,EntrenamientoDatosActivity.class);
                pasarPantalla.putExtra("DEPORTE",contenidoCaja1);
                finish();
                startActivity(pasarPantalla);
            }
        });

        boton_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarPantalla = new Intent(EntrenamientosActivity.this, InicioEntrenos.class);
                finish();
                startActivity(pasarPantalla);
            }
        });


    }
}