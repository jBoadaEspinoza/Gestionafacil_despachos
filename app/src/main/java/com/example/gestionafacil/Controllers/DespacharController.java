package com.example.gestionafacil.Controllers;

import android.content.Context;

import com.example.gestionafacil.Models.SesionUsuario;
import com.example.gestionafacil.services.DespachoService;
import com.example.gestionafacil.services.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class DespacharController {

    private Context context;
    private SesionUsuario sessionUsuario; // Instancia de la clase SesionUsuario

    private DespachoService service;

    public DespacharController(Context context) {
        this.context = context;
        service = RetrofitClient.getClient().create(DespachoService.class);
        sessionUsuario = new SesionUsuario(context);
    }
    public void despacharPedido(JsonArray idsMozos, String token, DespacharCallback callback) {
        // Construir el objeto JSON de la solicitud
        JsonObject requestBody = new JsonObject();
        requestBody.add("ids_mozos", idsMozos); // Agregar la lista de IDs de mozos
        requestBody.addProperty("token", token); // Agregar el token

        Call<JsonObject> call = service.DespacharPedido(requestBody);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    // La solicitud fue exitosa
                    callback.onDespachoSuccess(response.body());
                } else {
                    // La solicitud no fue exitosa
                    callback.onDespachoFailure("Error en la solicitud: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // La solicitud falló
                callback.onDespachoFailure("Error en la solicitud: " + t.getMessage());
            }
        });
    }

    public interface DespacharCallback {
        void onDespachoSuccess(JsonObject responseBody);
        void onDespachoFailure(String errorMessage);
    }

}
