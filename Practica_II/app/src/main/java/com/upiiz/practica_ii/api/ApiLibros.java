package com.upiiz.practica_ii.api;

import com.upiiz.practica_ii.models.Libro;
import com.upiiz.practica_ii.models.RespuestaLibros;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiLibros {

    //Solicita todos los libros
    @GET("/api/v1/libros")
    Call<RespuestaLibros> obtenerLibros();

    //Solicita un libro
    @GET("/api/v1/libros/{id}")
    Call<Libro> obtenerLibroPorId(@Path("id") int id);

}
