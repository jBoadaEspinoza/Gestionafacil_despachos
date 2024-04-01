package com.example.gestionafacil.Views.Fragments.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionafacil.Models.Mozo;
import com.example.gestionafacil.R;
import com.example.gestionafacil.Views.Fragments.Holders.MozoViewHolder;

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


    @NonNull
    @Override
    public MozoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mozo, parent, false);
        return new MozoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MozoViewHolder holder, int position) {
        Mozo mozo = mozoList.get(position);
        holder.bind(mozo, position);
        holder.checkBoxSeleccionado.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!mozosSeleccionados.contains(mozo)) {
                    mozosSeleccionados.add(mozo); // Agregar el mozo a la lista de mozos seleccionados si no está presente
                    mozo.setChecked(isChecked);
                    listener.onMozoCheckedChanged(mozo);
                    listener.onMozosListChanged(mozo);
                }
            } else {
                mozo.setChecked(false);
                mozosSeleccionados.remove(mozo); // Quitar el mozo de la lista de mozos seleccionados
                // Notificar deselección
                listener.onMozoCheckedChanged(mozo);
                // Notificar para borrar el mozo de la otra lista
                listener.onMozoRemoved(mozo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mozoList.size();
    }

    public interface OnMozoCheckedChangeListener {
        void onMozoCheckedChanged(Mozo mozo);
        void onMozosListChanged(Mozo mozo);
        void onMozoRemoved(Mozo mozo);

    }
}
