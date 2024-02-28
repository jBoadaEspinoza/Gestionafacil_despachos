package com.example.gestionafacil.Controllers;

import android.content.Context;

import com.example.gestionafacil.Models.Despacho;
import com.example.gestionafacil.Views.Fragments.DespachosFragment;
import com.example.gestionafacil.services.DespachoService;
import com.example.gestionafacil.services.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DespachosController {
    private DespachoService service;

    public DespachosController() {

        service = RetrofitClient.getClient().create(DespachoService.class);
    }

    public void obtenerDespachos(String operacion, String eId, String token, final DespachoCallback callback) {
        Call<JsonObject> call = service.getOrders(operacion, eId, token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject responseBody = response.body();
                    JsonObject responseObj = responseBody.getAsJsonObject("response");

                    if (responseObj.has("success") && responseObj.get("success").getAsBoolean()) {
                        List<Despacho> despachos = procesarDatos(responseBody);
                        callback.onDespachosLoaded(despachos);
                    } else {
                        callback.onDespachosLoadFailed("La solicitud no tuvo éxito. Código de estado HTTP: " + response.code());
                    }
                } else {
                    callback.onDespachosLoadFailed("La solicitud no fue exitosa. Código de estado HTTP: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onDespachosLoadFailed("Error al realizar la solicitud: " + t.getMessage());
            }
        });

    }
    private List<Despacho> procesarDatos(JsonObject responseBody) {
        List<Despacho> despachos = new ArrayList<>();
        JsonObject responseObj = responseBody.getAsJsonObject("response");

        if (responseObj.has("data")) {
            JsonArray dataArray = responseObj.getAsJsonArray("data");
            for (JsonElement element : dataArray) {
                JsonObject dataObject = element.getAsJsonObject();
                Despacho despacho = new Despacho();
                despacho.setId(dataObject.get("id").getAsString());
                despacho.setDenominacion(dataObject.get("denominacion").getAsString());
                despacho.setCategoria(dataObject.get("categoria").getAsString());
                despacho.setCantidad(dataObject.get("cantidad").getAsString());
                despacho.setAreaDeDespacho(dataObject.get("area_de_despacho").getAsString());
                despacho.setEstablecimiento(dataObject.get("establecimiento").getAsString());
                despachos.add(despacho);
            }
        }

        return despachos;
    }
    public interface DespachoCallback {
        void onDespachosLoaded(List<Despacho> despachos);
        void onDespachosLoadFailed(String errorMessage);
    }
}
