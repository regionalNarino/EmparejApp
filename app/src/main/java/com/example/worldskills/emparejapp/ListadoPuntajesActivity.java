package com.example.worldskills.emparejapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListadoPuntajesActivity extends AppCompatActivity {

    List<String> listadatos;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_puntajes);
        lista=findViewById(R.id.listaPuntajes);
        cargarPuntajes();
    }

    private void cargarPuntajes() {
        listadatos=new ArrayList<>();
        try{
            String sql="select * from puntaje where dificultad='"+getIntent().getExtras().getString("nivel")+"' order by puntos limit 5 ";
            Conexion conexion=new Conexion(this);
            SQLiteDatabase db=conexion.getReadableDatabase();

            Cursor cursor=db.rawQuery(sql,null);
            int contador=0;
            while (cursor.moveToNext()){
                listadatos.add(cursor.getString(1)+"  "+cursor.getString(2));
                contador++;
            }

            lista.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listadatos));
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
