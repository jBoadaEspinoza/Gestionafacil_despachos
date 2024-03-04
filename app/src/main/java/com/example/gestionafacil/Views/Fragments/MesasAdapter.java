package com.example.gestionafacil.Views.Fragments;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionafacil.Models.Mesa;
import com.example.gestionafacil.R;

import java.util.List;

public class MesasAdapter extends RecyclerView.Adapter<MesasAdapter.MesaViewHolder>{
    private Context context;
    private List<Mesa> mesas;

    public MesasAdapter(Context context, List<Mesa> mesas) {
        this.context = context;
        this.mesas = mesas;
    }
    public void setMesas(List<Mesa> mesas) {
        this.mesas = mesas;
        notifyDataSetChanged(); // Notificar al adaptador sobre el cambio en la lista de mesas
    }
    @NonNull
    @Override
    public MesaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mesa, parent, false);
        return new MesaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MesaViewHolder holder, int position) {
        Mesa mesa = mesas.get(position);
        holder.textViewMesaDenominacion.setText(mesa.getMesa_denominacion());
        holder.textViewCantidadMesas.setText(String.valueOf(mesa.getCantidad()));

        // Configurar el clic del botón de desplegar detalles
        holder.btnDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes implementar la lógica para mostrar los detalles de la mesa
            }
        });
    }

    @Override
    public int getItemCount() {
        return mesas.size();
    }

    public static class MesaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMesaDenominacion, textViewCantidadMesas;
        CheckBox checkBoxSeleccionada;
        ImageView btnDropdown;

        public MesaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMesaDenominacion = itemView.findViewById(R.id.textViewMesaDenominacion);
            textViewCantidadMesas = itemView.findViewById(R.id.textViewCantidadMesas);
            checkBoxSeleccionada = itemView.findViewById(R.id.checkBoxSeleccionada);
            btnDropdown = itemView.findViewById(R.id.btnDropdown);

        }
    }

}
