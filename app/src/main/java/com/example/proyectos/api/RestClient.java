package com.example.proyectos.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton para creación de cliente
 */

public final class RestClient {

    private static ConeccionApi con = null;

    public static ConeccionApi getClient() {
        if (con == null) {

            final Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(ConeccionApi.BASE_URL)
                    .build();

            con = retrofit.create(ConeccionApi.class);
        }
        return con;
    }
}
