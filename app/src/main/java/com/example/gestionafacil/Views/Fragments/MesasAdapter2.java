package com.example.gestionafacil.Views.Fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionafacil.Models.GrupoMesa;
import com.example.gestionafacil.Models.Mesa;
import com.example.gestionafacil.Models.Mozo;
import com.example.gestionafacil.R;

import java.util.ArrayList;
import java.util.List;

public class MesasAdapter2 extends RecyclerView.Adapter<MesaViewHolder>{
    private List<GrupoMesa> mesaList;
    private List<Mozo> mozosAgregados = new ArrayList<>();
    private CheckBox checkBoxGlobal;

    private boolean anyItemSelected = false;

    public void setAnyItemSelected(boolean anyItemSelected) {
        this.anyItemSelected = anyItemSelected;
    }

    public boolean isAnyItemSelected() {
        return anyItemSelected;
    }

    public CheckBox getCheckBoxGlobal() {
        return checkBoxGlobal;
    }

    public void setCheckBoxGlobal(CheckBox checkBoxGlobal) {
        this.checkBoxGlobal = checkBoxGlobal;
    }


    public MesasAdapter2(List<GrupoMesa> mesaList) {
        this.mesaList = mesaList;
    }

    public List<GrupoMesa> getMesaList() {
        return mesaList;
    }

    public List<Mozo> getMozosAgregados() {
        return mozosAgregados;
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
        holder.bind(grupoMesa.getMesa());
        // Agregar un listener al checkbox de la mesa
        holder.checkBoxSeleccionada.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Actualizar el estado del checkbox de la mesa en el GrupoMesa
                grupoMesa.getMesa().setChecked(isChecked);
            }
        });
        // Configurar el adaptador para el RecyclerView de mozos
        MozosAdapter mozoAdapter = new MozosAdapter(grupoMesa.getMozos(), new MozosAdapter.OnMozoCheckedChangeListener() {

            @Override
            public void onMozoCheckedChanged(int selectedCount) {
                Log.d("MesasAdapter2", "Mozos seleccionados: " + selectedCount);
                // Verificar si no todos los elementos están seleccionados y actualizar el estado del checkbox global


            }

            @Override
            public void onMozosListChanged(Mozo mozo) {
                Log.d("MesasAdapter2", "Mozo seleccionado:");
                Log.d("MesasAdapter2", "ID: " + mozo.getId() + ", Nombre: " + mozo.getMozo_nombre());
                mozosAgregados.add(mozo); // Agregar el mozo a la lista
            }

            @Override
            public void onMozoRemoved(Mozo mozo) {
                mozosAgregados.remove(mozo); // Remover el mozo de la lista
                checkBoxGlobal.setChecked(false);
            }
        });

        holder.recyclerViewMozos.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext())); // Asegúrate de configurar el LayoutManager adecuado
        holder.recyclerViewMozos.setAdapter(mozoAdapter);

        // Manejar clic en el botón de expansión
        holder.btnDropdown.setOnClickListener(v -> {
            boolean isExpandable = !grupoMesa.isExpandable(); // Cambiar el estado de expansión
            grupoMesa.setExpandable(isExpandable); // Actualizar el estado en el modelo
            notifyItemChanged(position);

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

    public void imprimirMozosAgregados() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Mozos agregados:\n");
        for (Mozo mozo : mozosAgregados) {
            stringBuilder.append("ID: ").append(mozo.getId()).append(", Nombre: ").append(mozo.getMozo_nombre()).append("\n");
        }
        Log.d("MesasAdapter2", stringBuilder.toString());
    }


    public boolean isAnyItemDeselected() {
        for (GrupoMesa grupoMesa : mesaList) {
            if (!grupoMesa.getMesa().isChecked()) {
                return true;
            }
            for (Mozo mozo : grupoMesa.getMozos()) {
                if (!mozo.isChecked()) {
                    return true;
                }
            }
        }
        return false;
    }


    public void selectAllItems() {
        for (GrupoMesa grupoMesa : mesaList) {
            grupoMesa.getMesa().setChecked(true);
            for (Mozo mozo : grupoMesa.getMozos()) {
                mozo.setChecked(true);
                // Agregar el mozo a la lista de mozos agregados si no está presente
                if (!mozosAgregados.contains(mozo)) {
                    mozosAgregados.add(mozo);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void deselectAllItems() {
        mozosAgregados.clear(); // Limpiar la lista de mozos agregados
        for (GrupoMesa grupoMesa : mesaList) {
            grupoMesa.getMesa().setChecked(false);
            for (Mozo mozo : grupoMesa.getMozos()) {
                mozo.setChecked(false);
            }
        }
        notifyDataSetChanged();
    }


}
