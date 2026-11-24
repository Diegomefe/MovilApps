package com.upiiz.dm_jdmf_08.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upiiz.dm_jdmf_08.R;
import com.upiiz.dm_jdmf_08.models.Cliente;

import java.util.ArrayList;
import java.util.List;
public class EmpleadoAdapter extends RecyclerView.Adapter<EmpleadoAdapter.EmpleadoViewHolder> {

    public interface OnEmpleadoClickListener {
        void onClick(Cliente cliente);
    }

    private final List<Cliente> clientes = new ArrayList<>();
    private final OnEmpleadoClickListener listener;

    public EmpleadoAdapter(OnEmpleadoClickListener listener) {
        this.listener = listener;
    }

    public void setEmpleados(List<Cliente> nuevos) {
        clientes.clear();
        if (nuevos != null) clientes.addAll(nuevos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmpleadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empleado, parent, false);
        return new EmpleadoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpleadoViewHolder h, int position) {
        Cliente c = clientes.get(position);
        h.tvId.setText("ID: " + c.getId());
        h.tvNombre.setText(c.getNombre());
        h.tvCorreo.setText(c.getCorreo());
        h.tvTelefono.setText(c.getTelefono());
        h.itemView.setOnClickListener(v -> listener.onClick(c));
    }

    @Override
    public int getItemCount() { return clientes.size(); }

    static class EmpleadoViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvNombre, tvCorreo, tvTelefono;

        EmpleadoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvIdEmpleado);
            tvNombre = itemView.findViewById(R.id.tvNombreEmpleado);
            tvCorreo = itemView.findViewById(R.id.tvCorreoEmpleado);
            tvTelefono = itemView.findViewById(R.id.tvTelefonoEmpleado);
        }
    }
}