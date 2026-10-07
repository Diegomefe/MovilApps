package com.upiiz.dm_jdmf_06;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class BasicListViewActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRegresar;
    ListView lvBasico;
    ArrayList<String> listadoProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_basic_list_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnRegresar = findViewById(R.id.btnRegresar);
        lvBasico = findViewById(R.id.lvBasico);

        btnRegresar.setOnClickListener(this);
        cargarDatos();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listadoProductos);

        lvBasico.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        regresar();
    }

    public void regresar(){
        Intent intentRegresar = new Intent(this, MainActivity.class);

        startActivity(intentRegresar);
    }

    public void cargarDatos(){
        // Memoria - Array
        //Base de datos - SQLite - ROOM
        //API REST Desarrollada con mal llama Arquitectura Hexagonal
        //Servicio en la nube - RealTime Database

        listadoProductos = new ArrayList<>();
        listadoProductos.add("Fabuloso");
        listadoProductos.add("Cloro");
        listadoProductos.add("Pinol");
        listadoProductos.add("Jabon Zote");
        listadoProductos.add("Maestro limpio");
        listadoProductos.add("Pato Purific");
        listadoProductos.add("AJAX");
        listadoProductos.add("Mr Musculo");
        listadoProductos.add("Vel Rosita");
        listadoProductos.add("Salvo");
        listadoProductos.add("Jabon Foca");
        listadoProductos.add("Jabon Blanca nieves");
        listadoProductos.add("Downy");
        listadoProductos.add("Vanish");
        listadoProductos.add("Escoba");
        listadoProductos.add("Trapeador");


    }
}