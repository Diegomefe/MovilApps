package com.upiiz.dm_jdmf_08;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.text.InputType;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.upiiz.dm_jdmf_08.models.Cliente;
import com.upiiz.dm_jdmf_08.models.PeticionClientes;
import com.upiiz.dm_jdmf_08.models.RespuestaCliente;
import com.upiiz.dm_jdmf_08.models.RespuestaClientes;
import com.upiiz.dm_jdmf_08.models.RespuestaEliminarCliente;
import com.upiiz.dm_jdmf_08.network.ClienteRetrofit;
import com.upiiz.dm_jdmf_08.network.api.ApiCliente;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // Declarar nuestra API - Clientes
    ApiCliente apiCliente;

    private ClienteAdapter adapter;

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

        // Obtener la instancia
        apiCliente = ClienteRetrofit.obtenerApiClientes();

        RecyclerView rv = findViewById(R.id.rvEmpleados);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClienteAdapter();
        rv.setAdapter(adapter);

        findViewById(R.id.btnNuevo).setOnClickListener(v -> mostrarFormulario(null));
    }

    // Al abrir o volver a la pantalla el listado se actualiza
    @Override
    protected void onResume() {
        super.onResume();
        obtenerClientes();
    }

    private void obtenerClientes() {
        apiCliente.obtenerClientes().enqueue(new Callback<RespuestaClientes>() {
            @Override
            public void onResponse(Call<RespuestaClientes> call, Response<RespuestaClientes> response) {
                // isSuccessful() = >= 200 && <= 299
                if (response.isSuccessful() && response.body() != null
                        && response.body().getDatos() != null) {
                    List<Cliente> clientes = response.body().getDatos().getContent();
                    adapter.setClientes(clientes != null ? clientes : new ArrayList<>());
                    if (clientes == null || clientes.isEmpty()) {
                        Toast.makeText(MainActivity.this,
                                "No existen clientes registrados", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this,
                            "Error del servidor: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaClientes> call, Throwable t) {
                // Sin internet, el servidor no responde o tardó demasiado
                Toast.makeText(MainActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Al seleccionar un empleado: consulta por id y muestra la información completa
    private void obtenerDetalle(int id) {
        apiCliente.obtenerClientePorId(id).enqueue(new Callback<RespuestaCliente>() {
            @Override
            public void onResponse(Call<RespuestaCliente> call, Response<RespuestaCliente> response) {
                if (response.isSuccessful() && response.body() != null
                        && response.body().getDatos() != null) {
                    mostrarDetalle(response.body().getDatos());
                } else {
                    Toast.makeText(MainActivity.this,
                            "Error del servidor: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaCliente> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarDetalle(Cliente c) {
        String mensaje = "ID: " + c.getId()
                + "\nNombre: " + c.getNombre()
                + "\nCorreo: " + c.getCorreo()
                + "\nTeléfono: " + c.getTelefono()
                + "\nDirección: " + c.getDireccion()
                + "\nRegistrado: " + (c.getRegistrado() ? "Sí" : "No");
        new AlertDialog.Builder(this)
                .setTitle("Información del empleado")
                .setMessage(mensaje)
                .setPositiveButton("Editar", (d, w) -> mostrarFormulario(c))
                .setNegativeButton("Cerrar", null)
                .show();
    }

    private void mostrarFormulario(Cliente cliente) {
        boolean editando = cliente != null;

        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.VERTICAL);
        form.setPadding(dp(24), dp(16), dp(24), 0);

        if (editando) {
            TextView tvId = new TextView(this);
            tvId.setText("ID: " + cliente.getId());
            tvId.setTypeface(null, Typeface.BOLD);
            form.addView(tvId);
        }

        EditText etNombre = campo("Nombre completo",
                InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        EditText etCorreo = campo("Correo electrónico",
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS | InputType.TYPE_CLASS_TEXT);
        EditText etTelefono = campo("Teléfono", InputType.TYPE_CLASS_PHONE);
        EditText etDireccion = campo("Dirección", InputType.TYPE_CLASS_TEXT);
        CheckBox cbRegistrado = new CheckBox(this);
        cbRegistrado.setText("Registrado");

        if (editando) {
            etNombre.setText(cliente.getNombre());
            etCorreo.setText(cliente.getCorreo());
            etTelefono.setText(cliente.getTelefono());
            etDireccion.setText(cliente.getDireccion());
            cbRegistrado.setChecked(cliente.getRegistrado());
        }

        form.addView(etNombre);
        form.addView(etCorreo);
        form.addView(etTelefono);
        form.addView(etDireccion);
        form.addView(cbRegistrado);

        AlertDialog dialogo = new AlertDialog.Builder(this)
                .setTitle(editando ? "Editar empleado" : "Nuevo empleado")
                .setView(form)
                .setPositiveButton("Guardar", null) // se define abajo para no cerrar si hay errores
                .setNegativeButton("Cancelar", null)
                .create();
        dialogo.show();

        dialogo.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            String direccion = etDireccion.getText().toString().trim();

            if (!validar(etNombre, etCorreo, etTelefono, etDireccion,
                    nombre, correo, telefono, direccion)) return;

            dialogo.dismiss();
            if (editando) {
                // PUT: el cuerpo lleva el id
                PeticionClientes peticion = new PeticionClientes(
                        cliente.getId(), nombre, correo, telefono, direccion, cbRegistrado.isChecked());
                actualizarCliente(cliente.getId(), peticion);
            } else {
                // POST: sin id
                PeticionClientes peticion = new PeticionClientes(
                        nombre, correo, telefono, direccion, cbRegistrado.isChecked());
                crearCliente(peticion);
            }
        });
    }

    private boolean validar(EditText etNombre, EditText etCorreo, EditText etTelefono,
                            EditText etDireccion, String nombre, String correo,
                            String telefono, String direccion) {
        boolean ok = true;

        if (nombre.isEmpty()) {
            etNombre.setError("El nombre es obligatorio");
            ok = false;
        } else if (!nombre.matches("^[\\p{L} .'-]+$")) {
            etNombre.setError("Solo letras y espacios");
            ok = false;
        }

        if (correo.isEmpty()) {
            etCorreo.setError("El correo es obligatorio");
            ok = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            etCorreo.setError("Formato de correo no válido");
            ok = false;
        }

        if (telefono.isEmpty()) {
            etTelefono.setError("El teléfono es obligatorio");
            ok = false;
        } else if (!telefono.matches("^\\+?[0-9]{10,15}$")) {
            etTelefono.setError("Solo números (10 a 15 dígitos)");
            ok = false;
        }

        if (direccion.isEmpty()) {
            etDireccion.setError("La dirección es obligatoria");
            ok = false;
        }
        return ok;
    }

    private void crearCliente(PeticionClientes peticion) {
        apiCliente.crearCliente(peticion).enqueue(new Callback<RespuestaCliente>() {
            @Override
            public void onResponse(Call<RespuestaCliente> call, Response<RespuestaCliente> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this,
                            "Empleado registrado correctamente", Toast.LENGTH_LONG).show();
                    obtenerClientes(); // actualizar el listado
                } else {
                    Toast.makeText(MainActivity.this,
                            "No se pudo registrar. Error " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaCliente> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void actualizarCliente(int id, PeticionClientes peticion) {
        apiCliente.actualizarCliente(id, peticion).enqueue(new Callback<RespuestaCliente>() {
            @Override
            public void onResponse(Call<RespuestaCliente> call, Response<RespuestaCliente> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this,
                            "Empleado actualizado correctamente", Toast.LENGTH_LONG).show();
                    obtenerClientes(); // actualizar el listado
                } else {
                    Toast.makeText(MainActivity.this,
                            "No se pudo actualizar. Error " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaCliente> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void confirmarEliminar(Cliente c) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar empleado")
                .setMessage("¿Seguro que deseas eliminar a " + c.getNombre() + "?")
                .setPositiveButton("Eliminar", (d, w) -> eliminarCliente(c.getId()))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminarCliente(int id) {
        apiCliente.eliminarCliente(id).enqueue(new Callback<RespuestaEliminarCliente>() {
            @Override
            public void onResponse(Call<RespuestaEliminarCliente> call, Response<RespuestaEliminarCliente> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this,
                            "Empleado eliminado correctamente", Toast.LENGTH_LONG).show();
                    obtenerClientes(); // actualizar el listado
                } else {
                    Toast.makeText(MainActivity.this,
                            "No se pudo eliminar. Error " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaEliminarCliente> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private int dp(int valor) {
        return Math.round(valor * getResources().getDisplayMetrics().density);
    }

    private EditText campo(String hint, int tipo) {
        EditText e = new EditText(this);
        e.setHint(hint);
        e.setInputType(tipo);
        e.setSingleLine(true);
        return e;
    }

    private class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.Holder> {

        private final List<Cliente> clientes = new ArrayList<>();

        void setClientes(List<Cliente> nuevos) {
            clientes.clear();
            clientes.addAll(nuevos);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout tarjeta = new LinearLayout(parent.getContext());
            tarjeta.setOrientation(LinearLayout.VERTICAL);
            tarjeta.setPadding(dp(16), dp(14), dp(16), dp(14));

            GradientDrawable fondo = new GradientDrawable();
            fondo.setColor(Color.WHITE);
            fondo.setCornerRadius(dp(18));
            fondo.setStroke(dp(1), Color.parseColor("#D6DDD9"));
            tarjeta.setBackground(fondo);
            tarjeta.setClickable(true);
            tarjeta.setFocusable(true);

            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(dp(6), dp(6), dp(6), dp(6));
            tarjeta.setLayoutParams(lp);

            TextView tvId = texto(parent, 12, "#6B7571", false);
            TextView tvNombre = texto(parent, 18, "#1B1F1D", true);
            TextView tvCorreo = texto(parent, 14, "#6B7571", false);
            TextView tvTelefono = texto(parent, 14, "#6B7571", false);

            tarjeta.addView(tvId);
            tarjeta.addView(tvNombre);
            tarjeta.addView(tvCorreo);
            tarjeta.addView(tvTelefono);
            return new Holder(tarjeta, tvId, tvNombre, tvCorreo, tvTelefono);
        }

        private TextView texto(ViewGroup parent, int tam, String color, boolean negrita) {
            TextView t = new TextView(parent.getContext());
            t.setTextSize(tam);
            t.setTextColor(Color.parseColor(color));
            if (negrita) t.setTypeface(null, Typeface.BOLD);
            return t;
        }

        @Override
        public void onBindViewHolder(@NonNull Holder h, int position) {
            Cliente c = clientes.get(position);
            h.tvId.setText("ID: " + c.getId());
            h.tvNombre.setText(c.getNombre());
            h.tvCorreo.setText(c.getCorreo());
            h.tvTelefono.setText(c.getTelefono());

            h.itemView.setOnClickListener(v -> obtenerDetalle(c.getId()));
            h.itemView.setOnLongClickListener(v -> {
                confirmarEliminar(c);
                return true;
            });
        }

        @Override
        public int getItemCount() { return clientes.size(); }

        class Holder extends RecyclerView.ViewHolder {
            TextView tvId, tvNombre, tvCorreo, tvTelefono;

            Holder(@NonNull View itemView, TextView tvId, TextView tvNombre,
                   TextView tvCorreo, TextView tvTelefono) {
                super(itemView);
                this.tvId = tvId;
                this.tvNombre = tvNombre;
                this.tvCorreo = tvCorreo;
                this.tvTelefono = tvTelefono;
            }
        }
    }
}