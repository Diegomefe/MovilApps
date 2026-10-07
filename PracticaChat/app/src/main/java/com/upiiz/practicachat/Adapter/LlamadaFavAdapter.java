package com.upiiz.practicachat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.upiiz.practicachat.R;

import Models.Llamada;

import java.util.ArrayList;

public class LlamadaFavAdapter extends BaseAdapter {
    private Context ctx;
    private ArrayList<Llamada> listaLlamadas;

    public LlamadaFavAdapter(Context ctx, ArrayList<Llamada> listaLlamadas) {
        this.ctx = ctx;
        this.listaLlamadas = listaLlamadas;
    }

    @Override
    public int getCount() {
        return listaLlamadas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaLlamadas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_llamada_fav, parent, false);
        }

        ImageView ivUser = convertView.findViewById(R.id.ivUser);
        TextView tvUserOrPhone = convertView.findViewById(R.id.tvUser);

        Llamada llamada = listaLlamadas.get(position);

        ivUser.setImageResource(llamada.getImagenUsuario());
        tvUserOrPhone.setText(llamada.getUsuario());

        return convertView;
    }
}