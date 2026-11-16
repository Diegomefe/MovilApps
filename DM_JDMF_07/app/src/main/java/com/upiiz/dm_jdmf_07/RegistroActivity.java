package com.upiiz.dm_jdmf_07;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.dm_jdmf_07.data.db.CrudVeterinario;
import com.upiiz.dm_jdmf_07.data.entities.Veterinario;

public class RegistroActivity extends AppCompatActivity {

    private EditText etCorreo, etContraReg, etNombre;
    private Button btnContinuar, btnCuentaExist;
    private CrudVeterinario crudVeterinario;

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

        etCorreo = findViewById(R.id.etCorreo);
        etContraReg = findViewById(R.id.etContraReg);
        etNombre = findViewById(R.id.etNombre);
        btnContinuar = findViewById(R.id.btnContinuar);
        btnCuentaExist = findViewById(R.id.btnCuentaExist);

        crudVeterinario = new CrudVeterinario(this);

        btnContinuar.setOnClickListener(v -> registrarVeterinario());

        btnCuentaExist.setOnClickListener(v -> {
            // Regresar a la pantalla de login (MainActivity)
            finish();
        });
    }

    private void registrarVeterinario() {
        String correo = etCorreo.getText().toString().trim();
        String contra = etContraReg.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();

        // Validar campos vacíos
        if (correo.isEmpty() || contra.isEmpty() || nombre.isEmpty()) {
            Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el correo ya está registrado antes de crear
        crudVeterinario.obtenerPorCorreo(correo, existente -> {
            if (existente != null) {
                Toast.makeText(RegistroActivity.this, "El correo ya se encuentra registrado", Toast.LENGTH_SHORT).show();
            } else {
                Veterinario nuevoVeterinario = new Veterinario(null, nombre, correo, contra, 0,null);
                crudVeterinario.crear(nuevoVeterinario, () -> {
                    Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                });
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}