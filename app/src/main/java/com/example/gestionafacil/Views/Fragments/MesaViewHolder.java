package com.example.gestionafacil.Views.Fragments;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionafacil.Models.GrupoMesa;
import com.example.gestionafacil.Models.Mesa;
import com.example.gestionafacil.Models.Mozo;
import com.example.gestionafacil.R;

import java.util.List;

public class MesaViewHolder extends RecyclerView.ViewHolder {

    TextView textViewMesaDenominacion;
    public CheckBox checkBoxSeleccionada;
    public TextView textViewCantidadMesas;
    public ImageView btnDropdown;
    RecyclerView recyclerViewMozos;

    public MesaViewHolder(View itemView) {
        super(itemView);
        textViewMesaDenominacion = itemView.findViewById(R.id.textViewMesaDenominacion);
        checkBoxSeleccionada = itemView.findViewById(R.id.checkBoxSeleccionada);
        textViewCantidadMesas = itemView.findViewById(R.id.textViewCantidadMesas);
        btnDropdown = itemView.findViewById(R.id.btnDropdown);

        recyclerViewMozos = itemView.findViewById(R.id.recyclerViewMozos);



    }

    public void bind(Mesa mesa) {
        textViewMesaDenominacion.setText(mesa.getMesa_denominacion());
        textViewCantidadMesas.setText(mesa.getCantidad());
    }

    void showMozos(boolean show) {
        if (show) {
            recyclerViewMozos.setVisibility(View.VISIBLE);
        } else {
            recyclerViewMozos.setVisibility(View.GONE);
        }
    }

    public void setMesaSelected(boolean isSelected) {
        checkBoxSeleccionada.setChecked(isSelected);
    }


}
