package com.example.mohaexamen_2ev;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActividadAdministracion extends AppCompatActivity {

    EditText txtNom,txtSimobo,txtNum_ato,txtEstado;
    Button btnInsertar,btnModificar,btnBorrar,btnVolver;

    private QuimicosSQLiteHelper dbHelper;
    private SQLiteDatabase db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_administracion);

        txtNom = findViewById(R.id.txtNom);
        txtSimobo = findViewById(R.id.txtSimobo);
        txtNum_ato = findViewById(R.id.txtNum_ato);
        txtEstado = findViewById(R.id.txtEstado);

        btnInsertar = findViewById(R.id.btnInsertar);
        btnModificar = findViewById(R.id.btnModificar);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnVolver = findViewById(R.id.btnVolver);

        // Inicializa el dbHelper
        dbHelper = new QuimicosSQLiteHelper(this, "Elementos.db", null, 1);

        // Obtén una instancia de la base de datos
        db = dbHelper.getReadableDatabase();

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nom = txtNom.getText().toString();
                String sim = txtSimobo.getText().toString();
                String estado = txtEstado.getText().toString();

                String n = txtNum_ato.getText().toString(); //parse
                int num = 0;
                try {
                    num = Integer.parseInt(n);
                }  catch (NumberFormatException e) {
                    Toast.makeText(ActividadAdministracion.this, "El numero automico no puede ser una palabra", Toast.LENGTH_SHORT).show();
                }
                if (nom.isEmpty() || sim.isEmpty()|| estado.isEmpty()) {
                    Toast.makeText(ActividadAdministracion.this, "hAY QUE RELLENAR TODOS LOS CAMPOS!", Toast.LENGTH_SHORT).show();

                } else {
                    //Comprobar se ya existe nom
                    Cursor cursor = db.rawQuery("SELECT nombre FROM Elementos WHERE nombre = ?", new String[]{nom});
                    //Caso hay elemento
                    if (cursor.moveToFirst()) {
                        //Lanzar dialogo
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActividadAdministracion.this);
                        alertDialogBuilder.setTitle("Error!");
                        alertDialogBuilder.setMessage("Nombre de elemento ya existe en la base de datos");
                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.show();

                    } else {
                        //Ahora a insertar
                        String sqlInsert = "INSERT INTO Elementos (nombre, simbolo, num_atomico, estado) VALUES ('"
                                + nom + "', '"
                                + sim + "', '"
                                + num + "', "
                                + estado + ") ";
                        //Ejecutar
                        try {
                            db.execSQL(sqlInsert);
                            Toast.makeText(ActividadAdministracion.this, "Elemento agregado correctamente", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(ActividadAdministracion.this, "Error al agregar el elemento: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                }
                    cursor.close();
                }
        }
        });


        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = txtNom.getText().toString();
                String sim = txtSimobo.getText().toString();
                String estado = txtEstado.getText().toString();
                String n = txtNum_ato.getText().toString(); //parse
                int num = 0;
                try {
                    num = Integer.parseInt(n);
                } catch (NumberFormatException e) {
                        Toast.makeText(ActividadAdministracion.this, "El numero automico no puede ser una palabra", Toast.LENGTH_SHORT).show();
                }
                if (sim.isEmpty()|| estado.isEmpty() || nom.isEmpty() || n.isEmpty()) {
                    Toast.makeText(ActividadAdministracion.this, "hAY QUE RELLENAR TODOS LOS CAMPOS!", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor cursor = db.rawQuery("SELECT count(*) FROM Elementos WHERE nombre = ?", new String[]{nom});
                    if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
                        ContentValues valores = new ContentValues();
                        valores.put("nombre", nom);
                        valores.put("simbolo", sim);
                        valores.put("num_atomico", num);
                        valores.put("estado", estado);
                        int filasActualizadas = db.update("Elementos", valores, "nombre = ?", new String[]{nom});

                        if (filasActualizadas > 0) {
                            Toast.makeText(ActividadAdministracion.this, "elemento actualizado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ActividadAdministracion.this, "Error al actualizar el elemento", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(ActividadAdministracion.this, "elemento no existe!", Toast.LENGTH_SHORT).show();
                        //error
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActividadAdministracion.this);
                        alertDialogBuilder.setTitle("Error");
                        alertDialogBuilder.setMessage("elemento no existe ");
                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.show();
                    }

                }

            }
        });


        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = txtNom.getText().toString();
                if (nom.isEmpty() || nom.equals("")) {
                    Toast.makeText(ActividadAdministracion.this, "Hay que introducir el nombre", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor cursor = db.rawQuery("SELECT nombre FROM Elementos WHERE nombre = ?", new String[]{nom});
                    if (!cursor.moveToFirst()) {
                        Toast.makeText(ActividadAdministracion.this, "elemento no existe!", Toast.LENGTH_SHORT).show();
                        //despedir
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActividadAdministracion.this);
                        alertDialogBuilder.setTitle("Error!");
                        alertDialogBuilder.setMessage("Elemento no existe en la BD");
                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.show();
                    } else {
                        db.beginTransaction();
                        try {
                            db.execSQL("DELETE FROM Elementos WHERE nombre = ?", new String[]{String.valueOf(nom)});
                            db.setTransactionSuccessful();
                            Toast.makeText(ActividadAdministracion.this, "Elemento eliminado correctamente", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(ActividadAdministracion.this, "Error al eliminar el elemento", Toast.LENGTH_SHORT).show();
                        } finally {
                            db.endTransaction();
                        }
                    }
                    cursor.close();
                }


            }
        });


        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActividadAdministracion.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}