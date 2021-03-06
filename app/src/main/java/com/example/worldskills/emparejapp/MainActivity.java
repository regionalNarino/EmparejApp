package com.example.worldskills.emparejapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
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
    int idDefault[];
    TextView tvJugadorUno,tvJugadorDos,tvPuntajeUno,tvPuntajeDos,tiempo;

    int turno;
    int puntosUno=0;
    int puntosDos=0;

    //definimos si el juego esta por defecto o con tiempo
    boolean validarTiempo;
    int tiempoTotal=0;
    CountDownTimer timer; //Solo sirve en caso de que el usuario quiera jugar con tiempo

    int contadorAciertos=0;

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

        idDefault= new int[]{R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8};
        imagenAleatoria();//seleccionar las imagenes que van a aparecer en el juego
        cargarImagenesArray();// seleccionar en que posicion van a ir las imagenes

        tvJugadorUno=findViewById(R.id.jugadorUno);
        tvJugadorDos=findViewById(R.id.jugadorDos);
        tvPuntajeUno=findViewById(R.id.puntajeUno);
        tvPuntajeDos=findViewById(R.id.puntajeDos);
        tiempo=findViewById(R.id.tiempo);

        tvJugadorUno.setText(Usuario.jugadorUno);
        tvJugadorDos.setText(Usuario.jugadorDos);

        //definimos quien sera el primero en iniciar el juego.
        turno= (int) (Math.random()*2);
        cambiarColores();  //ahora cambiamos los colores para mostrarle al usuario quien comienza el juego

        validarTiempo=consultarModoJuego();
        if (validarTiempo==true){
            //entonces habilitamos el modo de juego con tiempo
            timer=new CountDownTimer(tiempoTotal,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tiempo.setText(Long.toString(millisUntilFinished/1000));
                }

                @Override
                public void onFinish() {
                    finalizarJuego();
                }
            }.start();
        }else{
            LinearLayout barra=findViewById(R.id.barraTiempo);
            barra.setVisibility(View.INVISIBLE);
        }
    }

    private void finalizarJuego() {
        guardarDatos();
        desableAll();
        AlertDialog.Builder ventana=new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layout=this.getLayoutInflater();
        View view=layout.inflate(R.layout.ventana_fin_juego,null,false);

        TextView numeroUno=view.findViewById(R.id.name0ne);
        TextView numeroDos=view.findViewById(R.id.nameTwo);
        TextView puntos1=view.findViewById(R.id.pointsOne);
        TextView puntos2=view.findViewById(R.id.pointsTwo);
        Button btnCompartir=view.findViewById(R.id.btnCompartir);

        numeroUno.setText(tvJugadorUno.getText());
        numeroDos.setText(tvJugadorDos.getText());
        puntos1.setText(tvPuntajeUno.getText());
        puntos2.setText(tvPuntajeDos.getText());

        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventanaRedes();
            }
        });

        ventana.setView(view);
        ventana.create();
        ventana.show();

    }

    private void guardarDatos() {
        if (validarTiempo==false){
            try{
                String cadenaSQL="insert into puntaje (nombre,puntos) values('"+tvJugadorUno.getText()+"','"+tvPuntajeUno.getText()+"')";
                String cadenaSQL2="insert into puntaje (nombre,puntos) values('"+tvJugadorDos.getText()+"','"+tvPuntajeDos.getText()+"')";
                Conexion conexion=new Conexion(this);
                SQLiteDatabase dn=conexion.getWritableDatabase();
                dn.execSQL(cadenaSQL);
                dn.execSQL(cadenaSQL2);

            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ventanaRedes() {

        AlertDialog.Builder ventana=new AlertDialog.Builder(MainActivity.this);
        ventana.setTitle("Donde quieres compartir este resultado");
        final CharSequence [] opciones={"Facebook","Tweeter"};

        ventana.setItems(opciones, new DialogInterface.OnClickListener() {
            String texto="Acabo de jugar EmparejApp,           " +
                    "Estos son los puntajes.      " +
                    ""+tvJugadorUno.getText()+":  "+tvPuntajeUno.getText()+" " +
                    ""+tvJugadorUno.getText()+":  "+tvPuntajeUno.getText()+"";

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (opciones[which].equals("Facebook")){
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, texto);
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.facebook.lite");
                    startActivity(sendIntent);
                }
                if (opciones[which].equals("Tweeter")){
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, texto);
                    sendIntent.setType("text/plain");
                    sendIntent.setPackage("com.twitter.android");
                    startActivity(sendIntent);
                }

            }
        });
        ventana.show();
    }

    private void desableAll() {
        disabledButton();
        if (validarTiempo==true){
            timer.cancel();
        }
    }


    private boolean consultarModoJuego() {
        String cadenaSQL="select * from configuracion";
        Conexion conexion=new Conexion(this);
        SQLiteDatabase db=conexion.getReadableDatabase();
        Cursor cursor=db.rawQuery(cadenaSQL,null);
        cursor.moveToNext();
        if (cursor.getString(0).equals("on")){
            tiempoTotal=cursor.getInt(1);
            return true;
        }else{
            return false;
        }

    }

    private void cambiarColores() {
        if (turno==0){
            tvJugadorUno.setTextColor(Color.GREEN);
            tvPuntajeUno.setTextColor(Color.GREEN);
            tvJugadorDos.setTextColor(Color.GRAY);
            tvPuntajeDos.setTextColor(Color.GRAY);
        }else{
            tvJugadorUno.setTextColor(Color.GRAY);
            tvPuntajeUno.setTextColor(Color.GRAY);
            tvJugadorDos.setTextColor(Color.GREEN);
            tvPuntajeDos.setTextColor(Color.GREEN);
        }
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
                i++;
            }
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                btn1.setImageResource(imagenesAleatorias[0]);  //carga la imagen
                btn1.setId(imagenesAleatorias[0]);   //tambien el id se lo aplica al ImageButton
                if (seleccionado1==0){ //si esta en cero significa que es la primera imagen seleccionada
                    seleccionado1=imagenesAleatorias[0];  //pone el id de la imagen en esta variable
                }else{  //significa que es la segunda imagen seleccionada.
                    seleccionado2=imagenesAleatorias[0];  //carga la segunda variable con el id de la imagen
                    comparar(); //llama al metodo para comparar si las imagenes son iguales
                }
                break;

                //el procedimiento es igual para todos los botones

            case R.id.btn2:
                btn2.setImageResource(imagenesAleatorias[1]);
                btn2.setId(imagenesAleatorias[1]);
                if (seleccionado1==0){
                    seleccionado1=imagenesAleatorias[1];
                }else{
                    seleccionado2=imagenesAleatorias[1];
                    comparar();
                }
                break;

            case R.id.btn3:
                btn3.setImageResource(imagenesAleatorias[2]);
                btn3.setId(imagenesAleatorias[2]);
                if (seleccionado1==0){
                    seleccionado1=imagenesAleatorias[2];
                }else{
                    seleccionado2=imagenesAleatorias[2];
                    comparar();
                }
                break;

            case R.id.btn4:
                btn4.setImageResource(imagenesAleatorias[3]);
                btn4.setId(imagenesAleatorias[3]);
                if (seleccionado1==0){
                    seleccionado1=imagenesAleatorias[3];
                }else{
                    seleccionado2=imagenesAleatorias[3];
                    comparar();
                }
                break;

            case R.id.btn5:
                btn5.setImageResource(imagenesAleatorias[4]);
                btn5.setId(imagenesAleatorias[4]);
                if (seleccionado1==0){
                    seleccionado1=imagenesAleatorias[4];
                }else{
                    seleccionado2=imagenesAleatorias[4];
                    comparar();
                }
                break;

            case R.id.btn6:
                btn6.setId(imagenesAleatorias[5]);
                btn6.setImageResource(imagenesAleatorias[5]);
                if (seleccionado1==0){
                    seleccionado1=imagenesAleatorias[5];
                }else{
                    seleccionado2=imagenesAleatorias[5];
                    comparar();
                }
                break;

            case R.id.btn7:
                btn7.setImageResource(imagenesAleatorias[6]);
                btn7.setId(imagenesAleatorias[6]);
                if (seleccionado1==0){
                    seleccionado1=imagenesAleatorias[6];
                }else{
                    seleccionado2=imagenesAleatorias[6];
                    comparar();
                }
                break;

            case R.id.btn8:
                btn8.setImageResource(imagenesAleatorias[7]);
                btn8.setId(imagenesAleatorias[7]);
                if (seleccionado1==0){
                    seleccionado1=imagenesAleatorias[7];
                }else{
                    seleccionado2=imagenesAleatorias[7];
                    comparar();
                }
                break;
        }
    }

    private void comparar() {
        disabledButton(); //desabilitamos los botones para que el usuario no pueda hacer click mas de dos veces

        if (seleccionado1==seleccionado2){
            MediaPlayer media=MediaPlayer.create(this,R.raw.win);
            media.start();

            //comparamos el nuevo id que contiene el boton con las variable que han guardado los id de las imagenes
            //si el id que tiene el boton actualmente es igual al de las imagenes estas se van a ocultar
            if (btn1.getId()==seleccionado1 && btn1.getId()==seleccionado2){btn1.setVisibility(View.INVISIBLE);}
            if (btn2.getId()==seleccionado1 && btn2.getId()==seleccionado2){btn2.setVisibility(View.INVISIBLE);}
            if (btn3.getId()==seleccionado1 && btn3.getId()==seleccionado2){btn3.setVisibility(View.INVISIBLE);}
            if (btn4.getId()==seleccionado1 && btn4.getId()==seleccionado2){btn4.setVisibility(View.INVISIBLE);}
            if (btn5.getId()==seleccionado1 && btn5.getId()==seleccionado2){btn5.setVisibility(View.INVISIBLE);}
            if (btn6.getId()==seleccionado1 && btn6.getId()==seleccionado2){btn6.setVisibility(View.INVISIBLE);}
            if (btn7.getId()==seleccionado1 && btn7.getId()==seleccionado2){btn7.setVisibility(View.INVISIBLE);}
            if (btn8.getId()==seleccionado1 && btn8.getId()==seleccionado2){btn8.setVisibility(View.INVISIBLE);}
            contadorAciertos++;
            aumentarPuntos();
            enabledButton();
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    enabledButton();
                    ocultarImagen();
                }
            },1000);
            MediaPlayer media=MediaPlayer.create(this,R.raw.lose);
            media.start();
            reducirPuntos();
            cambiarTurno();
            cambiarColores();
        }
        seleccionado1=0;
        seleccionado2=0;
        retornarId();

        if (contadorAciertos==4){
            finalizarJuego();
        }

    }

    private void reducirPuntos() {
        if (turno==0){
            puntosUno-=2;
        }else{
            puntosDos-=2;
        }
        if (puntosUno<0){
            puntosUno=0;
        }
        if (puntosDos<0){
            puntosDos=0;
        }
        actualizarPuntos();
    }

    private void actualizarPuntos() {
        tvPuntajeUno.setText(Integer.toString(puntosUno));
        tvPuntajeDos.setText(Integer.toString(puntosDos));
    }

    private void cambiarTurno() {
        if (turno==0) turno=1;
        else turno=0;
    }

    private void aumentarPuntos() {
        if (turno==0){
            puntosUno+=100;
        }else{
            puntosDos+=100;
        }
        actualizarPuntos();
    }

    private void ocultarImagen() {
        btn1.setImageResource(R.drawable.quien);
        btn2.setImageResource(R.drawable.quien);
        btn3.setImageResource(R.drawable.quien);
        btn4.setImageResource(R.drawable.quien);
        btn5.setImageResource(R.drawable.quien);
        btn6.setImageResource(R.drawable.quien);
        btn7.setImageResource(R.drawable.quien);
        btn8.setImageResource(R.drawable.quien);
    }

    private void enabledButton() {
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        btn5.setEnabled(true);
        btn6.setEnabled(true);
        btn7.setEnabled(true);
        btn8.setEnabled(true);
    }

    private void disabledButton() {
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        btn5.setEnabled(false);
        btn6.setEnabled(false);
        btn7.setEnabled(false);
        btn8.setEnabled(false);
    }

    private void retornarId() {
        btn1.setId(idDefault[0]);
        btn2.setId(idDefault[1]);
        btn3.setId(idDefault[2]);
        btn4.setId(idDefault[3]);
        btn5.setId(idDefault[4]);
        btn6.setId(idDefault[5]);
        btn7.setId(idDefault[6]);
        btn8.setId(idDefault[7]);
    }
}
