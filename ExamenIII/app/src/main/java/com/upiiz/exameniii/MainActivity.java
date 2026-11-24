package com.upiiz.exameniii;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.upiiz.exameniii.adapters.ProductoAdapter;
import com.upiiz.exameniii.api.ClienteRetrofit;
import com.upiiz.exameniii.models.Producto;
import com.upiiz.exameniii.models.RespuestaProductos;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnConsultar, btnActualizar;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private TextView tvMensaje;
    private ProductoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActualizar = findViewById(R.id.btnActualizar);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        tvMensaje = findViewById(R.id.tvMensaje);

        adapter = new ProductoAdapter(new ArrayList<>(), producto -> {
            Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
            intent.putExtra("producto", producto);
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnActualizar.setOnClickListener(v -> consultarProductos());
        consultarProductos();
    }

    private void consultarProductos() {
        progressBar.setVisibility(View.VISIBLE);
        tvMensaje.setVisibility(View.GONE);
        btnActualizar.setEnabled(false);

        ClienteRetrofit.obtenerApiProductos().obtenerProductos().enqueue(new Callback<RespuestaProductos>() {
            @Override
            public void onResponse(Call<RespuestaProductos> call, Response<RespuestaProductos> response) {
                progressBar.setVisibility(View.GONE);
                btnActualizar.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    adapter.actualizarLista(response.body().getProducts());
                } else {
                    mostrarError("Error del servidor: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<RespuestaProductos> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnConsultar.setEnabled(true);
                btnActualizar.setEnabled(true);
                mostrarError("Error de conexión. Verifica tu internet e intenta de nuevo.");
            }
        });
    }

    private void mostrarError(String mensaje) {
        tvMensaje.setText(mensaje);
        tvMensaje.setVisibility(View.VISIBLE);
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}