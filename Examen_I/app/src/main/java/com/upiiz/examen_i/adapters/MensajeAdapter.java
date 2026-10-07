package com.upiiz.examen_i.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.upiiz.examen_i.R;

import java.util.List;

import Models.Mensaje;

public class MensajeAdapter extends BaseAdapter {

    private Context ctx;
    private List<Mensaje> listaMensajes;

    public MensajeAdapter(Context ctx, List<Mensaje> listaMensajes) {
        this.ctx = ctx;
        this.listaMensajes = listaMensajes;
    }

    @Override
    public int getCount() {
        return listaMensajes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaMensajes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_mensaje, parent, false);
        }

        Mensaje mensaje = listaMensajes.get(position);

        LinearLayout layoutRecibido = convertView.findViewById(R.id.layoutRecibido);
        LinearLayout layoutEnviado = convertView.findViewById(R.id.layoutEnviado);
        TextView txtTextoRecibido = convertView.findViewById(R.id.txtTextoRecibido);
        TextView txtHoraRecibido = convertView.findViewById(R.id.txtHoraRecibido);
        TextView txtTextoEnviado = convertView.findViewById(R.id.txtTextoEnviado);
        TextView txtHoraEnviado = convertView.findViewById(R.id.txtHoraEnviado);

        if (mensaje.isEsMio()) {
            layoutEnviado.setVisibility(View.VISIBLE);
            layoutRecibido.setVisibility(View.GONE);
            txtTextoEnviado.setText(mensaje.getTexto());
            txtHoraEnviado.setText(mensaje.getHora() + " ✓✓");
        } else {
            layoutRecibido.setVisibility(View.VISIBLE);
            layoutEnviado.setVisibility(View.GONE);
            txtTextoRecibido.setText(mensaje.getTexto());
            txtHoraRecibido.setText(mensaje.getHora());
        }

        return convertView;
    }
}
