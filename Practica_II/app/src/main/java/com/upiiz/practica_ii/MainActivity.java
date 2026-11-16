package com.upiiz.practica_ii;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import com.upiiz.practica_ii.db.PreferenciasUsuario;
import com.upiiz.practica_ii.ui.CatalogoActivity;
import com.upiiz.practica_ii.ui.FavoritosActivity;


public class MainActivity extends AppCompatActivity {

    private PreferenciasUsuario prefs;
    private TextView tvBienvenida, tvUltimaSeccion;
    private EditText etNombre;
    private RadioButton rbClaro, rbOscuro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new PreferenciasUsuario(this);

        // Aplicar el tema guardado antes de mostrar la pantalla
        AppCompatDelegate.setDefaultNightMode(
                PreferenciasUsuario.TEMA_OSCURO.equals(prefs.obtenerTema())
                        ? AppCompatDelegate.MODE_NIGHT_YES
                        : AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main);

        tvBienvenida = findViewById(R.id.tvBienvenida);
        tvUltimaSeccion = findViewById(R.id.tvUltimaSeccion);
        etNombre = findViewById(R.id.etNombre);
        rbClaro = findViewById(R.id.rbClaro);
        rbOscuro = findViewById(R.id.rbOscuro);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnCatalogo = findViewById(R.id.btnCatalogo);
        Button btnFavoritos = findViewById(R.id.btnFavoritos);

        etNombre.setText(prefs.obtenerNombre());
        rbOscuro.setChecked(PreferenciasUsuario.TEMA_OSCURO.equals(prefs.obtenerTema()));
        rbClaro.setChecked(!rbOscuro.isChecked());

        btnGuardar.setOnClickListener(v -> guardarPreferencias());
        btnCatalogo.setOnClickListener(v -> startActivity(new Intent(this, CatalogoActivity.class)));
        btnFavoritos.setOnClickListener(v -> startActivity(new Intent(this, FavoritosActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mostrarPreferencias();
    }

    private void guardarPreferencias() {
        String nombre = etNombre.getText().toString().trim();
        if (nombre.isEmpty()) {
            Toast.makeText(this, "Escribe un nombre o alias", Toast.LENGTH_SHORT).show();
            return;
        }
        prefs.guardarNombre(nombre);
        prefs.guardarTema(rbOscuro.isChecked()
                ? PreferenciasUsuario.TEMA_OSCURO
                : PreferenciasUsuario.TEMA_CLARO);
        Toast.makeText(this, "Preferencias guardadas", Toast.LENGTH_SHORT).show();

        // Cambia el tema (recrea la activity si el modo cambió)
        AppCompatDelegate.setDefaultNightMode(rbOscuro.isChecked()
                ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO);
        mostrarPreferencias();
    }

    private void mostrarPreferencias() {
        String nombre = prefs.obtenerNombre();
        tvBienvenida.setText(nombre.isEmpty() ? "¡Bienvenido!" : "¡Hola, " + nombre + "!");

        String seccion = prefs.obtenerUltimaSeccion();
        String texto;
        if (PreferenciasUsuario.SECCION_CATALOGO.equals(seccion)) texto = "Catálogo";
        else if (PreferenciasUsuario.SECCION_FAVORITOS.equals(seccion)) texto = "Favoritos";
        else texto = "Ninguna todavía";
        tvUltimaSeccion.setText("Última sección visitada: " + texto
                + "\nTema actual: " + prefs.obtenerTema());
    }
}