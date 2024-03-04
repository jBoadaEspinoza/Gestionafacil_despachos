package com.example.gestionafacil.Models;
import com.google.gson.annotations.SerializedName;

public class Mesa {
    @SerializedName("mesa_id")
    String mesa_id;
    @SerializedName("mesa_denominacion")
    String mesa_denominacion;
    @SerializedName("cantidad")
    String cantidad;

    public String getMesa_id() {
        return mesa_id;
    }

    public void setMesa_id(String mesa_id) {
        this.mesa_id = mesa_id;
    }

    public String getMesa_denominacion() {
        return mesa_denominacion;
    }

    public void setMesa_denominacion(String mesa_denominacion) {
        this.mesa_denominacion = mesa_denominacion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
