package com.example.mohaexamen_2ev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private QuimicosSQLiteHelper dbHelper;
    private SQLiteDatabase db;

    int contador;

    TextView lblNumConsultas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView lblNumConsultas  = findViewById(R.id.lblNumConsultas);
        Button btnConsultar = findViewById(R.id.btnConsultar);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSalir = findViewById(R.id.btnSalir);

        lblNumConsultas = findViewById(R.id.lblNumConsultas);



    }

    public void searchActivity(View view) {
        Intent intent = new Intent(this, ActividadConsulta.class);
        startActivityForResult(intent, 1234);
//        Bundle extras = getIntent().getExtras();
//        contador = extras.getInt("CONTADOR");
//        lblNumConsultas.setText(contador);

    }


    public void loginActivity(View view) {
        Intent intent = new Intent(this, ActividadLogin.class);
        startActivity(intent);
//        startActivityForResult(intent, 5678);

    }

    public void salir(View view) {
        //despedir
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Ya te vas!");
        alertDialogBuilder.setMessage("Adios...");

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //acabar
                finish();
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.show();

    }
}