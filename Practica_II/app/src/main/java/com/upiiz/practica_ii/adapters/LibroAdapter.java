package com.upiiz.practica_ii.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upiiz.practica_ii.R;
import com.upiiz.practica_ii.models.Libro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LibroAdapter extends RecyclerView.Adapter<LibroAdapter.LibroViewHolder> {

    public interface OnLibroListener {
        void onDetalle(Libro libro);
        void onFavorito(Libro libro, boolean eraFavorito);
    }

    private final List<Libro> libros = new ArrayList<>();
    private final Set<Integer> favoritos = new HashSet<>();
    private final boolean modoFavoritos;
    private final OnLibroListener listener;

    public LibroAdapter(boolean modoFavoritos, OnLibroListener listener) {
        this.modoFavoritos = modoFavoritos;
        this.listener = listener;
    }

    public void setLibros(List<Libro> nuevos) {
        libros.clear();
        if (nuevos != null) libros.addAll(nuevos);
        notifyDataSetChanged();
    }

    public void setFavoritos(List<Integer> ids) {
        favoritos.clear();
        if (ids != null) favoritos.addAll(ids);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_libro, parent, false);
        return new LibroViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder h, int position) {
        Libro libro = libros.get(position);
        boolean esFavorito = modoFavoritos || favoritos.contains(libro.getId());

        h.tvId.setText("ID: " + libro.getId());
        h.tvTitulo.setText(libro.getTitulo());
        h.tvAutor.setText("Autor: " + libro.getAutor());
        h.tvEditorial.setText("Editorial: " + libro.getEditorial());
        h.tvPaginas.setText("Páginas: " + libro.getPaginas());
        h.tvDisponible.setText(libro.isDisponible() ? "Disponible: Sí" : "Disponible: No");

        if (modoFavoritos) {
            h.btnFavorito.setText("Quitar");
        } else {
            h.btnFavorito.setText(esFavorito ? "★ Quitar de favoritos" : "☆ Agregar a favoritos");
        }

        h.btnDetalle.setOnClickListener(v -> listener.onDetalle(libro));
        h.btnFavorito.setOnClickListener(v -> listener.onFavorito(libro, esFavorito));
    }

    @Override
    public int getItemCount() { return libros.size(); }

    static class LibroViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvTitulo, tvAutor, tvEditorial, tvPaginas, tvDisponible;
        Button btnDetalle, btnFavorito;

        LibroViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvId);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvAutor = itemView.findViewById(R.id.tvAutor);
            tvEditorial = itemView.findViewById(R.id.tvEditorial);
            tvPaginas = itemView.findViewById(R.id.tvPaginas);
            tvDisponible = itemView.findViewById(R.id.tvDisponible);
            btnDetalle = itemView.findViewById(R.id.btnDetalle);
            btnFavorito = itemView.findViewById(R.id.btnFavorito);
        }
    }
}