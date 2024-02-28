package com.example.gestionafacil.Models;
import android.content.Context;
import android.content.SharedPreferences;
public class SesionUsuario {
    private static final String PREF_NAME = "UserToken";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_ESTABLISHMENT_ID = "id_establecimiento";
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


}
