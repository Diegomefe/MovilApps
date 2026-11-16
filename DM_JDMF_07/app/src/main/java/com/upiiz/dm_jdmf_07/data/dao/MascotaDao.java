package com.upiiz.dm_jdmf_07.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.upiiz.dm_jdmf_07.data.entities.Mascota;

import java.util.List;

@Dao
public interface MascotaDao {
    //Query = consulta
    @Query("SELECT * FROM mascotas")
    List<Mascota> obtenerTodos();
    @Query("SELECT * FROM mascotas WHERE id=:id")
    Mascota obtenerPorId(Long id);

    @Query("SELECT * FROM mascotas WHERE veterinario_id=:veterinarioId")
    List<Mascota> obtenerPorVeterinarioId(Long veterinarioId);
    @Delete
    void eliminar(Mascota mascota);
    // Si se requiere conocer el ID generado - Long
    // De lo contrario - void
    @Insert
    void crear(Mascota mascota);
    @Update
    void actualizar (Mascota mascota);
}
