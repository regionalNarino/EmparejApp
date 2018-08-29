package com.example.worldskills.emparejapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class ConfiguracionActivity extends AppCompatActivity {

    CheckBox chk;
    EditText txtTiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        chk=findViewById(R.id.checkbox);

    }

    public void guardar(View view) {

    }
}
