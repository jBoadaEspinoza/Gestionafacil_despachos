package com.example.gestionafacil.Views.Fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionafacil.Models.Mozo;
import com.example.gestionafacil.R;

import java.util.ArrayList;
import java.util.List;

public class MozosAdapter extends RecyclerView.Adapter<MozoViewHolder>{

    private List<Mozo> mozoList;
    private List<Mozo> mozosSeleccionados;
    private OnMozoCheckedChangeListener listener;

    public MozosAdapter(List<Mozo> mozoList, OnMozoCheckedChangeListener listener) {
        this.mozoList = mozoList;
        this.mozosSeleccionados = new ArrayList<>();
        this.listener = listener;
    }

    // Método para imprimir la lista completa de mozos seleccionados
    public void imprimirMozosSeleccionados() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Mozos seleccionados:");
        for (Mozo mozo : mozosSeleccionados) {
            stringBuilder.append("\nID: ").append(mozo.getId()).append(", Nombre: ").append(mozo.getMozo_nombre());
        }
        Log.d("MozosAdapter", stringBuilder.toString());
    }

    @NonNull
    @Override
    public MozoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mozo, parent, false);
        return new MozoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MozoViewHolder holder, int position) {
        Mozo mozo = mozoList.get(position);
        holder.textViewNumOrder.setText("#" + (position + 1) + " ");
        String nombreCompleto = mozo.getMozo_nombre();
        String[] partesNombre = nombreCompleto.split(" ");
        String primerNombre = partesNombre[0];
        holder.textViewMozoNombre.setText(primerNombre);
        holder.textViewOrderTime.setText(mozo.tiempoTranscurrido());

        // Manejar clics en el checkbox
        holder.checkBoxSeleccionado.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!mozosSeleccionados.contains(mozo)) {
                    mozosSeleccionados.add(mozo); // Agregar el mozo a la lista de mozos seleccionados si no está presente
                }
            } else {
                mozosSeleccionados.remove(mozo); // Quitar el mozo de la lista de mozos seleccionados
            }
            // Llamar al método en la interfaz del listener para notificar cambios en los mozos seleccionados
            listener.onMozoCheckedChanged(mozosSeleccionados.size());
        });
    }

    @Override
    public int getItemCount() {
        return mozoList.size();
    }

    // Interfaz para manejar cambios en los mozos seleccionados
    public interface OnMozoCheckedChangeListener {
        void onMozoCheckedChanged(int selectedCount);
    }

    // Método para obtener la lista de mozos seleccionados
    public List<Mozo> getMozosSeleccionados() {
        return mozosSeleccionados;
    }
}
