package com.upiiz.dm_jdmf_08.views;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.dm_jdmf_08.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.upiiz.dm_jdmf_08.models.Cliente;
import com.upiiz.dm_jdmf_08.models.PeticionClientes;
import com.upiiz.dm_jdmf_08.models.RespuestaCliente;
import com.upiiz.dm_jdmf_08.network.ClienteRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioEmpleadoActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "empleado_id";
    private TextView tvTituloForm, tvIdForm;
    private TextInputLayout tilNombre, tilCorreo, tilTelefono, tilDireccion;
    private TextInputEditText etNombre, etCorreo, etTelefono, etDireccion;
    private CheckBox cbRegistrado;
    private Button btnGuardar, btnCancelar;
    private ProgressBar progressBar;

    private int clienteId = -1; // -1 = crear, otro valor = editar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario_empleado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvTituloForm = findViewById(R.id.tvTituloForm);
        tvIdForm = findViewById(R.id.tvIdForm);
        tilNombre = findViewById(R.id.tilNombre);
        tilCorreo = findViewById(R.id.tilCorreo);
        tilTelefono = findViewById(R.id.tilTelefono);
        tilDireccion = findViewById(R.id.tilDireccion);
        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etTelefono = findViewById(R.id.etTelefono);
        etDireccion = findViewById(R.id.etDireccion);
        cbRegistrado = findViewById(R.id.cbRegistrado);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);
        progressBar = findViewById(R.id.progressBar);

        btnGuardar.setOnClickListener(v -> guardar());
        btnCancelar.setOnClickListener(v -> finish());

        clienteId = getIntent().getIntExtra(EXTRA_ID, -1);
        if (clienteId != -1) {
            tvTituloForm.setText("Editar empleado");
            tvIdForm.setVisibility(View.VISIBLE);
            tvIdForm.setText("ID: " + clienteId);
            btnGuardar.setEnabled(false); // hasta cargar los datos
            cargarCliente();
        } else {
            tvTituloForm.setText("Nuevo empleado");
        }
    }

    private void cargarCliente() {
        progressBar.setVisibility(View.VISIBLE);
        ClienteRetrofit.obtenerApiClientes().obtenerClientePorId(clienteId).enqueue(new Callback<RespuestaCliente>() {
            @Override
            public void onResponse(Call<RespuestaCliente> call, Response<RespuestaCliente> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getDatos() != null) {
                    Cliente c = response.body().getDatos();
                    etNombre.setText(c.getNombre());
                    etCorreo.setText(c.getCorreo());
                    etTelefono.setText(c.getTelefono());
                    etDireccion.setText(c.getDireccion());
                    cbRegistrado.setChecked(c.getRegistrado());
                    btnGuardar.setEnabled(true);
                } else {
                    Toast.makeText(FormularioEmpleadoActivity.this,
                            "Error al cargar", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaCliente> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(FormularioEmpleadoActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validar(String nombre, String correo, String telefono, String direccion) {
        boolean ok = true;
        tilNombre.setError(null);
        tilCorreo.setError(null);
        tilTelefono.setError(null);
        tilDireccion.setError(null);

        if (nombre.isEmpty()) {
            tilNombre.setError("El nombre es obligatorio");
            ok = false;
        } else if (!nombre.matches("^[\\p{L} .'-]+$")) {
            tilNombre.setError("Solo letras y espacios");
            ok = false;
        }

        if (correo.isEmpty()) {
            tilCorreo.setError("El correo es obligatorio");
            ok = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            tilCorreo.setError("Formato de correo no válido");
            ok = false;
        }

        if (telefono.isEmpty()) {
            tilTelefono.setError("El teléfono es obligatorio");
            ok = false;
        } else if (!telefono.matches("^\\+?[0-9]{10,15}$")) {
            tilTelefono.setError("Solo números (10 a 15 dígitos)");
            ok = false;
        }

        if (direccion.isEmpty()) {
            tilDireccion.setError("La dirección es obligatoria");
            ok = false;
        }
        return ok;
    }

    private void guardar() {
        String nombre = etNombre.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String direccion = etDireccion.getText().toString().trim();

        if (!validar(nombre, correo, telefono, direccion)) return;

        PeticionClientes peticion = new PeticionClientes(
                nombre, correo, telefono, direccion, cbRegistrado.isChecked());

        progressBar.setVisibility(View.VISIBLE);
        btnGuardar.setEnabled(false);

        Call<RespuestaCliente> call = (clienteId == -1)
                ? ClienteRetrofit.obtenerApiClientes().crearCliente(peticion)
                : ClienteRetrofit.obtenerApiClientes().actualizarCliente(clienteId, peticion);

        call.enqueue(new Callback<RespuestaCliente>() {
            @Override
            public void onResponse(Call<RespuestaCliente> call, Response<RespuestaCliente> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    String porDefecto = (clienteId == -1)
                            ? "Empleado registrado correctamente"
                            : "Empleado actualizado correctamente";
                    String msg = (response.body() != null && response.body().getMensaje() != null)
                            ? response.body().getMensaje() : porDefecto;
                    Toast.makeText(FormularioEmpleadoActivity.this, msg, Toast.LENGTH_LONG).show();
                    finish(); // las pantallas anteriores se refrescan en onResume
                } else {
                    btnGuardar.setEnabled(true);
                    Toast.makeText(FormularioEmpleadoActivity.this,
                            "Error al cargar", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaCliente> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnGuardar.setEnabled(true);
                Toast.makeText(FormularioEmpleadoActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}