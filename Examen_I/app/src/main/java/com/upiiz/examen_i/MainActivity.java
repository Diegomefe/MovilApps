package com.upiiz.examen_i;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUsuario;
    EditText etContrasena;
    Button btnInicioSesion;
    Button btnCrearCuenta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        btnInicioSesion = findViewById(R.id.btnInicioSesion);

        btnInicioSesion.setOnClickListener(this);
        btnCrearCuenta.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnInicioSesion){
            String user = etUsuario.getText().toString();
            String contra = etContrasena.getText().toString();
            if (user.equals("admin")){
                if (contra.equals("123456")){
                    Intent intent = new Intent(this, ContactosActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "El usuario es incorrecto", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.btnCrearCuenta) {
            Intent intent = new Intent(this, RegistroActivity.class);
            startActivity(intent);
        }
    }
}