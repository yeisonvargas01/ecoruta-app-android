package com.ecoruta.app.red;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ClienteApi {

    private static final String URL_BASE =
            "http://10.0.2.2:8081/api/v1/";

    private ClienteApi() {

    }

    public static EcoRutaApi obtenerInstancia() {
        return ContenedorInstancia.SERVICIO;
    }

    private static EcoRutaApi crearServicio() {

        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(URL_BASE)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        )
                        .build();

        return retrofit.create(
                EcoRutaApi.class
        );
    }

    private static final class ContenedorInstancia {

        private static final EcoRutaApi SERVICIO =
                crearServicio();

        private ContenedorInstancia() {

        }
    }
}