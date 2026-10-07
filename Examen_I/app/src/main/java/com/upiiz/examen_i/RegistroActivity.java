package com.upiiz.examen_i;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNombre;
    private EditText etCorreo;
    private EditText etUsuario;
    private EditText etContraReg;
    private EditText etContraConf;
    private Button btnRegistrarse;
    private Button btnIrALogin;
    private ImageButton btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etUsuario = findViewById(R.id.etUsuario);
        etContraReg = findViewById(R.id.etContraReg);
        etContraConf = findViewById(R.id.etContraConf);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        btnIrALogin = findViewById(R.id.btnIrALogin);
        btnVolver = findViewById(R.id.btnVolver);

        btnRegistrarse.setOnClickListener(this);
        btnIrALogin.setOnClickListener(this);
        if (btnVolver != null) {
            btnVolver.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnRegistrarse) {
            validarRegistro();
        } else if (id == R.id.btnIrALogin || id == R.id.btnVolver) {
            irAlLogin();
        }
    }

    private void validarRegistro() {
        String nombre = etNombre.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String usuario = etUsuario.getText().toString().trim();
        String contrasena = etContraReg.getText().toString();
        String confirmacion = etContraConf.getText().toString();

        if (nombre.length() < 3) {
            etNombre.setError("El nombre debe tener mínimo 3 caracteres");
            etNombre.requestFocus();
            Toast.makeText(this, "El nombre debe tener al menos 3 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (correo.isEmpty() || usuario.isEmpty() || contrasena.isEmpty() || confirmacion.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!contrasena.equals(confirmacion)) {
            etContraConf.setError("Las contraseñas no coinciden");
            etContraConf.requestFocus();
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
        irAlLogin();
    }

    private void irAlLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}