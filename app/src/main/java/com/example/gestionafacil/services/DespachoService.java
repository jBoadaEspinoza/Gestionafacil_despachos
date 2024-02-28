package com.example.gestionafacil.services;

import retrofit2.http.GET;
import retrofit2.http.Query;
import com.google.gson.JsonObject;
import retrofit2.Call;

public interface DespachoService {
    @GET("control-de-despachos/")
    Call<JsonObject> getOrders(
            @Query("operacion") String operacion,
            @Query("e_id") String e_id,
            @Query("token") String token
    );

}
