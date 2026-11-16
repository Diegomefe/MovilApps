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

public class ActualizarActivity extends AppCompatActivity {
    private EditText etNombre, etRaza, etEdad;
    private Button btnActualizar, btnRegresar;
    private CrudMascota crudMascota;
    private Long veterinarioId;
    private Long mascotaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        crudMascota = new CrudMascota(this);

        etNombre = findViewById(R.id.etNombrePerro);
        etRaza = findViewById(R.id.etRaza);
        etEdad = findViewById(R.id.etEdad);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnRegresar = findViewById(R.id.btnRegresar);

        mascotaId = getIntent().getLongExtra("MASCOTA_ID", -1L);
        veterinarioId = getIntent().getLongExtra("VETERINARIO_ID", -1L);

        if (mascotaId != -1L) {
            cargarDatosMascota();
        }

        btnActualizar.setOnClickListener(v -> actualizarMascota());
        btnRegresar.setOnClickListener(v -> finish());
    }

    private void cargarDatosMascota() {
        crudMascota.obtenerPorId(mascotaId, mascota -> {
            if (mascota != null) {
                etNombre.setText(mascota.getNombre());
                etRaza.setText(mascota.getRaza());
                etEdad.setText(String.valueOf(mascota.getEdad()));
            }
        });
    }

    private void actualizarMascota() {
        String nombre = etNombre.getText().toString().trim();
        String raza = etRaza.getText().toString().trim();
        String edadStr = etEdad.getText().toString().trim();

        if (nombre.isEmpty() || raza.isEmpty() || edadStr.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int edad = Integer.parseInt(edadStr);
        Mascota mascotaActualizada = new Mascota(mascotaId, nombre, raza, edad,veterinarioId);
        crudMascota.actualizar(mascotaActualizada);

        Toast.makeText(this, "Mascota actualizada", Toast.LENGTH_SHORT).show();
        finish();
    }
}