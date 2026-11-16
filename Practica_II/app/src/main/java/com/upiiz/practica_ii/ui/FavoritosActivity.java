package com.upiiz.practica_ii.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.upiiz.practica_ii.R;
import com.upiiz.practica_ii.adapters.LibroAdapter;
import com.upiiz.practica_ii.db.CrudLibros;
import com.upiiz.practica_ii.db.LibroEntity;
import com.upiiz.practica_ii.db.PreferenciasUsuario;
import com.upiiz.practica_ii.models.Libro;

import java.util.ArrayList;
import java.util.List;

public class FavoritosActivity extends AppCompatActivity {

    private LibroAdapter adapter;
    private TextView tvVacio;
    private CrudLibros crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        new PreferenciasUsuario(this).guardarUltimaSeccion(PreferenciasUsuario.SECCION_FAVORITOS);
        crud = new CrudLibros(this);

        tvVacio = findViewById(R.id.tvVacio);
        RecyclerView rv = findViewById(R.id.rvFavoritos);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LibroAdapter(true, new LibroAdapter.OnLibroListener() {
            @Override
            public void onDetalle(Libro libro) {
                Intent i = new Intent(FavoritosActivity.this, DetalleActivity.class);
                i.putExtra(DetalleActivity.EXTRA_ID, libro.getId());
                startActivity(i);
            }

            @Override
            public void onFavorito(Libro libro, boolean eraFavorito) {
                crud.eliminarPorId(libro.getId(), () -> {
                    Toast.makeText(FavoritosActivity.this,
                            "Quitado de favoritos", Toast.LENGTH_SHORT).show();
                    cargarFavoritos();
                });
            }
        });
        rv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarFavoritos();
    }

    private void cargarFavoritos() {
        crud.obtenerTodos((List<LibroEntity> entidades) -> {
            List<Libro> libros = new ArrayList<>();
            for (LibroEntity e : entidades) libros.add(e.toLibro());

            adapter.setLibros(libros);
            tvVacio.setVisibility(libros.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }
}