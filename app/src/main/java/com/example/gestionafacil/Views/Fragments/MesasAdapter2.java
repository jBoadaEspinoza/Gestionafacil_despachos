package com.example.gestionafacil.Views.Fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionafacil.Models.GrupoMesa;
import com.example.gestionafacil.Models.Mozo;
import com.example.gestionafacil.R;

import java.util.List;

public class MesasAdapter2 extends RecyclerView.Adapter<MesaViewHolder>{
    private List<GrupoMesa> mesaList;

    public MesasAdapter2(List<GrupoMesa> mesaList) {
        this.mesaList = mesaList;
    }

    @NonNull
    @Override
    public MesaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mesa, parent, false);
        return new MesaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MesaViewHolder holder, int position) {
        GrupoMesa grupoMesa = mesaList.get(position);
        holder.textViewMesaDenominacion.setText(grupoMesa.getMesa().getMesa_denominacion());
        holder.textViewCantidadMesas.setText(grupoMesa.getMesa().getCantidad());

        // Configurar el adaptador para el RecyclerView de mozos
// Configurar el adaptador para el RecyclerView de mozos
        MozosAdapter mozoAdapter = new MozosAdapter(grupoMesa.getMozos(), new MozosAdapter.OnMozoCheckedChangeListener() {
            @Override
            public void onMozoCheckedChanged(int selectedCount) {


                Log.d("MesasAdapter2", "Mozos seleccionados: " + selectedCount);            }
        });        holder.recyclerViewMozos.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext())); // Asegúrate de configurar el LayoutManager adecuado
        holder.recyclerViewMozos.setAdapter(mozoAdapter);

        // Manejar clic en el botón de expansión
        holder.btnDropdown.setOnClickListener(v -> {
            boolean isExpandable = !grupoMesa.isExpandable(); // Cambiar el estado de expansión
            grupoMesa.setExpandable(isExpandable); // Actualizar el estado en el modelo
            notifyItemChanged(position); // Notificar al adaptador que el elemento ha cambiado
        });

        // Mostrar u ocultar los mozos dependiendo del estado de expansión
        holder.showMozos(grupoMesa.isExpandable());
    }

    @Override
    public int getItemCount() {
        return mesaList.size();
    }

    // Método para agregar mozos a una mesa específica
    public void addMozosToMesa(String mesaId, List<Mozo> mozos) {
        for (GrupoMesa grupoMesa : mesaList) {
            if (grupoMesa.getMesa().getMesa_id().equals(mesaId)) {
                grupoMesa.getMozos().addAll(mozos);
                notifyDataSetChanged();
                return;
            }
        }
    }

    // Método para establecer la lista de mesas
    public void setMesaList(List<GrupoMesa> mesaList) {
        this.mesaList = mesaList;
        notifyDataSetChanged();
    }



}
