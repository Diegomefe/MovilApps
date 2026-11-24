package com.upiiz.exameniii;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.upiiz.exameniii.models.Producto;

import java.util.Locale;

public class DetalleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detalle del producto");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Producto p = (Producto) getIntent().getSerializableExtra("producto");
        if (p == null) {
            finish();
            return;
        }

        ImageView ivDetalle = findViewById(R.id.ivDetalle);
        TextView tvNombre = findViewById(R.id.tvDetalleNombre);
        TextView tvDescripcion = findViewById(R.id.tvDetalleDescripcion);
        TextView tvPrecio = findViewById(R.id.tvDetallePrecio);
        TextView tvCategoria = findViewById(R.id.tvDetalleCategoria);
        TextView tvMarca = findViewById(R.id.tvDetalleMarca);
        TextView tvCalificacion = findViewById(R.id.tvDetalleCalificacion);

        Glide.with(this).load(p.getThumbnail()).into(ivDetalle);
        tvNombre.setText(p.getTitle());
        tvDescripcion.setText(p.getDescription());
        tvPrecio.setText(String.format(Locale.US, "Precio: $%.2f", p.getPrice()));
        tvCategoria.setText("Categoría: " + p.getCategory());
        tvMarca.setText("Marca: " + (p.getBrand() != null ? p.getBrand() : "Sin marca"));
        tvCalificacion.setText(String.format(Locale.US, "Calificación: %.2f / 5", p.getRating()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}