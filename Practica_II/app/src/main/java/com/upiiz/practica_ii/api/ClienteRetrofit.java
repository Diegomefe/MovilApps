package com.upiiz.practica_ii.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteRetrofit {

    private static final String BASE_URL = "https://ejercicio-08-documentacion-y-endpoints.onrender.com/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static ApiLibros obtenerApiLibros() {
        return retrofit.create(ApiLibros.class);
    }
}
