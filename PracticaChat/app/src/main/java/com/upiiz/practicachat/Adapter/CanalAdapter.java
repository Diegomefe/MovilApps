package com.upiiz.practicachat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.upiiz.practicachat.R;

import Models.Canal;

import java.util.ArrayList;

public class CanalAdapter extends BaseAdapter {
    private Context ctx;
    private ArrayList<Canal> listaCanales;

    public CanalAdapter(Context ctx, ArrayList<Canal> listaCanales) {
        this.ctx = ctx;
        this.listaCanales = listaCanales;
    }

    @Override
    public int getCount() {
        return listaCanales.size();
    }

    @Override
    public Object getItem(int position) {
        return listaCanales.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_canal, parent, false);
        }

        ImageView ivUser = convertView.findViewById(R.id.ivUser);
        TextView tvUser = convertView.findViewById(R.id.tvUser);
        TextView tvUltimoMensaje = convertView.findViewById(R.id.tvUltimoMensaje);
        TextView tvUltimaConexion = convertView.findViewById(R.id.tvUltimaConexion);
        TextView tvNumMensajes = convertView.findViewById(R.id.tvNumMensajes);
        Canal canal = listaCanales.get(position);

        ivUser.setImageResource(canal.getImagen());
        tvUser.setText(canal.getNombre());
        tvUltimoMensaje.setText(canal.getUltimoMensaje());
        tvUltimaConexion.setText(canal.getUltimaConexion());

        if (canal.getMensajesNoLeidos() > 0) {
            tvNumMensajes.setVisibility(View.VISIBLE);
            tvNumMensajes.setText(String.valueOf(canal.getMensajesNoLeidos()));
        } else {
            tvNumMensajes.setVisibility(View.GONE);
        }

        return convertView;
    }
}