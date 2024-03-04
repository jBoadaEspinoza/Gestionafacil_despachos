package com.example.gestionafacil.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gestionafacil.Controllers.MesasController;
import com.example.gestionafacil.Models.Mesa;
import com.example.gestionafacil.Models.SesionUsuario;
import com.example.gestionafacil.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleDespachoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleDespachoFragment extends Fragment {

    private TextView textViewTituloFragment;
    private String titulo;
    private String id_despacho;

    private SesionUsuario sessionManager;

    public DetalleDespachoFragment() {
        // Required empty public constructor
    }


    public static DetalleDespachoFragment newInstance(String titulo, String idDespacho) {
        DetalleDespachoFragment fragment = new DetalleDespachoFragment();
        Bundle args = new Bundle();
        args.putString("titulo", titulo);
        args.putString("idDespacho", idDespacho);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detalle_despacho, container, false);

        // Obtener el título del despacho de los argumentos
        titulo = getArguments().getString("titulo");
        id_despacho = getArguments().getString("idDespacho");

        // Usar el título del despacho para mostrar la información relevante en el fragmento
        textViewTituloFragment = rootView.findViewById(R.id.textViewTituloFragment);
        textViewTituloFragment.setText(titulo);


        // Configurar el clic del botón de retroceso
        ImageView btnBack = rootView.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volver atrás en la actividad que contiene este fragmento
                requireActivity().onBackPressed();
            }
        });

        // Inicializar el RecyclerView y configurar el MesasAdapter
        RecyclerView recyclerViewMesas = rootView.findViewById(R.id.recyclerViewMesas);
        recyclerViewMesas.setLayoutManager(new LinearLayoutManager(requireContext()));
        MesasAdapter adapter = new MesasAdapter(requireContext(), new ArrayList<>());
        recyclerViewMesas.setAdapter(adapter);

        // Obtener las mesas correspondientes al despacho utilizando el MesasController
        MesasController mesasController = new MesasController();
        // Obtener el token de la sesión del usuario
        sessionManager = new SesionUsuario(requireContext());
        String token = sessionManager.getToken();

        mesasController.obtenerMesas("cantidad_de_articulos_agrupado_por_mesa", id_despacho, token, new MesasController.MesasCallback() {
            @Override
            public void onMesasLoaded(List<Mesa> mesas) {
                // Actualizar el adaptador con las mesas obtenidas
                adapter.setMesas(mesas);
            }

            @Override
            public void onMesassLoadFailed(String errorMessage) {
                // Manejar el fallo al cargar las mesas
            }
        });


        return rootView;
    }


}