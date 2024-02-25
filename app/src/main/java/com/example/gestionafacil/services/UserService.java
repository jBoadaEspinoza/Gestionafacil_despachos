package com.example.gestionafacil.services;
import com.example.gestionafacil.Models.LoginData;
import com.example.gestionafacil.Models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface UserService {
    @POST("control-de-despachos/login")
    Call<LoginResponse> login(@Body LoginData loginData);
}
