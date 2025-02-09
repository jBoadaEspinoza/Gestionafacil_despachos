package com.example.gestionafacil.Models;


import java.util.List;

public class  GrupoMesa{
    private Mesa mesa;
    private List<Mozo> mozos;

    private boolean isExpandable;

    private boolean isSelected;

    public GrupoMesa(Mesa mesa, List<Mozo> mozos, boolean isExpandable) {
        this.mesa = mesa;
        this.mozos = mozos;
        this.isExpandable = isExpandable;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public Mesa getMesa() {
        return mesa;
    }

    public List<Mozo> getMozos() {
        return mozos;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }
}
