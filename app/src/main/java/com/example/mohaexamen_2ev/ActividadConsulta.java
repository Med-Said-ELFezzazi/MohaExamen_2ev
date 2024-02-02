package com.example.mohaexamen_2ev;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;


public class ActividadConsulta extends AppCompatActivity {
    EditText txtEntrada;
    TextView lblRslt;
    Button btnBuscar;
    Button btnVolver;
    int contador_veces = 0;

    private QuimicosSQLiteHelper dbHelper;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_consulta);
        // Inicializa el dbHelper       CREACION DE LA BD
        dbHelper = new QuimicosSQLiteHelper(this, "Elementos.db", null, 1);

        // Obtén una instancia de la base de datos
        db = dbHelper.getReadableDatabase();        //Las tablas ya están creadas

        //Insertar los 3 elementos 'registros '
        //1 elemento
        ContentValues values = new ContentValues();
        values.put("nombre", "HELIO");
        values.put("simbolo", "He");
        values.put("num_atomico", "2");
        values.put("estado", "GAS");

        long newRowId = db.insert("Elementos", null, values);

        if (newRowId != -1) {
            // El contacto se agregó correctamente
//            Toast.makeText(ActividadConsulta.this, "elemento agregado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            // Hubo un error al agregar el contacto
            Toast.makeText(ActividadConsulta.this, "Error al agregar el elemento", Toast.LENGTH_SHORT).show();
        }

        //2 elemento
        ContentValues values2 = new ContentValues();
        values.put("nombre", "HIERRO");
        values.put("simbolo", "Fe");
        values.put("num_atomico", "26");
        values.put("estado", "SOLIDO");

        long newRowId2 = db.insert("Elementos", null, values);

        if (newRowId2 != -1) {
            // El contacto se agregó correctamente
//            Toast.makeText(ActividadConsulta.this, "elemento agregado correctamente 2", Toast.LENGTH_SHORT).show();
        } else {
            // Hubo un error al agregar el contacto
            Toast.makeText(ActividadConsulta.this, "Error al agregar el elemento 2", Toast.LENGTH_SHORT).show();
        }

        //3 elemento
        ContentValues values3 = new ContentValues();
        values.put("nombre", "mercurio");
        values.put("simbolo", "Hg");
        values.put("num_atomico", "80");
        values.put("estado", "LIQUIDO");

        long newRowId3 = db.insert("Elementos", null, values);

        if (newRowId3 != -1) {
            // El contacto se agregó correctamente
//            Toast.makeText(ActividadConsulta.this, "elemento agregado correctamente 3", Toast.LENGTH_SHORT).show();
        } else {
            // Hubo un error al agregar el contacto
            Toast.makeText(ActividadConsulta.this, "Error al agregar el elemento 3", Toast.LENGTH_SHORT).show();
        }

        //Encontrar vars
        btnBuscar = findViewById(R.id.btnBuscar);
        btnVolver = findViewById(R.id.btnVolver);
        txtEntrada = findViewById(R.id.txtEntrada);
        lblRslt = findViewById(R.id.lblRslt);
    }

    /**
     * Metodo que busca el elemento
     * @param view
     */
    public void buscarElemento(View view) {
        if (btnBuscar.getText().equals("Limpiar")) {
            //lIMPIA
            limpiarCampo();
            //cONVIERTA
            btnBuscar.setText("Buscar");
        } else {
            String simbolo;
            int num_atom;
            String estado;
            String entrada = txtEntrada.getText().toString();
            if (entrada.isEmpty() || entrada.equals("")) {               // Si el text field esta vacio
                Toast.makeText(ActividadConsulta.this, "Deberia rellenar el campo", Toast.LENGTH_SHORT).show();
            } else {
                // Consulta SQL para obtener los datos
                Cursor cursor = db.rawQuery("SELECT simbolo, num_atomico, estado FROM Elementos WHERE nombre = ?", new String[]{entrada});
                //Aumentar el contador de busquedas
                contador_veces++;
                //Caso hay elemento
                if (cursor.moveToFirst()) {
                    //Convertitr el botón a limpiar
                    btnBuscar.setText("Limpiar");
                    simbolo = cursor.getString(0);
                    num_atom = cursor.getInt(1);
                    estado = cursor.getString(2);
                    // poner los datos en label
                    lblRslt.setText("Elemento " + entrada + "\nSimbolo: " + simbolo + "\nNum_atomico: " + num_atom + "\nEstado: " + estado);
                } else {
                    //Lanzar dialogo deciendo q no existe
                    dialogo();
                    lblRslt.setText("Elemento no existe");
                }
                cursor.close();
            }
        }


    }

    /**
     *Metodo que lanza dialogo
     */
    private void dialogo() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Resultado");
        alertDialogBuilder.setMessage("Elemento no existe!");

        //Una vez el botón ok clickado , se cierra el dialogo
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Hacer nada , para quedar en la pantalla
            }
        });
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.show();
    }

    private void limpiarCampo() {
        //Vaciar los campos
        txtEntrada.setText("");
        lblRslt.setText("");
    }

    public void volver(View view) {
        Intent intent = new Intent(ActividadConsulta.this, MainActivity.class);
        intent.putExtra("CONTADOR", contador_veces) ;// Actualizar el contador para la próxima actividad
        startActivityForResult(intent, 1234);
       //finish();

    }
}