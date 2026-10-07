package com.upiiz.practicachat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.upiiz.practicachat.R;

import java.util.ArrayList;

import Models.Estado;

public class EstadoAdapter extends RecyclerView.Adapter<EstadoAdapter.EstadoViewHolder> {

    private Context ctx;
    private ArrayList<Estado> listaEstados;

    public EstadoAdapter(Context ctx, ArrayList<Estado> listaEstados) {
        this.ctx = ctx;
        this.listaEstados = listaEstados;
    }

    @NonNull
    @Override
    public EstadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_estado, parent, false);
        return new EstadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstadoViewHolder holder, int position) {
        Estado estado = listaEstados.get(position);

        holder.ivEstado.setImageResource(estado.getImagenEstado());
        holder.sivFoto.setImageResource(estado.getFotoPerfil());

        holder.ivEstado.setOnClickListener(v ->
                Toast.makeText(ctx, "Abriendo estado " + estado.getId(), Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return listaEstados.size();
    }

    public static class EstadoViewHolder extends RecyclerView.ViewHolder {
        ImageButton ivEstado;
        ShapeableImageView sivFoto;

        public EstadoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivEstado = itemView.findViewById(R.id.ivEstado);
            sivFoto = itemView.findViewById(R.id.sivFoto);
        }
    }
}