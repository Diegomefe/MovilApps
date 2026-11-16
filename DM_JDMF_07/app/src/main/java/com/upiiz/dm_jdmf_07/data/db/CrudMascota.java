package com.upiiz.dm_jdmf_07.data.db;

import android.content.Context;
import android.os.Looper;

import androidx.room.Room;

import com.upiiz.dm_jdmf_07.data.entities.Mascota;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;

public class CrudMascota {
    //Manejar el CRUD
    //Intanciar la db
    //Usar hilos para realizar la CRUD

    AppDataBase db;
    // Operaciones de lectura y escritura
    // Puede bloquear la pantalla
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public CrudMascota(Context context){
        db = Room.databaseBuilder(context, AppDataBase.class,"veterinaria").build();
    }

    public void actualizar(Mascota mascota){
        executorService.execute(()->{
            db.mascotaDao().actualizar(mascota);
        });

    }

    public void agregar(Mascota mascota){
        executorService.execute(()->{
            //Mandamos a segundo plano la operacion de ID (Input - Output)
            db.mascotaDao().crear(mascota);
        });


    }

    public void eliminar(Mascota mascota){
        executorService.execute(()->{
            db.mascotaDao().eliminar(mascota);
        });
    }

    public void obtenerTodas(OnCategoriasLoadedListener onCategoriasLoadedListener){
        //Segundo plano - Hilo Secundario - lo que no sea UI
        executorService.execute(()->{
            List<Mascota> mascotas = db.mascotaDao().obtenerTodos();
            //Si vamos aqui, ya tengo las mascotas
            //Requiero regresarlas a primer plano - Hilo principal - UI
            new Handler(Looper.getMainLooper()).post(()->{
                //MainLooper - Hilo principal - UI
                onCategoriasLoadedListener.onComplete(mascotas);
            });
        });
    }

    public void obtenerPorId(Long id, OnCategoriaLoadedListener onCategoriaLoadedListener){
        executorService.execute(()->{
            Mascota mascota = db.mascotaDao().obtenerPorId(id);
            new Handler(Looper.getMainLooper()).post(()->{
                onCategoriaLoadedListener.onComplete(mascota);
            });
        });
    }

    public void obtenerPorVeterinarioId(Long veterinarioId, OnCategoriasLoadedListener onCategoriasLoadedListener) {
        executorService.execute(() -> {
            List<Mascota> mascotas = db.mascotaDao().obtenerPorVeterinarioId(veterinarioId);
            new Handler(Looper.getMainLooper()).post(() -> {
                onCategoriasLoadedListener.onComplete(mascotas);
            });
        });
    }

    //Interface para obtenerTodas
    public interface OnCategoriaLoadedListener{
        void onComplete(Mascota mascota);
    }

    //Interface para obtenerUna

    public interface OnCategoriasLoadedListener{
        void onComplete(List<Mascota> mascotas);
    }
}
