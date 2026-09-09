package com.upiiz.dm_jdmf_02;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //1.- Declarar variables

    EditText etNombre;
    TextView tvSaludo;
    Button btnSaludo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Revisar la vista que se este cargando
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //2.- Enlazar variables con vistas (layout, button, textView, etc.) Null pointer exception/error
        etNombre = findViewById(R.id.etNombre);
        tvSaludo = findViewById((R.id.tvSaludo));
        btnSaludo = findViewById(R.id.btnSaludo);
        //3.- Desarrollo
        //3.1 El boton escuche los clicks en el emulador o touch en el celular/tablet
        btnSaludo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Debemos de saludar: Hola, soy Robot
        //1.- Obtener el nombre del etNombre
        String nombre = etNombre.getText().toString();
        //2.- Concatenar Hola, soy + el contenido de etNombre
        String saludo = "Hola, soy " + nombre;
        //3.- Asignarlo a tvSaludo
        tvSaludo.setText(saludo);

    }
}