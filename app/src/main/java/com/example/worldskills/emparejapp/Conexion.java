package com.example.worldskills.emparejapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexion extends SQLiteOpenHelper {
    public Conexion(Context context) {
        super(context, "game", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table configuracion (estado text,tiempo text)");
        db.execSQL("insert into configuracion (estado,tiempo) values('off','30000')");

        db.execSQL("create table puntaje(id integer primary key autoincrement, nombre text, puntos text, dificultad text)");
        db.execSQL("insert into puntaje(nombre,puntos,dificultad) values('julio','300','dificil')");
        db.execSQL("insert into puntaje(nombre,puntos,dificultad) values('jorge','300','facil')");
        db.execSQL("insert into puntaje(nombre,puntos,dificultad) values('luis','300','medio')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
