package com.example.gestionafacil.Models;
import com.google.gson.annotations.SerializedName;

public class Mozo {
    @SerializedName("id")
    String id;
    @SerializedName("mozo_nombre")
    String mozo_nombre;
    @SerializedName("mozo_apellid")
    String mozo_apellido;
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
        return mozo_apellido;
    }

    public void setMozo_apellido(String mozo_apellido) {
        this.mozo_apellido = mozo_apellido;
    }

    public String getOrdenado() {
        return ordenado;
    }

    public void setOrdenado(String ordenado) {
        this.ordenado = ordenado;
    }
}
