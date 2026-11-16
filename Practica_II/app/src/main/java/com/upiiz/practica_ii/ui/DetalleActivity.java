package com.upiiz.practica_ii.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.upiiz.practica_ii.R;
import com.upiiz.practica_ii.api.ClienteRetrofit;
import com.upiiz.practica_ii.db.CrudLibros;
import com.upiiz.practica_ii.db.LibroEntity;
import com.upiiz.practica_ii.models.Libro;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "libro_id";

    private TextView tvId, tvTitulo, tvAutor, tvEditorial, tvPaginas, tvDisponible, tvEstadoFavorito;
    private Button btnFavorito;
    private ProgressBar progressBar;
    private CrudLibros crud;

    private Libro libro;
    private boolean esFavorito = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        crud = new CrudLibros(this);

        tvId = findViewById(R.id.tvId);
        tvTitulo = findViewById(R.id.tvTitulo);
        tvAutor = findViewById(R.id.tvAutor);
        tvEditorial = findViewById(R.id.tvEditorial);
        tvPaginas = findViewById(R.id.tvPaginas);
        tvDisponible = findViewById(R.id.tvDisponible);
        tvEstadoFavorito = findViewById(R.id.tvEstadoFavorito);
        btnFavorito = findViewById(R.id.btnFavorito);
        progressBar = findViewById(R.id.progressBar);

        btnFavorito.setEnabled(false);
        btnFavorito.setOnClickListener(v -> alternarFavorito());

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id == -1) {
            Toast.makeText(this, "Libro no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        cargarDetalle(id);
    }

    private void cargarDetalle(int id) {
        progressBar.setVisibility(View.VISIBLE);
        ClienteRetrofit.obtenerApiLibros().obtenerLibroPorId(id).enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    mostrar(response.body());
                } else {
                    Toast.makeText(DetalleActivity.this,
                            "Error del servidor: " + response.code(), Toast.LENGTH_LONG).show();
                    cargarDesdeRoom(id);
                }
            }

            @Override
            public void onFailure(Call<Libro> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DetalleActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                cargarDesdeRoom(id);
            }
        });
    }

    // Respaldo offline: si el libro es favorito, se muestra desde Room
    private void cargarDesdeRoom(int id) {
        crud.obtenerPorId(id, entidad -> {
            if (entidad != null) {
                Toast.makeText(this, "Mostrando datos guardados localmente", Toast.LENGTH_SHORT).show();
                mostrar(entidad.toLibro());
            }
        });
    }

    private void mostrar(Libro l) {
        libro = l;
        tvId.setText("ID: " + l.getId());
        tvTitulo.setText(l.getTitulo());
        tvAutor.setText("Autor: " + l.getAutor());
        tvEditorial.setText("Editorial: " + l.getEditorial());
        tvPaginas.setText("Páginas: " + l.getPaginas());
        tvDisponible.setText(l.isDisponible() ? "Disponible: Sí" : "Disponible: No");

        crud.obtenerPorId(l.getId(), entidad -> {
            esFavorito = entidad != null;
            actualizarBoton();
        });
    }

    private void actualizarBoton() {
        btnFavorito.setEnabled(true);
        btnFavorito.setText(esFavorito ? "Quitar de favoritos" : "Agregar a favoritos");
        tvEstadoFavorito.setText(esFavorito ? "★ Está en tus favoritos" : "☆ No está en favoritos");
    }

    private void alternarFavorito() {
        if (libro == null) return;
        boolean eraFavorito = esFavorito;

        Runnable alTerminar = () -> {
            esFavorito = !eraFavorito;
            actualizarBoton();
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