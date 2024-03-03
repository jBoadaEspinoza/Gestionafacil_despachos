package com.example.gestionafacil.services;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AreaDespachoService {
    @GET("areas-de-despacho/")
    Call<JsonObject> getAreas(
            @Query("token") String token
    );
}
