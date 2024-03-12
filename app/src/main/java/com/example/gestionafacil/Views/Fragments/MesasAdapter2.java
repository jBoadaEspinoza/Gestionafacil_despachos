package com.example.gestionafacil.Views.Fragments;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private boolean onBind;
    private Button btnDespachar;

    public void setBtnDespachar(Button btnDespachar) {
        this.btnDespachar = btnDespachar;
    }
    private boolean isSelectAll = false;

    private boolean anyItemSelected = false;

    public void setCheckBoxGlobal(CheckBox checkBoxGlobal) {
        this.checkBoxGlobal = checkBoxGlobal;
    }


    public MesasAdapter2(List<GrupoMesa> mesaList) {
        this.mesaList = mesaList;
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

        holder.bind(grupoMesa.getMesa());

        // Establecer el estado de selección del CheckBox
        onBind = true;
        holder.checkBoxSeleccionada.setChecked(grupoMesa.getMesa().isChecked());
        onBind = false;
        holder.checkBoxSeleccionada.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!onBind){
                    // Actualizar el estado de selección de la mesa y de todos sus mozos asociados
                    if (isChecked) {
                        grupoMesa.getMesa().setChecked(true);
                        mozosAgregados.addAll(grupoMesa.getMozos());
                        updateMozosCheckboxes(grupoMesa, true);
                    } else {
                        // Verificar si todos los mozos ya están marcados
                        boolean allMozosChecked = true;
                        for (Mozo mozo : grupoMesa.getMozos()) {
                            if (!mozo.isChecked()) {
                                allMozosChecked = false;
                                break;
                            }
                        }
                        // Si todos los mozos están marcados, desmarcar todo
                        if (allMozosChecked) {
                            // Desmarcar el checkbox de la mesa
                            grupoMesa.getMesa().setChecked(false);
                            // Desmarcar todos los mozos asociados
                            for (Mozo mozo : grupoMesa.getMozos()) {
                                mozo.setChecked(false);
                            }
                            mozosAgregados.removeAll(grupoMesa.getMozos());
                            // Notificar cambios en el adaptador
                            notifyDataSetChanged();
                        }
                    }
                }

            }
        });
        // Configurar el adaptador para el RecyclerView de mozos
        MozosAdapter mozoAdapter = new MozosAdapter(grupoMesa.getMozos(), new MozosAdapter.OnMozoCheckedChangeListener() {

            @Override
            public void onMozoCheckedChanged(int selectedCount) {
                Log.d("MesasAdapter2", "Mozos seleccionados: " + selectedCount);
                // Verificar si no todos los elementos están seleccionados y actualizar el estado del checkbox global
                boolean allMozosChecked = areAllMozosChecked(grupoMesa);
                holder.checkBoxSeleccionada.setChecked(allMozosChecked);
                btnDespachar.setText("Despachar (" + mozosAgregados.size() + ")");
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
                // Verificar si el mozo eliminado estaba marcado
                if (mozo.isChecked()) {
                    // Verificar si quedan más mozos marcados en la mesa
                    boolean anyMozosChecked = false;
                    btnDespachar.setText("Despachar (" + mozosAgregados.size() + ")");

                    for (Mozo m : grupoMesa.getMozos()) {
                        if (m.isChecked()) {
                            anyMozosChecked = true;
                            break;
                        }
                    }
                    // Desmarcar el CheckBox de la mesa solo si no quedan más mozos marcados
                    if (!anyMozosChecked) {
                        btnDespachar.setText("Despachar (" + mozosAgregados.size() + ")");

                        holder.checkBoxSeleccionada.setChecked(false);
                        checkBoxGlobal.setChecked(false);
                    }
                }
            }
        });

        holder.recyclerViewMozos.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext())); // Asegúrate de configurar el LayoutManager adecuado
        holder.recyclerViewMozos.setAdapter(mozoAdapter);

        // Manejar clic en el checkbox de la mesa


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


    public void selectAllItems() {
        isSelectAll = true;
        for (GrupoMesa grupoMesa : mesaList) {
            grupoMesa.getMesa().setChecked(true); // Establecer el estado de selección de la mesa como verdadero
            updateMozosCheckboxes(grupoMesa, true); // Actualizar los checkboxes de los mozos asociados
            mozosAgregados.addAll(grupoMesa.getMozos());
        }
        btnDespachar.setText("Despachar (" + mozosAgregados.size() + ")");
        notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
    }



    public void deselectAllItems() {
        mozosAgregados.clear(); // Limpiar la lista de mozos agregados
        for (GrupoMesa grupoMesa : mesaList) {
            grupoMesa.getMesa().setChecked(false);
            for (Mozo mozo : grupoMesa.getMozos()) {
                mozo.setChecked(false);
            }
        }
        btnDespachar.setText("Despachar (" + mozosAgregados.size() + ")");

        notifyDataSetChanged();
    }
    private boolean areAllMozosChecked(GrupoMesa grupoMesa) {
        for (Mozo mozo : grupoMesa.getMozos()) {
            if (!mozo.isChecked()) {
                return false;
            }
        }
        return true;
    }
    private void updateMozosCheckboxes(GrupoMesa grupoMesa, boolean isChecked) {
        // Actualizar el estado de los checkboxes de los mozos asociados
        for (Mozo mozo : grupoMesa.getMozos()) {
            mozo.setChecked(isChecked);
        }
        notifyDataSetChanged();

    }

    // Método para verificar si todos los elementos están seleccionados

}
