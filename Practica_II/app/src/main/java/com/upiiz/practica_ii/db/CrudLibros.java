package com.upiiz.practica_ii.db;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import com.upiiz.practica_ii.models.Libro;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrudLibros {
    // Maneja el CRUD de favoritos
    // Instancia la db (una sola para toda la app)
    // Usa hilos para realizar las operaciones de entrada/salida

    private static AppDatabase db;
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public CrudLibros(Context context) {
        // Se crea una sola vez; Room recomienda una única instancia por app
        synchronized (CrudLibros.class) {
            if (db == null) {
                db = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "explorador_libros.db").build();
            }
        }
    }

    public void insertar(LibroEntity libro, Runnable onFinished) {
        executorService.execute(() -> {
            db.libroDao().crear(libro);
            if (onFinished != null) mainHandler.post(onFinished);
        });
    }

    public void actualizar(LibroEntity libro, Runnable onFinished) {
        executorService.execute(() -> {
            db.libroDao().actualizar(libro);
            if (onFinished != null) mainHandler.post(onFinished);
        });
    }

    public void eliminar(LibroEntity libro, Runnable onFinished) {
        executorService.execute(() -> {
            db.libroDao().eliminar(libro);
            if (onFinished != null) mainHandler.post(onFinished);
        });
    }

    public void eliminarPorId(int id, Runnable onFinished) {
        executorService.execute(() -> {
            db.libroDao().eliminarPorId(id);
            if (onFinished != null) mainHandler.post(onFinished);
        });
    }

    public void obtenerTodos(OnLibrosLoadedListener listener) {
        executorService.execute(() -> {
            List<LibroEntity> libros = db.libroDao().obtenerTodos();
            mainHandler.post(() -> listener.onComplete(libros));
        });
    }

    public void obtenerIds(OnIdsLoadedListener listener) {
        executorService.execute(() -> {
            List<Integer> ids = db.libroDao().obtenerIds();
            mainHandler.post(() -> listener.onComplete(ids));
        });
    }

    public void obtenerPorId(int id, OnLibroLoadedListener listener) {
        executorService.execute(() -> {
            LibroEntity libro = db.libroDao().obtenerPorId(id);
            mainHandler.post(() -> listener.onComplete(libro));
        });
    }

    // Actualiza en Room los favoritos que ya existían con los datos frescos del servidor
    // y devuelve los ids de favoritos
    public void sincronizarConCatalogo(List<Libro> catalogo, OnIdsLoadedListener listener) {
        executorService.execute(() -> {
            List<Integer> ids = db.libroDao().obtenerIds();
            for (Libro l : catalogo) {
                if (ids.contains(l.getId())) {
                    db.libroDao().actualizar(LibroEntity.fromLibro(l));
                }
            }
            mainHandler.post(() -> listener.onComplete(ids));
        });
    }

    // Interface para obtener uno
    public interface OnLibroLoadedListener {
        void onComplete(LibroEntity libro);
    }

    // Interface para obtener varios
    public interface OnLibrosLoadedListener {
        void onComplete(List<LibroEntity> libros);
    }

    // Interface para obtener los ids de favoritos
    public interface OnIdsLoadedListener {
        void onComplete(List<Integer> ids);
    }
}