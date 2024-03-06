package com.example.gestionafacil.Models;
import com.google.gson.annotations.SerializedName;

public class Mozo {
    @SerializedName("id")
    String id;
    @SerializedName("mozo_nombre")
    String mozo_nombre;
    @SerializedName("mozo_apellidos")
    String mozo_apellidos;
    @SerializedName("ordenado")
    String ordenado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMozo_nombre() {
        return mozo_nombre;
    }

    public void setMozo_nombre(String mozo_nombre) {
        this.mozo_nombre = mozo_nombre;
    }

    public String getMozo_apellido() {
        return mozo_apellidos;
    }

    public void setMozo_apellido(String mozo_apellido) {
        this.mozo_apellidos = mozo_apellido;
    }

    public String getOrdenado() {
        return ordenado;
    }

    public void setOrdenado(String ordenado) {
        this.ordenado = ordenado;
    }
}
