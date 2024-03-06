package com.example.gestionafacil.Views.Fragments;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionafacil.Models.Mozo;
import com.example.gestionafacil.R;
import androidx.annotation.NonNull;

public class MozoViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewMozoNombre;
    public CheckBox checkBoxSeleccionado;

    public MozoViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewMozoNombre = itemView.findViewById(R.id.textViewMozoNombre);
        checkBoxSeleccionado = itemView.findViewById(R.id.checkBoxSeleccionado);
    }

    public void bindMozo(Mozo mozo, boolean show) {
        if (show) {
            textViewMozoNombre.setText(mozo.getMozo_nombre()); // Asignar el nombre del mozo al TextView
            textViewMozoNombre.setVisibility(View.VISIBLE);
            checkBoxSeleccionado.setVisibility(View.VISIBLE);
        } else {
            textViewMozoNombre.setVisibility(View.GONE);
            checkBoxSeleccionado.setVisibility(View.GONE);
        }
    }
}

