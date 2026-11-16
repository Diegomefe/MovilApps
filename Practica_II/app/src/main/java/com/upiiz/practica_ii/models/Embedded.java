package com.upiiz.practica_ii.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Embedded {

    @SerializedName("libroDtoList")
    private List<Libro> libros;

    public List<Libro> getLibros() { return libros; }
}