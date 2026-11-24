package com.upiiz.exameniii.api;

import com.upiiz.exameniii.models.RespuestaProductos;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiProductos {
    @GET("/products")
    Call<RespuestaProductos> obtenerProductos();
}
