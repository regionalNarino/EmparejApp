package com.example.worldskills.emparejapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PuntajesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntajes);


    }

    public void onClick(View view) {
        Intent intent=new Intent(PuntajesActivity.this,ListadoPuntajesActivity.class);
        switch (view.getId()){
            case R.id.facil:
                intent.putExtra("nivel","facil");
                break;
            case R.id.medio:
                intent.putExtra("nivel","medio");
                break;
            case R.id.dificil:
                intent.putExtra("nivel","dificil");
                break;
        }

        startActivity(intent);
    }
}
