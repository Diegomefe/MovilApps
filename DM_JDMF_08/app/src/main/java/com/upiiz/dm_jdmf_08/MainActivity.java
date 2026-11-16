package com.upiiz.dm_jdmf_08;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.upiiz.dm_jdmf_08.models.Cliente;
import com.upiiz.dm_jdmf_08.models.RespuestaClientes;
import com.upiiz.dm_jdmf_08.network.ClienteRetrofit;
import com.upiiz.dm_jdmf_08.network.api.ApiCliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //Declarar nuestra API - Clientes
    ApiCliente apiCliente;

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
        //Obtener la instancia

        apiCliente = ClienteRetrofit.obtenerApiClientes();
        obtenerClientes();

    }

    private void obtenerClientes() {
        //Todos los clientes que se obtienen ingresarlos a un ListView
        apiCliente.obtenerClientes().enqueue(new Callback<RespuestaClientes>() {
            @Override
            public void onResponse(Call<RespuestaClientes> call, Response<RespuestaClientes> response) {
                //Cuando nos responde el servidor
                //200, 400, 500
                // isSuccessful() = >= 200 && <= 299
                if (response.isSuccessful() && response.body() != null){
                    //Tiene exito y trae datos
                    //Obtener los clientes del body
                    List<Cliente> clientes = response.body().getDatos().getContent();
                    clientes.forEach(cliente -> {
                        cliente.getNombre()
                    });
                    Toast.makeText(MainActivity.this, "Datos:"+response.body().toString(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "No existen clientes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespuestaClientes> call, Throwable t) {
                //Error producido cuando no nos podemos conectar al servidor o tardo mucho tiempo
                //Error de comunicación
                //Sin internet
                //El servidor no responde

                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}