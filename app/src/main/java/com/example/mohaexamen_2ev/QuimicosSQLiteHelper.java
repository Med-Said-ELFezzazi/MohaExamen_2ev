package com.example.mohaexamen_2ev;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import androidx.annotation.Nullable;

public class QuimicosSQLiteHelper extends SQLiteOpenHelper {

    //creacion de la tabla
    String sqlCrearElementos = "CREATE TABLE Elementos (" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    nombre TEXT," +
            "    simbolo TEXT," +
            "    num_atomico INTEGER," +
            "    estado TEXT)";

    //El constructor
    public QuimicosSQLiteHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCrearElementos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior
        db.execSQL("DROP TABLE IF EXISTS Elementos");
        //crea la nueva
        db.execSQL(sqlCrearElementos);
    }
}
