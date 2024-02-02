package com.example.mohaexamen_2ev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private QuimicosSQLiteHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView lblNumConsultas  = findViewById(R.id.lblNumConsultas);
        Button btnConsultar = findViewById(R.id.btnConsultar);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSalir = findViewById(R.id.btnSalir);



    }

    public void searchActivity(View view) {
        Intent intent = new Intent(this, ActividadConsulta.class);
        startActivityForResult(intent);
    }

    private void startActivityForResult(Intent intent) {
    }
}