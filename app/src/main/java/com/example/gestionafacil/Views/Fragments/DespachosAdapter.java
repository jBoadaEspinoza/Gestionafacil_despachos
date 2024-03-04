package com.example.gestionafacil.Views.Fragments;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestionafacil.Models.Despacho;
import com.example.gestionafacil.R;
import java.util.List;

public class DespachosAdapter  extends RecyclerView.Adapter<DespachosAdapter.DespachoViewHolder>{
    private Context context;
    private List<Despacho> despachos;

    private OnDespachoClickListener mListener;

    public DespachosAdapter(Context context, List<Despacho> despachos, OnDespachoClickListener listener) {
        this.context = context;
        this.despachos = despachos;
    }
    public void setDespachos(List<Despacho> despachos) {
        this.despachos = despachos;
        notifyDataSetChanged();
    }

    public void setOnDespachoClickListener(OnDespachoClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public DespachoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_despacho, parent, false);
        return new DespachoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DespachoViewHolder holder, int position) {
        Despacho despacho = despachos.get(position);
        holder.textViewTitulo.setText(despacho.getDenominacion());
        holder.textViewEstablecimiento.setText(despacho.getAreaDeDespacho());
        holder.textViewCantidad.setText(despacho.getCantidad());

        // Configurar el clic en el elemento
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el título del despacho seleccionado
                String titulo = despacho.getDenominacion();
                String id = despacho.getId();
                // Notificar al listener del clic en el despacho
                mListener.onDespachoClick(titulo, id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return despachos.size();
    }

    public static class DespachoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitulo;
        TextView textViewEstablecimiento;
        TextView textViewCantidad;

        public DespachoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewEstablecimiento = itemView.findViewById(R.id.textViewEstablecimiento);
            textViewCantidad = itemView.findViewById(R.id.textViewCantidad);
        }
    }

    public interface OnDespachoClickListener {
        void onDespachoClick(String titulo, String id);
    }
}
