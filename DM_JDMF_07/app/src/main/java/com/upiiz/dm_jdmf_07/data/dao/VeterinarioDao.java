package com.upiiz.dm_jdmf_07.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.upiiz.dm_jdmf_07.data.entities.Veterinario;

import java.util.List;

@Dao
public interface VeterinarioDao {
    @Query("SELECT * FROM veterinarios")
    List<Veterinario> ObtenerTodos();
    @Query("SELECT * FROM veterinarios WHERE id=:id")
    Veterinario obtenerPorId(Long id);

    @Query("SELECT * FROM veterinarios WHERE correo=:correo")
    Veterinario obtenerPorCorreo(String correo);
    @Insert
    void crear(Veterinario veterinario);
    @Update
    void actualizar(Veterinario veterinario);
    @Delete
    void eliminar(Veterinario veterinario);
}
