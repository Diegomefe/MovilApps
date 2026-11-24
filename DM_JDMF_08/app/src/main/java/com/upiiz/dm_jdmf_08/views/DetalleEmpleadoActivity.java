package com.upiiz.dm_jdmf_08.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.upiiz.dm_jdmf_08.R;
import com.upiiz.dm_jdmf_08.models.Cliente;
import com.upiiz.dm_jdmf_08.models.RespuestaCliente;
import com.upiiz.dm_jdmf_08.models.RespuestaEliminarCliente;
import com.upiiz.dm_jdmf_08.network.ClienteRetrofit;

public class DetalleEmpleadoActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "empleado_id";

    private TextView tvNombre, tvId, tvCorreo, tvTelefono, tvDireccion, tvRegistrado;
    private Button btnEditar, btnEliminar;
    private ProgressBar progressBar;
    private int clienteId;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_empleado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvNombre = findViewById(R.id.tvNombre);
        tvId = findViewById(R.id.tvId);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvTelefono = findViewById(R.id.tvTelefono);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvRegistrado = findViewById(R.id.tvRegistrado);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        progressBar = findViewById(R.id.progressBar);

        clienteId = getIntent().getIntExtra(EXTRA_ID, -1);
        if (clienteId == -1) {
            Toast.makeText(this, "Empleado no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);

        btnEditar.setOnClickListener(v -> {
            Intent i = new Intent(this, FormularioEmpleadoActivity.class);
            i.putExtra(FormularioEmpleadoActivity.EXTRA_ID, clienteId);
            startActivity(i);
        });
        btnEliminar.setOnClickListener(v -> confirmarEliminar());
    }

    // Recarga al volver del formulario de edición
    @Override
    protected void onResume() {
        super.onResume();
        if (clienteId != -1) cargarDetalle();
    }

    private void cargarDetalle() {
        progressBar.setVisibility(View.VISIBLE);
        ClienteRetrofit.obtenerApiClientes().obtenerClientePorId(clienteId).enqueue(new Callback<RespuestaCliente>() {
            @Override
            public void onResponse(Call<RespuestaCliente> call, Response<RespuestaCliente> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getDatos() != null) {
                    mostrar(response.body().getDatos());
                } else {
                    Toast.makeText(DetalleEmpleadoActivity.this,
                            "Error al cargar", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaCliente> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DetalleEmpleadoActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrar(Cliente c) {
        cliente = c;
        tvNombre.setText(c.getNombre());
        tvId.setText("ID: " + c.getId());
        tvCorreo.setText("Correo: " + c.getCorreo());
        tvTelefono.setText("Teléfono: " + c.getTelefono());
        tvDireccion.setText("Dirección: " + c.getDireccion());
        tvRegistrado.setText(c.getRegistrado() ? "Registrado: Sí" : "Registrado: No");
        btnEditar.setEnabled(true);
        btnEliminar.setEnabled(true);
    }

    private void confirmarEliminar() {
        if (cliente == null) return;
        new AlertDialog.Builder(this)
                .setTitle("Eliminar empleado")
                .setMessage("¿Seguro que deseas eliminar a " + cliente.getNombre() + "?")
                .setPositiveButton("Eliminar", (d, w) -> eliminar())
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminar() {
        progressBar.setVisibility(View.VISIBLE);
        btnEliminar.setEnabled(false);
        ClienteRetrofit.obtenerApiClientes().eliminarCliente(clienteId).enqueue(new Callback<RespuestaEliminarCliente>() {
            @Override
            public void onResponse(Call<RespuestaEliminarCliente> call, Response<RespuestaEliminarCliente> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    String msg = (response.body() != null && response.body().getMensaje() != null)
                            ? response.body().getMensaje() : "Empleado eliminado correctamente";
                    Toast.makeText(DetalleEmpleadoActivity.this, msg, Toast.LENGTH_LONG).show();
                    finish(); // el listado se actualiza en su onResume
                } else {
                    btnEliminar.setEnabled(true);
                    Toast.makeText(DetalleEmpleadoActivity.this,
                            "Error al cargar", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaEliminarCliente> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnEliminar.setEnabled(true);
                Toast.makeText(DetalleEmpleadoActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}