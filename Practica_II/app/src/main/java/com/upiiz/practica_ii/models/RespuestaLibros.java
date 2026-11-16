package com.upiiz.practica_ii.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RespuestaLibros {

    @SerializedName("_embedded")
    private Embedded embedded;

    public List<Libro> getDatos() {
        // Si no hay libros, HAL omite "_embedded": se devuelve lista vacía en vez de null
        if (embedded == null || embedded.getLibros() == null) {
            return new ArrayList<>();
        }
        return embedded.getLibros();
    }
}