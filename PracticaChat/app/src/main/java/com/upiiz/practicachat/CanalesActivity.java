package com.upiiz.practicachat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.imageview.ShapeableImageView;
import com.upiiz.practicachat.Adapter.CanalAdapter;
import com.upiiz.practicachat.Adapter.CanalSeguirAdapter;

import java.util.ArrayList;

import Models.Canal;
import Models.Estado;

public class CanalesActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    private ListView lvCanales;
    private ListView lvCanalesSeguir;
    private ArrayList<Canal> listaMisCanales;
    private ArrayList<Canal> listaCanalesSugeridos;
    private CanalAdapter adapterMisCanales;
    private CanalSeguirAdapter adapterCanalesSeguir;
    private ImageButton btnChats, btnEstados, btnLlamadas;
    private LinearLayout llContenedorEstados;
    private ArrayList<Estado> listaEstados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.canales_activity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvCanales = findViewById(R.id.lvCanales);
        lvCanalesSeguir = findViewById(R.id.lvCanalesSeguir);
        btnChats = findViewById(R.id.btnChats);
        btnEstados = findViewById(R.id.btnEstados);
        btnLlamadas = findViewById(R.id.btnLlamadas);
        llContenedorEstados = findViewById(R.id.llContenedorEstados);

        cargarMisCanales();
        cargarCanalesSugeridos();

        cargarEstados();
        mostrarEstadosEnHorizontalScroll();

        adapterMisCanales = new CanalAdapter(this, listaMisCanales);
        lvCanales.setAdapter(adapterMisCanales);
        ajustarAlturaListView(lvCanales);

        adapterCanalesSeguir = new CanalSeguirAdapter(this, listaCanalesSugeridos);
        lvCanalesSeguir.setAdapter(adapterCanalesSeguir);
        ajustarAlturaListView(lvCanalesSeguir);

        lvCanales.setOnItemClickListener(this);
        lvCanales.setOnItemLongClickListener(this);

        lvCanalesSeguir.setOnItemClickListener(this);
        lvCanalesSeguir.setOnItemLongClickListener(this);

        if (btnChats != null) btnChats.setOnClickListener(this);
        if (btnEstados != null) btnEstados.setOnClickListener(this);
        if (btnLlamadas != null) btnLlamadas.setOnClickListener(this);
    }

    private void cargarMisCanales() {
        listaMisCanales = new ArrayList<>();
        listaMisCanales.add(new Canal(1L, "Noticias UPIIZ", "Nueva convocatoria de movilidad 2026", "10:15", R.drawable.user, 3));
        listaMisCanales.add(new Canal(2L, "Android Developers", "Android Studio Meerkat ya disponible", "09:30", R.drawable.user, 12));
        listaMisCanales.add(new Canal(3L, "Comunidad Linux", "Lanzamiento del nuevo kernel LTS", "Ayer", R.drawable.user, 0));
        listaMisCanales.add(new Canal(4L, "Bolsa de Empleo IPN", "Ofertas para egresados en sistemas", "28/09", R.drawable.user, 1));
    }

    private void cargarCanalesSugeridos() {
        listaCanalesSugeridos = new ArrayList<>();
        listaCanalesSugeridos.add(new Canal(5L, "National Geographic", "Fotografías del día y naturaleza salvaje", "", R.drawable.user, 0));
        listaCanalesSugeridos.add(new Canal(6L, "Google News", "Resumen informativo de las últimas 24 horas", "", R.drawable.user, 0));
        listaCanalesSugeridos.add(new Canal(7L, "TechCrunch", "Últimas novedades sobre startups e inteligencia artificial", "", R.drawable.user, 0));
        listaCanalesSugeridos.add(new Canal(8L, "Champions League", "Resultados y goles de la jornada actual", "", R.drawable.user, 0));
    }

    private void cargarEstados() {
        listaEstados = new ArrayList<>();
        listaEstados.add(new Estado(1L, R.drawable.fondo, R.drawable.user, false));
        listaEstados.add(new Estado(2L, R.drawable.fondo, R.drawable.user, false));
        listaEstados.add(new Estado(3L, R.drawable.fondo, R.drawable.user, false));
        listaEstados.add(new Estado(4L, R.drawable.fondo, R.drawable.user, false));
        listaEstados.add(new Estado(5L, R.drawable.fondo, R.drawable.user, false));
    }

    private void mostrarEstadosEnHorizontalScroll() {
        llContenedorEstados.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Estado estado : listaEstados) {
            View view = inflater.inflate(R.layout.item_estado, llContenedorEstados, false);

            ImageButton ivEstado = view.findViewById(R.id.ivEstado);
            ShapeableImageView sivFoto = view.findViewById(R.id.sivFoto);

            ivEstado.setImageResource(estado.getImagenEstado());
            sivFoto.setImageResource(estado.getFotoPerfil());

            // Definir color inicial según estado
            if (estado.isVisto()) {
                sivFoto.setStrokeColor(ColorStateList.valueOf(Color.WHITE));
            } else {
                sivFoto.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.botones)));
            }

            View.OnClickListener clickListener = v -> {
                estado.setVisto(true);
                sivFoto.setStrokeColor(ColorStateList.valueOf(Color.WHITE));
                Toast.makeText(this, "Estado visto", Toast.LENGTH_SHORT).show();
            };

            ivEstado.setOnClickListener(clickListener);
            sivFoto.setOnClickListener(clickListener);

            llContenedorEstados.addView(view);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnChats) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.btnLlamadas) {
            Intent intent = new Intent(this, LlamadasActivity.class);
            startActivity(intent);
        } else if (id == R.id.btnEstados) {
            Toast.makeText(this, "Ya te encuentras en Novedades", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Diferenciar a qué ListView pertenece el toque
        if (parent.getId() == R.id.lvCanales) {
            Canal canal = listaMisCanales.get(position);
            Toast.makeText(this, "Abriendo canal seguido: " + canal.getNombre(), Toast.LENGTH_SHORT).show();
        } else if (parent.getId() == R.id.lvCanalesSeguir) {
            Canal canal = listaCanalesSugeridos.get(position);
            Toast.makeText(this, "Viendo vista previa de: " + canal.getNombre(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.lvCanales) {
            Canal canal = listaMisCanales.get(position);
            Toast.makeText(this, "Opciones de canal: " + canal.getNombre(), Toast.LENGTH_SHORT).show();
        } else if (parent.getId() == R.id.lvCanalesSeguir) {
            Canal canal = listaCanalesSugeridos.get(position);
            Toast.makeText(this, "Detalles de recomendación: " + canal.getNombre(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public static void ajustarAlturaListView(ListView listView) {
        android.widget.ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}