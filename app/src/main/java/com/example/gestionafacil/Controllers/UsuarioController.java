package com.example.gestionafacil.Controllers;
import com.example.gestionafacil.Models.LoginData;
import com.example.gestionafacil.Models.LoginResponse;

import com.example.gestionafacil.services.RetrofitClient;
import com.example.gestionafacil.services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioController {
    private UserService userService;

    public UsuarioController() {
        this.userService = RetrofitClient.getClient().create(UserService.class);

    }

    public void login(String ruc, String usuario, String clave, final OnLoginListener listener) {
        LoginData loginData = new LoginData(ruc, usuario, clave);
        Call<LoginResponse> call = userService.login(loginData);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.getResponse() != null) {
                        boolean success = loginResponse.getResponse().isSuccess();
                        if (success) {
                            listener.onSuccess();
                        } else {
                            String errorMessage = loginResponse.getResponse().getMessage();
                            listener.onError(errorMessage);
                        }
                    } else {
                        listener.onError("Error en la respuesta");
                    }
                } else {
                    listener.onError("Error en la solicitud");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                listener.onError("Error en la conexión");
            }
        });
    }
    public interface OnLoginListener {
        void onSuccess();

        void onError(String errorMessage);
    }
}