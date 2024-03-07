package com.example.gestionafacil.Models;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.gestionafacil.Views.Activities.LoginActivity;

public class SesionUsuario {
    private static final String PREF_NAME = "UserToken";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_ESTABLISHMENT_ID = "id_establecimiento";
    private static final String KEY_ESTABLISHMENT_NAME = "nombre_establecimiento"; // Nuevo clave para el nombre del establecimiento

    private SharedPreferences sharedPreferences;

    public SesionUsuario(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_TOKEN);
        editor.apply();
    }

    public void saveEstablishmentId(int establishmentId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ESTABLISHMENT_ID, establishmentId);
        editor.apply();
    }

    public int getEstablishmentId() {
        return sharedPreferences.getInt(KEY_ESTABLISHMENT_ID, -1); // -1 es un valor predeterminado si no se encuentra ningún ID almacenado
    }

    public void clearEstablishmentId() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_ESTABLISHMENT_ID);
        editor.apply();
    }

    // Método para guardar el nombre del establecimiento
    public void saveEstablishmentName(String establishmentName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ESTABLISHMENT_NAME, establishmentName);
        editor.apply();
    }

    // Método para obtener el nombre del establecimiento
    public String getEstablishmentName() {
        return sharedPreferences.getString(KEY_ESTABLISHMENT_NAME, null);
    }

    // Método para limpiar el nombre del establecimiento
    public void clearEstablishmentName() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_ESTABLISHMENT_NAME);
        editor.apply();
    }

    public void logout() {
        clearToken();
        clearEstablishmentId();
        clearEstablishmentName();
    }

    public void mostrarDialogoTokenExpirado(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Token Expirado")
                .setMessage("Su token de sesión ha expirado. Por favor, vuelva a iniciar sesión.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Crear un Intent para iniciar LoginActivity
                        Intent intent = new Intent(context, LoginActivity.class);
                        // Limpiar la pila de actividades y abrir LoginActivity
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                })
                .setCancelable(false) // Evitar que el usuario cierre el diálogo tocando fuera del área del diálogo
                .show();
    }

}
