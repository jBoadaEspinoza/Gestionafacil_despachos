package com.example.gestionafacil.Views.Fragments;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionafacil.Models.Mozo;
import com.example.gestionafacil.R;
import androidx.annotation.NonNull;

public class MozoViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewNumOrder;
    public TextView textViewMozoNombre;
    public TextView textViewOrderTime;
    public CheckBox checkBoxSeleccionado;

    public MozoViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewNumOrder = itemView.findViewById(R.id.textViewNumOrder);
        textViewMozoNombre = itemView.findViewById(R.id.textViewMozoNombre);
        textViewOrderTime = itemView.findViewById(R.id.textViewOrderTime);
        checkBoxSeleccionado = itemView.findViewById(R.id.checkBoxSeleccionado);
    }

    public void bind(Mozo mozo, int position) {
        textViewNumOrder.setText("#" + (position + 1) + " ");
        String nombreCompleto = mozo.getMozo_nombre();
        String[] partesNombre = nombreCompleto.split(" ");
        String primerNombre = partesNombre[0];
        textViewMozoNombre.setText(primerNombre);
        textViewOrderTime.setText(mozo.tiempoTranscurrido());
        checkBoxSeleccionado.setChecked(mozo.isChecked()); // Establecer el estado del checkbox
    }

}

