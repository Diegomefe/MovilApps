package com.upiiz.dm_jdmf_07.data.entities;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "mascotas",
        foreignKeys = @ForeignKey(
                entity = Veterinario.class,
                parentColumns = "id",
                childColumns = "veterinario_id",
                onDelete = ForeignKey.CASCADE
        ))
public class Mascota {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "nombre")
    private String nombre;
    private String raza;
    private int edad;
    @ColumnInfo(name = "veterinario_id")
    private Long veterinarioId;

    public Mascota(Long id,String nombre, String raza, int edad, Long veterinarioId) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.veterinarioId = veterinarioId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Long getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(Long veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    @Override
    public String toString() {
        return nombre;
    }

}




