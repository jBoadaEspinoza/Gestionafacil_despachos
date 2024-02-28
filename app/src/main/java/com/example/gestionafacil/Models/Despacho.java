package com.example.gestionafacil.Models;
import com.google.gson.annotations.SerializedName;

public class Despacho {
    @SerializedName("id")
    private String id;
    @SerializedName("denominacion")
    private String denominacion;
    @SerializedName("categoria")
    private String categoria;
    @SerializedName("cantidad")
    private String cantidad;
    @SerializedName("area_de_despacho")
    private String areaDeDespacho;
    @SerializedName("establecimiento")
    private String establecimiento;

    public String getId() {
        return id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getAreaDeDespacho() {
        return areaDeDespacho;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setAreaDeDespacho(String areaDeDespacho) {
        this.areaDeDespacho = areaDeDespacho;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }
}
