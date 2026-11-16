package com.upiiz.dm_jdmf_07;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.dm_jdmf_07.data.db.CrudMascota;
import com.upiiz.dm_jdmf_07.data.db.CrudVeterinario;
import com.upiiz.dm_jdmf_07.data.entities.Mascota;
import com.upiiz.dm_jdmf_07.data.entities.Veterinario;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etCorreo;
    EditText etPassword;
    Button btnIngresar;
    Button btnRegistrarse;
    CrudVeterinario crudVeterinario;


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
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etContrasena);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        crudVeterinario = new CrudVeterinario(this);

        btnRegistrarse.setOnClickListener(this);
        btnIngresar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.btnIngresar){
            validarUsuario();
        }else{
            Intent intent = new Intent(this, RegistroActivity.class);
            startActivity(intent);
        }

    }

    public void validarUsuario(){
        String correo = etCorreo.getText().toString();
        String contra = etPassword.getText().toString();

        if (correo.isEmpty() || contra.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese correo y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        crudVeterinario.obtenerPorCorreo(correo, veterinario -> {
            if (veterinario == null) {
                // El correo no existe en la base de datos
                Toast.makeText(MainActivity.this, "El usuario no está registrado", Toast.LENGTH_SHORT).show();
            } else if (!veterinario.getContrasena().equals(contra)) {
                // El usuario existe pero la contraseña no coincide
                Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
            } else {
                // Usuario y contraseña válidos
                Toast.makeText(MainActivity.this, "Bienvenido " + veterinario.getNombre(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, MascotasActivity.class);
                // Pasar el ID del veterinario para filtrar sus mascotas específicas
                intent.putExtra("VETERINARIO_ID", veterinario.getId());
                startActivity(intent);
                finish();
            }
        });
    }
}