package com.upiiz.practica_ii.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.upiiz.practica_ii.R;
import com.upiiz.practica_ii.adapters.LibroAdapter;
import com.upiiz.practica_ii.api.ClienteRetrofit;
import com.upiiz.practica_ii.db.CrudLibros;
import com.upiiz.practica_ii.db.LibroEntity;
import com.upiiz.practica_ii.db.PreferenciasUsuario;
import com.upiiz.practica_ii.models.Libro;
import com.upiiz.practica_ii.models.RespuestaLibros;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogoActivity extends AppCompatActivity {

    private LibroAdapter adapter;
    private ProgressBar progressBar;
    private CrudLibros crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        new PreferenciasUsuario(this).guardarUltimaSeccion(PreferenciasUsuario.SECCION_CATALOGO);
        crud = new CrudLibros(this);

        progressBar = findViewById(R.id.progressBar);
        Button btnActualizar = findViewById(R.id.btnActualizar);
        RecyclerView rv = findViewById(R.id.rvLibros);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LibroAdapter(false, new LibroAdapter.OnLibroListener() {
            @Override
            public void onDetalle(Libro libro) {
                Intent i = new Intent(CatalogoActivity.this, DetalleActivity.class);
                i.putExtra(DetalleActivity.EXTRA_ID, libro.getId());
                startActivity(i);
            }

            @Override
            public void onFavorito(Libro libro, boolean eraFavorito) {
                alternarFavorito(libro, eraFavorito);
            }
        });
        rv.setAdapter(adapter);

        btnActualizar.setOnClickListener(v -> cargarCatalogo());
        cargarCatalogo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        crud.obtenerIds(ids -> adapter.setFavoritos(ids)); // por si cambió algo desde Detalle
    }

    private void cargarCatalogo() {
        progressBar.setVisibility(View.VISIBLE);
        ClienteRetrofit.obtenerApiLibros().obtenerLibros().enqueue(new Callback<RespuestaLibros>() {
            @Override
            public void onResponse(Call<RespuestaLibros> call, Response<RespuestaLibros> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getDatos() != null) {
                    List<Libro> lista = response.body().getDatos();
                    adapter.setLibros(lista);
                    crud.sincronizarConCatalogo(lista, ids -> adapter.setFavoritos(ids));
                } else {
                    Toast.makeText(CatalogoActivity.this,
                            "Error del servidor: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaLibros> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CatalogoActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void alternarFavorito(Libro libro, boolean eraFavorito) {
        Runnable alTerminar = () -> {
            crud.obtenerIds(ids -> adapter.setFavoritos(ids));
            Toast.makeText(this,
                    eraFavorito ? "Quitado de favoritos" : "Agregado a favoritos",
                    Toast.LENGTH_SHORT).show();
        };

        if (eraFavorito) {
            crud.eliminarPorId(libro.getId(), alTerminar);
        } else {
            crud.insertar(LibroEntity.fromLibro(libro), alTerminar);
        }
    }
}