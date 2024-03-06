package com.example.gestionafacil.Models;


import java.util.List;

public class GrupoMesa{
    private Mesa mesa;
    private List<Mozo> mozos;

    public GrupoMesa(Mesa mesa, List<Mozo> mozos) {
        this.mesa = mesa;
        this.mozos = mozos;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public List<Mozo> getMozos() {
        return mozos;
    }
}
