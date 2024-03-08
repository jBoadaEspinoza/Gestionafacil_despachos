package com.example.gestionafacil.Controllers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.gestionafacil.Models.Despacho;
import com.example.gestionafacil.Models.SesionUsuario;
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

public class DespachosController {
    private DespachoService service;
    private SesionUsuario sessionUsuario; // Instancia de la clase SesionUsuario
    private Context context;

    public DespachosController(Context context) {
        this.context = context;
        service = RetrofitClient.getClient().create(DespachoService.class);
        sessionUsuario = new SesionUsuario(context);

    }

    public void obtenerDespachos(String operacion, String eId, String token, final DespachoCallback callback) {
        Call<JsonObject> call = service.getDespachos(operacion, eId, token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject responseBody = response.body();
                    JsonObject responseObj = responseBody.getAsJsonObject("response");

                    if (responseObj.has("success") && responseObj.get("success").getAsBoolean()) {
                        // Obtener el nuevo token del JSON de respuesta, si está disponible
                        String nuevoToken = responseObj.has("token_actualizado") ? responseObj.get("token_actualizado").getAsString() : null;

                        // Guardar el nuevo token en la sesión
                        if (nuevoToken != null) {
                            sessionUsuario.saveToken(nuevoToken);
                        }

                        List<Despacho> despachos = procesarDatos(responseBody);

                        callback.onDespachosLoaded(despachos);
                    } else {
                        if (responseObj.has("msg") && responseObj.get("msg").getAsString().equals("Token expirado")) {
                            // Borrar el token de la sesión si el mensaje indica que el token ha expirado
                            sessionUsuario.logout();
                            // Mostrar el diálogo de token expirado
                            sessionUsuario.mostrarDialogoTokenExpirado(context);
                        }
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
            Gson gson = new Gson(); // Crear un objeto Gson

            for (JsonElement element : dataArray) {
                JsonObject dataObject = element.getAsJsonObject();
                // Convertir directamente el JsonObject en un objeto Despacho
                Despacho despacho = gson.fromJson(dataObject, Despacho.class);

                // Agregar el objeto Despacho a la lista
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
