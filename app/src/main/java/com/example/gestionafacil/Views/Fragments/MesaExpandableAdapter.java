package com.example.gestionafacil.Views.Fragments;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionafacil.Models.GrupoMesa;

import com.example.gestionafacil.Models.Mozo;
import com.example.gestionafacil.R;
import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.List;

public class MesaExpandableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_MESA = 0;
    private static final int VIEW_TYPE_MOZO = 1;
    private List<GrupoMesa> grupos;
    private SparseBooleanArray expandedGroups = new SparseBooleanArray();
    private List<MozoViewHolder> mozoViewHolders = new ArrayList<>();
    public MesaExpandableAdapter(List<GrupoMesa> grupos) {
        this.grupos = grupos;
    }

    public void setGrupos(List<GrupoMesa> grupos) {
        this.grupos = grupos;
        notifyDataSetChanged();
    }

    public void addMozosToMesa(String mesaId, List<Mozo> mozos) {
        // Busca la mesa correspondiente y agrega los mozos
        for (GrupoMesa grupo : grupos) {
            if (grupo.getMesa().getMesa_id().equals(mesaId)) {
                grupo.getMozos().addAll(mozos);
                notifyDataSetChanged();
                return;
            }
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_MESA:
                View mesaView = inflater.inflate(R.layout.item_mesa, parent, false);
                return new MesaViewHolder(mesaView);
            case VIEW_TYPE_MOZO:
                View mozoView = inflater.inflate(R.layout.item_mozo, parent, false);
                MozoViewHolder mozoViewHolder = new MozoViewHolder(mozoView);
                mozoViewHolders.add(mozoViewHolder);
                return mozoViewHolder;
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int currentPos = 0;
        for (GrupoMesa grupo : grupos) {
            if (position == currentPos) {
                // Este es un elemento de la mesa
                MesaViewHolder mesaHolder = (MesaViewHolder) holder;

                // Configurar OnClickListener para el botón btndown
                mesaHolder.btnDropdown.setOnClickListener(v -> {
                    int groupPosition = holder.getAdapterPosition();
                    toggleGroup(groupPosition);
                    showMozos(expandedGroups.get(groupPosition, false));
                });

                return;
            }
            currentPos++;

            // Verificar si la posición corresponde a un mozo en este grupo
            if (position < currentPos + grupo.getMozos().size()) {
                MozoViewHolder mozoHolder = (MozoViewHolder) holder;
                Mozo mozo = grupo.getMozos().get(position - currentPos); // Ajuste de posición relativa
                return;
            }

            // Incrementar la posición para el próximo grupo de mozos
            currentPos += grupo.getMozos().size();
        }
        throw new IllegalArgumentException("Invalid position");
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        for (GrupoMesa grupo : grupos) {
            itemCount += grupo.getMozos().size() + 1; // Adding 1 for the mesa item
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        int count = 0;
        for (GrupoMesa grupo : grupos) {
            if (position == count) {
                return VIEW_TYPE_MESA;
            }
            count++;
            if (position < count + grupo.getMozos().size()) {
                return VIEW_TYPE_MOZO;
            }
            count += grupo.getMozos().size();
        }
        throw new IllegalArgumentException("Invalid position");
    }

    private void toggleGroup(int groupPosition) {
        boolean isExpanded = expandedGroups.get(groupPosition);
        expandedGroups.put(groupPosition, !isExpanded);
        notifyItemChanged(groupPosition);
    }

    public void showMozos(boolean show) {
        for (MozoViewHolder mozoHolder : mozoViewHolders) {
            if (show) {
                mozoHolder.textViewMozoNombre.setVisibility(View.VISIBLE);
                mozoHolder.checkBoxSeleccionado.setVisibility(View.VISIBLE);
            } else {
                mozoHolder.textViewMozoNombre.setVisibility(View.GONE);
                mozoHolder.checkBoxSeleccionado.setVisibility(View.GONE);
            }
        }
    }



}
