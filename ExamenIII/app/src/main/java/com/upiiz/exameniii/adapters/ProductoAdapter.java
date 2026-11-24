package com.upiiz.exameniii.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.upiiz.exameniii.R;
import com.upiiz.exameniii.models.Producto;

import java.util.List;
import java.util.Locale;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    public interface OnProductoClickListener {
        void onProductoClick(Producto producto);
    }

    private List<Producto> productos;
    private final OnProductoClickListener listener;

    public ProductoAdapter(List<Producto> productos, OnProductoClickListener listener) {
        this.productos = productos;
        this.listener = listener;
    }

    public void actualizarLista(List<Producto> nuevaLista) {
        this.productos = nuevaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto p = productos.get(position);

        holder.tvNombre.setText(p.getTitle());
        holder.tvDescripcion.setText(p.getDescription());
        holder.tvPrecio.setText(String.format(Locale.US, "$%.2f", p.getPrice()));
        holder.tvCategoria.setText(p.getCategory());

        Glide.with(holder.itemView.getContext())
                .load(p.getThumbnail())
                .into(holder.ivProducto);

        holder.itemView.setOnClickListener(v -> listener.onProductoClick(p));
    }

    @Override
    public int getItemCount() {
        return productos == null ? 0 : productos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProducto;
        TextView tvNombre, tvDescripcion, tvPrecio, tvCategoria;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProducto = itemView.findViewById(R.id.ivProducto);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvCategoria = itemView.findViewById(R.id.tvCategoria);
        }
    }
}