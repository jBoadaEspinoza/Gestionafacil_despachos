package com.example.gestionafacil.Controllers;
import android.content.Context;
import android.util.Log;

import com.example.gestionafacil.Models.AreaDespacho;
import com.example.gestionafacil.Models.Despacho;
import com.example.gestionafacil.Models.SesionUsuario;
import com.example.gestionafacil.services.AreaDespachoService;
import com.example.gestionafacil.services.RetrofitClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AreasDespachoController {

    private SesionUsuario sessionUsuario; // Instancia de la clase SesionUsuario


    private Context context;

    public AreasDespachoController(Context context) {

        this.context = context;
        sessionUsuario = new SesionUsuario(context);

    }
    public void obtenerAreasDespacho(String token, final AreaDespachoCallback callback) {
        AreaDespachoService service = RetrofitClient.getClient().create(AreaDespachoService.class);
        Call<JsonObject> call = service.getAreas(token);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject responseBody = response.body();
                    JsonObject responseObj = responseBody.getAsJsonObject("response");


                    if (responseObj.has("success") && responseObj.get("success").getAsBoolean()) {

                        List<AreaDespacho> despachos = procesarDatos(responseBody);
                        callback.onAreasDespachoLoaded(despachos);
                    } else {
                        if (responseObj.has("msg") && responseObj.get("msg").getAsString().equals("Token expirado")) {
                            // Borrar el token de la sesión si el mensaje indica que el token ha expirado
                            sessionUsuario.logout();
                            // Mostrar el diálogo de token expirado
                            sessionUsuario.mostrarDialogoTokenExpirado(context);
                        }
                        callback.onAreasDespachoLoadFailed("La solicitud no tuvo éxito. Código de estado HTTP: " + response.code());
                    }

                } else {
                    callback.onAreasDespachoLoadFailed("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("AreaDespachoController", "Error al obtener las áreas de despacho", t);
                callback.onAreasDespachoLoadFailed("Error al obtener las áreas de despacho: " + t.getMessage());
            }
        });
    }

    private List<AreaDespacho> procesarDatos(JsonObject responseBody) {
        List<AreaDespacho> areadespachos = new ArrayList<>();
        JsonObject responseObj = responseBody.getAsJsonObject("response");

        if (responseObj.has("data")) {
            JsonArray dataArray = responseObj.getAsJsonArray("data");
            for (JsonElement element : dataArray) {
                JsonObject dataObject = element.getAsJsonObject();
                AreaDespacho despacho = new AreaDespacho();
                despacho.setId(dataObject.get("id").getAsString());
                despacho.setDenominacionSingular(dataObject.get("denominacion_singular_es").getAsString());
                despacho.setDenominacionPlural(dataObject.get("denominacion_plural_es").getAsString());

                areadespachos.add(despacho);
            }
        }

        return areadespachos;
    }

    public interface AreaDespachoCallback {
        void onAreasDespachoLoaded(List<AreaDespacho> despachos);

        void onAreasDespachoLoadFailed(String errorMessage);
    }
}
