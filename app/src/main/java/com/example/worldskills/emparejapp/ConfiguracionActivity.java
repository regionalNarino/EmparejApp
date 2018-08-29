package com.example.worldskills.emparejapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ConfiguracionActivity extends AppCompatActivity {

    CheckBox chk;
    EditText txtTiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        chk=findViewById(R.id.checkbox);
        txtTiempo=findViewById(R.id.txtTiempo);

        consultarConfiguracion();

        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk.isChecked()){
                    txtTiempo.setEnabled(true);
                    txtTiempo.setVisibility(View.VISIBLE);
                }else{
                    txtTiempo.setEnabled(false);
                    txtTiempo.setVisibility(View.INVISIBLE);
                }
            }
        });


    }

    private void consultarConfiguracion() {

        try{
            String cadenaSQL="select * from configuracion";
            Conexion conexion=new Conexion(this);
            SQLiteDatabase db=conexion.getReadableDatabase();
            Cursor cursor=db.rawQuery(cadenaSQL,null);
            cursor.moveToNext();

            Toast.makeText(this, cursor.getString(0), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, cursor.getString(1), Toast.LENGTH_SHORT).show();

            if (cursor.getString(0).equals("on")){
                chk.setChecked(true);
                txtTiempo.setEnabled(true);
                txtTiempo.setVisibility(View.VISIBLE);
                txtTiempo.setText(cursor.getString(1));

            }else{
                txtTiempo.setEnabled(false);
                txtTiempo.setVisibility(View.INVISIBLE);
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void guardar(View view) {
        String cadenaSQL;
        if (chk.isChecked()){
            cadenaSQL="update configuracion set estado='on', tiempo='"+txtTiempo.getText().toString()+"'";
        }else{
            cadenaSQL="update configuracion set estado='off'";
        }

        Conexion conexion=new Conexion(this);
        SQLiteDatabase db=conexion.getReadableDatabase();
        db.execSQL(cadenaSQL);
        finish();

    }
}
