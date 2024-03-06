package com.example.gestionafacil.Views.Fragments;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionafacil.Models.Mesa;
import com.example.gestionafacil.Models.Mozo;
import com.example.gestionafacil.R;

import java.util.List;

public class MesaViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewMesaDenominacion;
    private CheckBox checkBoxSeleccionada;
    private TextView textViewCantidadMesas;
    public ImageView btnDropdown;

    public MesaViewHolder(View itemView) {
        super(itemView);
        textViewMesaDenominacion = itemView.findViewById(R.id.textViewMesaDenominacion);
        checkBoxSeleccionada = itemView.findViewById(R.id.checkBoxSeleccionada);
        textViewCantidadMesas = itemView.findViewById(R.id.textViewCantidadMesas);
        btnDropdown = itemView.findViewById(R.id.btnDropdown);
        // Encuentra y asigna otras vistas aquí si es necesario
    }

    public void bindMesa(Mesa mesa) {
        // Aquí configura los datos de la mesa en las vistas correspondientes
        textViewMesaDenominacion.setText(mesa.getMesa_denominacion()); // Suponiendo que "getNombre" es el método para obtener el nombre de la mesa
        textViewCantidadMesas.setText(mesa.getCantidad()); // Suponiendo que "getNombre" es el método para obtener el nombre de la mesa

    }
    public void showMozos(boolean show) {
        if (show) {
            // Mostrar los mozos
            textViewCantidadMesas.setVisibility(View.VISIBLE);
        } else {
            // Ocultar los mozos
            textViewCantidadMesas.setVisibility(View.GONE);
        }
    }

}
