package com.upiiz.practicachat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.practicachat.Adapter.LlamadaAdapter;
import com.upiiz.practicachat.Adapter.LlamadaFavAdapter;

import java.util.ArrayList;

import Models.Llamada;

public class LlamadasActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView lvFavoritos;
    private ListView lvRecientes;

    private ArrayList<Llamada> listaFavoritos;
    private ArrayList<Llamada> listaRecientes;

    private LlamadaFavAdapter adapterFavoritos;
    private LlamadaAdapter adapterRecientes;

    private Button btnMasFavoritos;
    private ImageButton btnChats, btnEstados, btnLlamadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_llamadas);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvFavoritos = findViewById(R.id.lvFavoritos);
        lvRecientes = findViewById(R.id.lvRecientes);
        btnMasFavoritos = findViewById(R.id.btnMasFavoritos);

        btnChats = findViewById(R.id.btnChats);
        btnEstados = findViewById(R.id.btnEstados);
        btnLlamadas = findViewById(R.id.btnLlamadas);

        cargarFavoritos();
        cargarRecientes();

        adapterFavoritos = new LlamadaFavAdapter(this, listaFavoritos);
        lvFavoritos.setAdapter(adapterFavoritos);

        adapterRecientes = new LlamadaAdapter(this, listaRecientes);
        lvRecientes.setAdapter(adapterRecientes);

        lvFavoritos.setOnItemClickListener(this);
        lvFavoritos.setOnItemLongClickListener(this);

        lvRecientes.setOnItemClickListener(this);
        lvRecientes.setOnItemLongClickListener(this);

        if (btnMasFavoritos != null) btnMasFavoritos.setOnClickListener(this);
        if (btnChats != null) btnChats.setOnClickListener(this);
        if (btnEstados != null) btnEstados.setOnClickListener(this);
        if (btnLlamadas != null) btnLlamadas.setOnClickListener(this);
    }

    private void cargarFavoritos() {
        listaFavoritos = new ArrayList<>();
        listaFavoritos.add(new Llamada(1L, "María González", "", R.drawable.user));
        listaFavoritos.add(new Llamada(2L, "Carlos López", "", R.drawable.user));
        listaFavoritos.add(new Llamada(3L, "Mamá", "", R.drawable.user));
        listaFavoritos.add(new Llamada(4L, "Sofía UPIIZ", "", R.drawable.user));
    }

    private void cargarRecientes() {
        listaRecientes = new ArrayList<>();
        listaRecientes.add(new Llamada(5L, "Juan Pérez", "Hoy, 10:45 a. m.", R.drawable.user));
        listaRecientes.add(new Llamada(6L, "+52 492 123 4567", "Hoy, 8:12 a. m.", R.drawable.user));
        listaRecientes.add(new Llamada(7L, "Ana Torres", "Ayer, 9:30 p. m.", R.drawable.user));
        listaRecientes.add(new Llamada(8L, "Pedro Soto", "Ayer, 4:15 p. m.", R.drawable.user));
        listaRecientes.add(new Llamada(9L, "Diego Ramírez", "28 de septiembre, 11:20 a. m.", R.drawable.user));
        listaRecientes.add(new Llamada(10L, "+52 492 987 6543", "27 de septiembre, 6:05 p. m.", R.drawable.user));
        listaRecientes.add(new Llamada(11L, "Elena Morales", "25 de septiembre, 2:40 p. m.", R.drawable.user));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnChats) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.btnEstados) {
            Intent intent = new Intent(this, CanalesActivity.class);
            startActivity(intent);
        } else if (id == R.id.btnLlamadas) {
            Toast.makeText(this, "Ya estás en Llamadas", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.btnMasFavoritos) {
            Toast.makeText(this, "Agregar nuevo favorito", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.lvFavoritos) {
            Llamada contactoFav = listaFavoritos.get(position);
            Toast.makeText(this, "Llamando a favorito: " + contactoFav.getUsuario(), Toast.LENGTH_SHORT).show();
        } else if (parent.getId() == R.id.lvRecientes) {
            Llamada contactoRec = listaRecientes.get(position);
            Toast.makeText(this, "Detalles de llamada: " + contactoRec.getUsuario(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.lvFavoritos) {
            Llamada contactoFav = listaFavoritos.get(position);
            Toast.makeText(this, "Eliminar de favoritos: " + contactoFav.getUsuario(), Toast.LENGTH_SHORT).show();
        } else if (parent.getId() == R.id.lvRecientes) {
            Llamada contactoRec = listaRecientes.get(position);
            Toast.makeText(this, "Eliminar registro: " + contactoRec.getUsuario(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}