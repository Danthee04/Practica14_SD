package com.example.practica14_sd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {

    private EditText et_NombreArchivo, et_Contenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_NombreArchivo = (EditText) findViewById(R.id.txt_NombreArchivo);
        et_Contenido = (EditText) findViewById(R.id.txt_Contenido);
    }

    //Metodo para el boton GUARDAR
    public void Guardar(View view) {
        String NombreArchivo_String = et_NombreArchivo.getText().toString();
        String Contenido_String = et_Contenido.getText().toString();

        try {
            //Cuando vamos a guardar un archivo de texto dentro del sistema externo(SD), es indispinseable decirle a la app donde se encuentra la tarjeta SD es decir la ruta a seguir para encontrar la tarjeta SD
            File TarjetaSD = Environment.getExternalStorageDirectory();//Esta linea de codigo nos permite guardar temporalmente la ruta donde esta la tarjeta SD en el objeto del mismo nombre, ademas debemos recuperar la ruta es decir encontrarla con la ayuda del metodo que se muestra
            Toast.makeText(this, TarjetaSD.getPath(), Toast.LENGTH_SHORT).show();//Mensaje para que el usuario pueda ver en donde esta guardado su archivo, en este caso en vez de usar comillas para indicar el mensaje ponemos la variable TarjetaSD (ya que esta variable contiene la ruta)
            //El primer parametro es la URL(ubicacion) de la tarjeta SD, tal y como lo conseguimos a la hora de enviar el mensaje al usuario.
            //El segundo parametro es el nombre que el usuario selecciono para este archivo y este lo tenemos almacenado en la variable "NombreArchivo_String"
            File RutaArchivo = new File(TarjetaSD.getPath(), NombreArchivo_String);//Esta linea de codigo sirve para generar la ruta del archivo, Pide 2 parametros
            //Con la ayuda del metodo openFileOutput abriremos el archivo, de igual forma nos solicita 2 parametros
            //El Primero es el nombre del archivo que queremos abrir
            //el segundo es  Activity.MODE_PRIVATE
            OutputStreamWriter AbrirArchivo_OSW = new OutputStreamWriter(openFileOutput(NombreArchivo_String, Activity.MODE_PRIVATE));//Esta linea de codigo sirve para abrir el archivo
            AbrirArchivo_OSW.write(Contenido_String);//Esta linea de codigo indica al programa que queremos escribir algo dentro de el (en este caso pasar el texto que ya escribio el usuario)
            AbrirArchivo_OSW.flush();//Limpamos el buffer
            AbrirArchivo_OSW.close();//Cerramos el archivo

            Toast.makeText(this, "Guardado Correctamente", Toast.LENGTH_SHORT).show();
            et_NombreArchivo.setText("");//Limpiamos el campo de texto
            et_Contenido.setText("");//Limpiamos el campo de texto
        } catch (IOException e) {
            Toast.makeText(this, "No se pudo guardar", Toast.LENGTH_SHORT).show();
        }

    }


    //Metodo para el boton CONSULTAR
    public void Consultar(View view){
        //Primero debemos crear una variable para recuperar el nombre de el archivo que el usuario desea consultar
        String NombreArchivo_String = et_NombreArchivo.getText().toString();

        try {
            File TarjetaSD = Environment.getExternalStorageDirectory();//Guardar la ruta de la tarjeta SD
            File RutaArchivo = new File(TarjetaSD.getPath(), NombreArchivo_String);//Genera una variable con la ruta del archivo
            InputStreamReader AbrirArchivo_ISW = new InputStreamReader(openFileInput(NombreArchivo_String));//Abre el archivo que ya generamos

            BufferedReader LeerArchivo_BR = new BufferedReader(AbrirArchivo_ISW);//Leer cada linea de texto para saber si esta vacio o no
            String PrimeraLinea_String = LeerArchivo_BR.readLine();//Indicamos que tiene que leer la primer linea de texto y guardarla dentro de una variable
            String ContenidoCompleto_String = "";//Creamos variable que contendra el texto completo

            while (PrimeraLinea_String != null){ //Hacer mientras la primer linea no esta vacia
                ContenidoCompleto_String = ContenidoCompleto_String + PrimeraLinea_String + "\n";//Variable de acumulacion, en cada ciclo ira acumulando el texto
                PrimeraLinea_String = LeerArchivo_BR.readLine();// Hara el recorrido hacia la siguiente linea
            }
            LeerArchivo_BR.close();
            AbrirArchivo_ISW.close();
            et_Contenido.setText(ContenidoCompleto_String);//Colocamos el texto almacenado en el EditText del Contenido
        }catch (IOException e){
            Toast.makeText(this, "Error al leer el archivo", Toast.LENGTH_SHORT).show();
        }
    }
}