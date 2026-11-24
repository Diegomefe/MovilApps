package com.upiiz.exameniii.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteRetrofit {
    private static final String BASE_URL = "https://dummyjson.com/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static ApiProductos obtenerApiProductos() {
        return retrofit.create(ApiProductos.class);
    }
}
