package com.example.mohaexamen_2ev;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ActividadLogin extends AppCompatActivity {

    ImageView imgError;
    EditText txtNombre, txtContrasena;
    Button btnLogin, btnCancelar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_login);

        imgError = findViewById(R.id.imgError);

        imgError.setVisibility(View.INVISIBLE);

        txtNombre = findViewById(R.id.txtNombre);
        txtContrasena = findViewById(R.id.txtContrasena);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnLogin = findViewById(R.id.btnLogin);




    }

    public void login(View view) {
        String nom = txtNombre.getText().toString();
        String pass = txtContrasena.getText().toString();
        if (nom.isEmpty() || pass.isEmpty() ||nom.equals("")|| pass.equals("")) {
            Toast.makeText(ActividadLogin.this, "Deberia rellenar los campos", Toast.LENGTH_SHORT).show();
        } else {
            if (!nom.equals("admin") || !pass.equals("admin")) {
                //imagen habilitar
                imgError.setVisibility(view.getVisibility());
                Toast.makeText(ActividadLogin.this, "Login y/o contraseña son incorrectos", Toast.LENGTH_SHORT).show();
            } else {
                //imagen deshabilitar
                imgError.setVisibility(View.INVISIBLE);
                Toast.makeText(ActividadLogin.this, "Login y contraseña correctos", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ActividadAdministracion.class);
                startActivity(intent);
            }
        }
    }

    //volver
    public void cancelar(View view) {

        finish();
    }
}