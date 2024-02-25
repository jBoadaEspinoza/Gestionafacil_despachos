package com.example.gestionafacil.Models;

public class LoginData {
    private String ruc;
    private String usuario;
    private String clave;

    public LoginData(String ruc, String usuario, String clave) {
        this.ruc = ruc;
        this.usuario = usuario;
        this.clave = clave;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
