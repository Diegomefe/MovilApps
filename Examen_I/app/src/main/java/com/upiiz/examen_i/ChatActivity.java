package com.upiiz.examen_i;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.examen_i.adapters.MensajeAdapter;

import java.util.ArrayList;
import java.util.List;

import Models.Mensaje;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lvMensajes;
    private List<Mensaje> listaMensajes;
    private MensajeAdapter adapter;
    private TextView nombreContacto;
    private ImageButton btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvMensajes = findViewById(R.id.lvMensajes);
        listaMensajes = new ArrayList<>();
        nombreContacto = findViewById(R.id.txtNombreContacto);
        btnAtras = findViewById(R.id.btnAtras);

        btnAtras.setOnClickListener(this);


        String nameContacto = getIntent().getStringExtra("nombre_usuario");

        if (nameContacto != null && !nameContacto.isEmpty()) {
            nombreContacto.setText(nameContacto);
        }


        listaMensajes.add(new Mensaje("Hola, ¿cómo estás?", "09:30", true));
        listaMensajes.add(new Mensaje("¡Hola! Muy bien, ¿y tú?", "09:31", false));
        listaMensajes.add(new Mensaje("Todo bien, ¿quieres vernos más tarde?", "09:32", true));
        listaMensajes.add(new Mensaje("Sí, me parece bien. ¿A qué hora?", "09:33", false));
        listaMensajes.add(new Mensaje("¿Te parece a las 5?", "09:34", true));
        listaMensajes.add(new Mensaje("Perfecto, nos vemos entonces.", "09:35", false));
        listaMensajes.add(new Mensaje("¿En la cafetería de siempre?", "09:36", true));
        listaMensajes.add(new Mensaje("Mejor en la plaza central.", "09:37", false));
        listaMensajes.add(new Mensaje("Vale, me queda más cerca.", "09:38", true));
        listaMensajes.add(new Mensaje("¿Llevarás el libro que me comentaste?", "09:39", false));
        listaMensajes.add(new Mensaje("Sí, ya lo tengo en la mochila.", "09:40", true));
        listaMensajes.add(new Mensaje("¡Genial, gracias!", "09:41", false));
        listaMensajes.add(new Mensaje("¿Alguien más vendrá?", "09:42", true));
        listaMensajes.add(new Mensaje("Quizá Carlos nos alcance después.", "09:43", false));
        listaMensajes.add(new Mensaje("Excelente, le avisaré.", "09:44", true));
        listaMensajes.add(new Mensaje("No te preocupes, ya le escribí.", "09:45", false));
        listaMensajes.add(new Mensaje("De acuerdo, nos ahorramos el mensaje.", "09:46", true));
        listaMensajes.add(new Mensaje("Jaja sí. Llega puntual.", "09:47", false));
        listaMensajes.add(new Mensaje("Siempre llego a tiempo.", "09:48", true));
        listaMensajes.add(new Mensaje("¡Ya lo veremos! Hasta pronto.", "09:49", false));

        adapter = new MensajeAdapter(this, listaMensajes);
        lvMensajes.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ContactosActivity.class);
        startActivity(intent);
    }
}