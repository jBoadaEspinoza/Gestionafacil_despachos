package com.example.gestionafacil.Controllers;

import android.content.Context;

import com.example.gestionafacil.Models.Despacho;
import com.example.gestionafacil.Models.Mesa;
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

public class MesasController {
    private DespachoService service;
    private Context context;
    private SesionUsuario sessionUsuario; // Instancia de la clase SesionUsuario


    public MesasController(Context context) {
        this.context = context;
        service = RetrofitClient.getClient().create(DespachoService.class);
        sessionUsuario = new SesionUsuario(context);

    }

    public void obtenerMesas(String operacion, String aId, String token, final MesasController.MesasCallback callback) {
        Call<JsonObject> call = service.getMesas_por_articulo(operacion, aId, token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject responseBody = response.body();
                    JsonObject responseObj = responseBody.getAsJsonObject("response");

                    if (responseObj.has("success") && responseObj.get("success").getAsBoolean()) {
                        String nuevoToken = responseObj.has("token_actualizado") ? responseObj.get("token_actualizado").getAsString() : null;

                        // Guardar el nuevo token en la sesión
                        if (nuevoToken != null) {
                            sessionUsuario.saveToken(nuevoToken);
                        }

                        List<Mesa> mesas = procesarDatos(responseBody);
                        callback.onMesasLoaded(mesas);
                    } else {
                        if (responseObj.has("message") && responseObj.get("message").getAsString().equals("Token expirado")) {
                            // Borrar el token de la sesión si el mensaje indica que el token ha expirado
                            sessionUsuario.logout();
                            // Mostrar el diálogo de token expirado
                            sessionUsuario.mostrarDialogoTokenExpirado(context);
                        }
                        callback.onMesassLoadFailed("La solicitud no tuvo éxito. Código de estado HTTP: " + response.code());
                    }
                } else {
                    callback.onMesassLoadFailed("La solicitud no fue exitosa. Código de estado HTTP: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onMesassLoadFailed("Error al realizar la solicitud: " + t.getMessage());
            }
        });

    }

    private List<Mesa> procesarDatos(JsonObject responseBody) {
        List<Mesa> mesas = new ArrayList<>();
        JsonObject responseObj = responseBody.getAsJsonObject("response");

        if (responseObj.has("data")) {
            JsonArray dataArray = responseObj.getAsJsonArray("data");
            Gson gson = new Gson(); // Crear un objeto Gson

            for (JsonElement element : dataArray) {
                JsonObject dataObject = element.getAsJsonObject();
                Mesa mesa = gson.fromJson(dataObject, Mesa.class); // Convertir directamente a objeto Mesa
                mesas.add(mesa);
            }
        }

        return mesas;
    }
    public interface MesasCallback {
        void onMesasLoaded(List<Mesa> mesas);
        void onMesassLoadFailed(String errorMessage);
    }
}
