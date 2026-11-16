package com.upiiz.practica_ii.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LibroDao {

    @Insert
    void crear(LibroEntity libro);

    @Query("SELECT * FROM libros")
    List<LibroEntity> obtenerTodos();

    @Query("SELECT * FROM libros WHERE id = :id")
    LibroEntity obtenerPorId(int id);
    @Query("SELECT id FROM libros")
    List<Integer> obtenerIds();
    @Update
    void actualizar(LibroEntity libro);

    @Delete
    void eliminar(LibroEntity libro);

    @Query("DELETE FROM libros WHERE id = :id")
    void eliminarPorId(int id);
}