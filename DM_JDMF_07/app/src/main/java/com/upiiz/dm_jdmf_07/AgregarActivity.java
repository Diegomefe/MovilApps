package com.upiiz.dm_jdmf_07;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.dm_jdmf_07.data.db.CrudMascota;
import com.upiiz.dm_jdmf_07.data.entities.Mascota;

public class AgregarActivity extends AppCompatActivity {
    private EditText etNombre, etRaza, etEdad;
    private Button btnGuardar, btnRegresar;
    private CrudMascota crudMascota;
    private Long veterinarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        crudMascota = new CrudMascota(this);

        veterinarioId = getIntent().getLongExtra("VETERINARIO_ID", -1L);

        etNombre = findViewById(R.id.etNombre);
        etRaza = findViewById(R.id.etRaza);
        etEdad = findViewById(R.id.etEdad);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnRegresar = findViewById(R.id.btnRegresar);

        btnGuardar.setOnClickListener(v -> guardarMascota());
        btnRegresar.setOnClickListener(v -> finish());
    }

    private void guardarMascota() {
        String nombre = etNombre.getText().toString();
        String raza = etRaza.getText().toString();
        String edadStr = etEdad.getText().toString();

        if (nombre.isEmpty() || raza.isEmpty() || edadStr.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int edad = Integer.parseInt(edadStr);
        Mascota nuevaMascota = new Mascota(null, nombre, raza, edad, veterinarioId);
        crudMascota.agregar(nuevaMascota);

        Toast.makeText(this, "Mascota guardada", Toast.LENGTH_SHORT).show();
        finish();
    }
}