package com.upiiz.practicachat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.practicachat.Adapter.ChatAdapter;

import java.util.ArrayList;

import Models.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView lvChats;
    private ArrayList<User> listaUsuarios;
    private ChatAdapter adapter;

    // Botones de navegación inferior
    private ImageButton btnChats, btnEstados, btnLlamadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvChats = findViewById(R.id.lvChats);
        btnChats = findViewById(R.id.btnChats);
        btnEstados = findViewById(R.id.btnEstados);
        btnLlamadas = findViewById(R.id.btnLlamadas);

        cargarUsuarios();

        adapter = new ChatAdapter(this, listaUsuarios);
        lvChats.setAdapter(adapter);

        lvChats.setOnItemClickListener(this);
        lvChats.setOnItemLongClickListener(this);

        if (btnChats != null) btnChats.setOnClickListener(this);
        if (btnEstados != null) btnEstados.setOnClickListener(this);
        if (btnLlamadas != null) btnLlamadas.setOnClickListener(this);
    }

    private void cargarUsuarios() {
        listaUsuarios = new ArrayList<>();
        listaUsuarios.add(new User(1L, "Juan", "Voy tendido pandilla", "06:23", R.drawable.user));
        listaUsuarios.add(new User(2L, "María", "En camino a la oficina", "07:15", R.drawable.user));
        listaUsuarios.add(new User(3L, "Carlos", "Ya casi llego al punto", "07:40", R.drawable.user));
        listaUsuarios.add(new User(4L, "Ana", "Buenos días a todos", "08:05", R.drawable.user));
        listaUsuarios.add(new User(5L, "Luis", "¿Alguien tiene los apuntes?", "08:30", R.drawable.user));
        listaUsuarios.add(new User(6L, "Sofía", "Nos vemos en la cafetería", "09:12", R.drawable.user));
        listaUsuarios.add(new User(7L, "Pedro", "Revisando el código del proyecto", "09:45", R.drawable.user));
        listaUsuarios.add(new User(8L, "Lucía", "¿A qué hora empieza la junta?", "10:10", R.drawable.user));
        listaUsuarios.add(new User(9L, "Diego", "Listo el respaldo de la práctica", "10:50", R.drawable.user));
        listaUsuarios.add(new User(10L, "Elena", "Conexión establecida sin problemas", "11:25", R.drawable.user));
        listaUsuarios.add(new User(11L, "Javier", "Ya quedó configurada la red", "12:00", R.drawable.user));
        listaUsuarios.add(new User(12L, "Camila", "Al rato me conecto", "12:35", R.drawable.user));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnChats) {
            Toast.makeText(this, "Ya estás en Chats", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.btnEstados) {
            Intent intent = new Intent(this, CanalesActivity.class);
            startActivity(intent);
        } else if (id == R.id.btnLlamadas) {
            Intent intent = new Intent(this, LlamadasActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User usuario = listaUsuarios.get(position);
        Toast.makeText(this, "Chat con: " + usuario.getNombre(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        User usuario = listaUsuarios.get(position);
        Toast.makeText(this, "Opciones de chat: " + usuario.getNombre(), Toast.LENGTH_SHORT).show();
        return true;
    }
}