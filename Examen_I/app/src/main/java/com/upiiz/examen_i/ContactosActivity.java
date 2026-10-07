package com.upiiz.examen_i;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.examen_i.adapters.ContactoAdapter;

import java.util.ArrayList;
import java.util.List;

import Models.Contacto;

public class ContactosActivity extends AppCompatActivity {

    private ListView lvChats;
    private List<Contacto> listaUsuarios;
    private ContactoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contactos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvChats = findViewById(R.id.lvChats);
        listaUsuarios = new ArrayList<>();

        listaUsuarios.add(new Contacto("Ana García", "ana123", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Luis Martínez", "luism", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Marta López", "marta_22", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Pedro Sánchez", "pedro99", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Sofía Torres", "sofia_t", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Juan Rodríguez", "juanr", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Elena Gómez", "elena_g", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Carlos Ruiz", "carlos_r", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Lucía Morales", "lucia_m", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Diego Herrera", "diego_h", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Valentina Castro", "vale_c", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Fernando Díaz", "fer_diaz", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Camila Vargas", "cami_v", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Mateo Ortiz", "mateo_o", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Isabella Mendoza", "isa_men", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Andrés Silva", "andres_s", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Paula Romero", "paula_r", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Gabriel Flores", "gabo_f", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Daniela Peña", "dani_p", R.drawable.perfil));
        listaUsuarios.add(new Contacto("Alejandro Cruz", "alex_cruz", R.drawable.perfil));

        adapter = new ContactoAdapter(this, listaUsuarios);
        lvChats.setAdapter(adapter);

        // Click / touch sobre cualquier usuario -> abrir ChatActivity
        lvChats.setOnItemClickListener((parent, view, position, id) -> {
            Contacto usuarioSeleccionado = listaUsuarios.get(position);
            Intent intent = new Intent(ContactosActivity.this, ChatActivity.class);
            intent.putExtra("nombre_usuario", usuarioSeleccionado.getNombre());
            startActivity(intent);
        });
    }
}