package com.example.gestionafacil.Controllers;

import com.example.gestionafacil.Models.Mozo;
import com.example.gestionafacil.services.DespachoService;
import com.example.gestionafacil.services.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MozosController {

    private DespachoService service;

    public MozosController() {

        service = RetrofitClient.getClient().create(DespachoService.class);
    }

    public void obtenerMozos(String operacion, String eId, String mId, String token, final MozosCallback callback) {
        Call<JsonObject> call = service.getDestalle(operacion, eId, mId, token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject responseBody = response.body();
                    JsonObject responseObj = responseBody.getAsJsonObject("response");

                    if (responseObj.has("success") && responseObj.get("success").getAsBoolean()) {
                        List<Mozo> mozos = procesarDatos(responseBody);
                        callback.onMozosLoaded(mozos);
                    } else {
                        callback.onMozosLoadFailed("La solicitud no tuvo éxito. Código de estado HTTP: " + response.code());
                    }
                } else {
                    callback.onMozosLoadFailed("La solicitud no fue exitosa. Código de estado HTTP: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onMozosLoadFailed("Error al realizar la solicitud: " + t.getMessage());
            }
        });
    }

    private List<Mozo> procesarDatos(JsonObject responseBody) {
        List<Mozo> mozos = new ArrayList<>();
        JsonObject responseObj = responseBody.getAsJsonObject("response");

        if (responseObj.has("data")) {
            JsonArray dataArray = responseObj.getAsJsonArray("data");
            Gson gson = new Gson(); // Crear un objeto Gson

            for (JsonElement element : dataArray) {
                JsonObject dataObject = element.getAsJsonObject();
                Mozo mozo = gson.fromJson(dataObject, Mozo.class); // Convertir directamente a objeto Mozo
                mozos.add(mozo);
            }
        }

        return mozos;
    }

    public interface MozosCallback {
        void onMozosLoaded(List<Mozo> mozos);
        void onMozosLoadFailed(String errorMessage);
    }
}
