package com.upiiz.dm_jdmf_07;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.dm_jdmf_07.data.db.CrudMascota;
import com.upiiz.dm_jdmf_07.data.entities.Mascota;

import java.util.ArrayList;
import java.util.List;

public class MascotasActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lvChats;
    private CrudMascota crudMascota;
    private List<Mascota> listaMascotas = new ArrayList<>();
    private ImageButton agregarMascota;
    private Button btnSalir;
    private ArrayAdapter<Mascota> adaptador;
    private Long veterinarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mascotas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        veterinarioId = getIntent().getLongExtra("VETERINARIO_ID", -1L);
        crudMascota = new CrudMascota(this);
        lvChats = findViewById(R.id.lvChats);
        agregarMascota = findViewById(R.id.btnAnadir);
        btnSalir = findViewById(R.id.btnSalir);

        agregarMascota.setOnClickListener(this);
        btnSalir.setOnClickListener(this);

        // Inicializar el adaptador
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaMascotas);
        lvChats.setAdapter(adaptador);

        lvChats.setOnItemClickListener((parent, view, position, id) -> {
            Mascota seleccionada = listaMascotas.get(position);
            Intent intent = new Intent(this, ActualizarActivity.class);
            intent.putExtra("MASCOTA_ID", seleccionada.getId());
            intent.putExtra("VETERINARIO_ID", veterinarioId);
            startActivity(intent);
        });

        lvChats.setOnItemLongClickListener((parent, view, position, id) -> {
            Mascota seleccionada = listaMascotas.get(position);
            mostrarDialogoEliminar(seleccionada);
            return true;
        });

        mostrarListaMascotas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Vuelve a consultar la base de datos al regresar de otra pantalla
        mostrarListaMascotas();
    }

    private void mostrarDialogoEliminar(Mascota mascota) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_eliminar);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }

        TextView tvMensaje = dialog.findViewById(R.id.tvDialogMensaje);
        Button btnCancelar = dialog.findViewById(R.id.btnCancelar);
        Button btnConfirmar = dialog.findViewById(R.id.btnConfirmar);

        tvMensaje.setText("¿Está seguro que quiere eliminar a la mascota " + mascota.getNombre() + "?");

        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        btnConfirmar.setOnClickListener(v -> {
            crudMascota.eliminar(mascota);
            dialog.dismiss();
            mostrarListaMascotas();
        });

        dialog.show();
    }

    private void mostrarListaMascotas(){
        if (veterinarioId != -1L) {
            crudMascota.obtenerPorVeterinarioId(veterinarioId, mascotas -> {
                listaMascotas.clear();
                listaMascotas.addAll(mascotas);
                adaptador.notifyDataSetChanged();
            });
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btnAnadir){
            Intent intent = new Intent(this, AgregarActivity.class);
            intent.putExtra("VETERINARIO_ID", veterinarioId);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}