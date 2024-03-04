package com.example.gestionafacil.services;

import retrofit2.http.GET;
import retrofit2.http.Query;
import com.google.gson.JsonObject;
import retrofit2.Call;

public interface DespachoService {
    @GET("control-de-despachos/")
    Call<JsonObject> getDespachos(
            @Query("operacion") String operacion,
            @Query("e_id") String e_id,
            @Query("token") String token
    );

    @GET("control-de-despachos/")
    Call<JsonObject> getMesas_por_articulo(
            @Query("operacion") String operacion,
            @Query("a_id") String a_id,
            @Query("token") String token
    );

    @GET("control-de-despachos/")
    Call<JsonObject> getDestalle(
            @Query("operacion") String operacion,
            @Query("a_id") String e_id,
            @Query("m_id") String m_id,
            @Query("token") String token
    );
}
