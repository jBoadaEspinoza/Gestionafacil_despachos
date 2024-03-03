package com.example.gestionafacil.Models;
import com.google.gson.annotations.SerializedName;

public class AreaDespacho {
    @SerializedName("id")
    private String id;

    @SerializedName("denominacion_singular_es")
    private String denominacionSingular;

    @SerializedName("denominacion_plural_es")
    private String denominacionPlural;

    @SerializedName("activo")
    private boolean activo;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDenominacionSingular() {
        return denominacionSingular;
    }

    public void setDenominacionSingular(String denominacionSingular) {
        this.denominacionSingular = denominacionSingular;
    }

    public String getDenominacionPlural() {
        return denominacionPlural;
    }

    public void setDenominacionPlural(String denominacionPlural) {
        this.denominacionPlural = denominacionPlural;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
