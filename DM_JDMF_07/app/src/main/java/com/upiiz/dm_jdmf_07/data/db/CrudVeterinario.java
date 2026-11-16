package com.upiiz.dm_jdmf_07.data.db;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import com.upiiz.dm_jdmf_07.data.entities.Mascota;
import com.upiiz.dm_jdmf_07.data.entities.Veterinario;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrudVeterinario {
    //Manejar el CRUD
    //Intanciar la db
    //Usar hilos para realizar la CRUD

    AppDataBase db;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public CrudVeterinario(Context context){
        db = Room.databaseBuilder(context, AppDataBase.class,"veterinaria").build();
    }

    public void crear(Veterinario veterinario, Runnable onFinished){
        executorService.execute(()->{
            //Mandamos a segundo plano la operacion de ID (Input - Output)
            db.veterinarioDao().crear(veterinario);
            if (onFinished != null) {
                new Handler(Looper.getMainLooper()).post(onFinished);
            }
        });
    }

    public void obtenerPorCorreo(String correo, CrudVeterinario.OnCategoriaLoadedListener onCategoriaLoadedListener){
        executorService.execute(()->{
            Veterinario veterinario = db.veterinarioDao().obtenerPorCorreo(correo);
            new Handler(Looper.getMainLooper()).post(()->{
                onCategoriaLoadedListener.onComplete(veterinario);
            });
        });
    }

    public void obtenerTodas(CrudVeterinario.OnCategoriasLoadedListener onCategoriasLoadedListener){
        //Segundo plano - Hilo Secundario - lo que no sea UI
        executorService.execute(()->{
            List<Veterinario> veterinarios = db.veterinarioDao().ObtenerTodos();
            //Si vamos aqui, ya tengo las mascotas
            //Requiero regresarlas a primer plano - Hilo principal - UI
            new Handler(Looper.getMainLooper()).post(()->{
                //MainLooper - Hilo principal - UI
                onCategoriasLoadedListener.onComplete(veterinarios);
            });
        });
    }

    public void obtenerPorId(Long id, CrudVeterinario.OnCategoriaLoadedListener onCategoriaLoadedListener){
        executorService.execute(()->{
            Veterinario veterinario = db.veterinarioDao().obtenerPorId(id);
            new Handler(Looper.getMainLooper()).post(()->{
                onCategoriaLoadedListener.onComplete(veterinario);
            });
        });
    }



    public interface OnCategoriaLoadedListener{
        void onComplete(Veterinario veterinario);
    }

    //Interface para obtenerUna

    public interface OnCategoriasLoadedListener{
        void onComplete(List<Veterinario> veterinarios);
    }
}
