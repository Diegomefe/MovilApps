package com.upiiz.examen_i.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.upiiz.examen_i.R;
import java.util.List;
import Models.Contacto;

public class ContactoAdapter extends BaseAdapter {

    private Context context;
    private List<Contacto> listaUsuarios;

    public ContactoAdapter(Context context, List<Contacto> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @Override
    public int getCount() {
        return listaUsuarios != null ? listaUsuarios.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return listaUsuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.usuario_item, parent, false);
        }

        Contacto usuario = listaUsuarios.get(position);

        ImageView ivUser = convertView.findViewById(R.id.ivUser);
        TextView tvUser = convertView.findViewById(R.id.tvUser);
        TextView tvUltimoMensaje = convertView.findViewById(R.id.tvUltimoMensaje);

        tvUser.setText(usuario.getNombre());
        tvUltimoMensaje.setText(usuario.getUsername());
        ivUser.setImageResource(usuario.getImagenResId());

        return convertView;
    }
}