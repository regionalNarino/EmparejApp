package com.example.worldskills.emparejapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;
    int imagenes[]=new int[4];
    int imageDefault[]={ //vector con todas las imagenes en nuestro proyecto
            R.drawable.uno,R.drawable.dos,R.drawable.tres,R.drawable.cuatro,R.drawable.cinco,R.drawable.seis,R.drawable.siete,R.drawable.ocho,
            R.drawable.nueve,R.drawable.diez,R.drawable.once,R.drawable.doce,R.drawable.trece,R.drawable.catorce,R.drawable.quince,R.drawable.dseis,
            R.drawable.dsiete,R.drawable.docho,R.drawable.dnueve,R.drawable.dveinte,
    };

    int imagenesAleatorias[]=new int[8];
    //construimos dos variables para comparar la igualdad de cada una

    int seleccionado1=0;
    int seleccionado2=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        btn4=findViewById(R.id.btn4);
        btn5=findViewById(R.id.btn5);
        btn6=findViewById(R.id.btn6);
        btn7=findViewById(R.id.btn7);
        btn8=findViewById(R.id.btn8);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);

        imagenAleatoria();//seleccionar las imagenes que van a aparecer en el juego
        cargarImagenesArray();// seleccionar en que posicion van a ir las imagenes

    }

    private void cargarImagenesArray() {
        int contador=0;
        //de esta manera es como ponemos dos imagenes iguales y guardamos su posición
        for ( int i=0; i<imagenes.length; ){
            int aleatorio= (int) (Math.random()*8);
            if (imagenesAleatorias[aleatorio]==0){
                imagenesAleatorias[aleatorio]=imagenes[i];
                contador++;
                if (contador==2){
                    i++;
                    contador=0;
                }
            }
        }
    }

    private void imagenAleatoria() {
        for (int i=0; i<imagenes.length;){
            int aleatorio1= (int) (Math.random()*20);
            //validación para que ninguna imagen se repita y se generen de manera aleatoria
            if (imagenes[0]!=imageDefault[aleatorio1] && imagenes[1]!=imageDefault[aleatorio1] &&
                    imagenes[2]!=imageDefault[aleatorio1] && imagenes[3]!=imageDefault[aleatorio1] ){

                imagenes[i]=imageDefault[aleatorio1];
                Toast.makeText(this, imagenes[i], Toast.LENGTH_SHORT).show();
                i++;
            }
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                if (seleccionado1==0){
                    btn1.setImageResource(imagenesAleatorias[0]);
                }else{
                    comparar();
                }
                break;
            case R.id.btn2:
                if (seleccionado1==0){
                    btn2.setImageResource(imagenesAleatorias[0]);
                }else{
                    comparar();
                }
                break;
            case R.id.btn3:
                if (seleccionado1==0){
                    btn3.setImageResource(imagenesAleatorias[0]);
                }else{
                    comparar();
                }
                break;
            case R.id.btn4:
                if (seleccionado1==0){
                    btn4.setImageResource(imagenesAleatorias[0]);
                }else{
                    comparar();
                }
                break;
            case R.id.btn5:
                if (seleccionado1==0){
                    btn5.setImageResource(imagenesAleatorias[0]);
                }else{
                    comparar();
                }
                break;
            case R.id.btn6:
                if (seleccionado1==0){
                    btn6.setImageResource(imagenesAleatorias[0]);
                }else{
                    comparar();
                }
                break;
            case R.id.btn7:
                if (seleccionado1==0){
                    btn7.setImageResource(imagenesAleatorias[0]);
                }else{
                    comparar();
                }
                break;
            case R.id.btn8:
                if (seleccionado1==0){
                    btn8.setImageResource(imagenesAleatorias[0]);
                }else{
                    comparar();
                }
                break;
        }
    }

    private void comparar() {
    }
}
