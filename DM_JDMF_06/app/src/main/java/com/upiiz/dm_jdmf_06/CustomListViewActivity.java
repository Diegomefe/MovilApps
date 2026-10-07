package com.upiiz.dm_jdmf_06;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.dm_jdmf_06.adapters.CustomAdapter;

import java.util.ArrayList;

import models.User;

public class CustomListViewActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    ListView lvUsuario;
    ArrayList<User> listaUsuario;
    CustomAdapter adapter;
    Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_custom_list_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Enlazar
        lvUsuario = findViewById(R.id.lvCustom);
        btnRegresar = findViewById(R.id.btnRegresar);
        cargarUsuarios();
        adapter = new CustomAdapter(this,listaUsuario);
        lvUsuario.setAdapter(adapter);

        //Acciones
        btnRegresar.setOnClickListener(this);
        lvUsuario.setOnItemClickListener(this);
        lvUsuario.setOnItemLongClickListener(this);
    }

    private void cargarUsuarios() {
        //Memoria
        //DB
        //API
        //Nube
        //Archivo
        listaUsuario = new ArrayList<>();
        listaUsuario.add(new User(1L,"Juan","Voy tendo, voy tendo pandilla", "6:23",R.drawable.user));
        listaUsuario.add(new User(2L, "María", "En camino a la oficina", "7:15", R.drawable.user));
        listaUsuario.add(new User(3L, "Carlos", "Ya casi llego al punto", "7:40", R.drawable.user));
        listaUsuario.add(new User(4L, "Ana", "Buenos días a todos", "8:05", R.drawable.user));
        listaUsuario.add(new User(5L, "Luis", "¿Alguien tiene los apuntes?", "8:30", R.drawable.user));
        listaUsuario.add(new User(6L, "Sofía", "Nos vemos en la cafetería", "9:12", R.drawable.user));
        listaUsuario.add(new User(7L, "Pedro", "Revisando el código del proyecto", "9:45", R.drawable.user));
        listaUsuario.add(new User(8L, "Lucía", "¿A qué hora empieza la junta?", "10:10", R.drawable.user));
        listaUsuario.add(new User(9L, "Diego", "Listo el respaldo de la práctica", "10:50", R.drawable.user));
        listaUsuario.add(new User(10L, "Elena", "Conexión establecida sin problemas", "11:25", R.drawable.user));
        listaUsuario.add(new User(11L, "Javier", "Ya quedó configurada la red", "12:00", R.drawable.user));
        listaUsuario.add(new User(12L, "Camila", "Al rato me conecto", "12:35", R.drawable.user));
        listaUsuario.add(new User(13L, "Mateo", "Enviando los cambios al repositorio", "13:15", R.drawable.user));
        listaUsuario.add(new User(14L, "Valeria", "¿Dónde nos vemos al salir?", "13:40", R.drawable.user));
        listaUsuario.add(new User(15L, "Andrés", "Todo funcionando al 100", "14:10", R.drawable.user));
    }

    @Override
    public void onClick(View v){
        //Regresar a al MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Click o touch sobre el listado
        Toast.makeText(this, "Diste click o touch", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //Clcik a Touch presionado durante cierto tiempo
        Toast.makeText(this,"Diste touch largo",Toast.LENGTH_LONG).show();
        return false;
    }
}