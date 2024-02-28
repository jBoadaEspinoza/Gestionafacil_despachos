package com.example.gestionafacil.Views.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.content.Context;

import androidx.annotation.Nullable;

import com.example.gestionafacil.Controllers.DespachosController;
import com.example.gestionafacil.Controllers.UsuarioController;
import com.example.gestionafacil.Models.Despacho;
import com.example.gestionafacil.Models.SesionUsuario;
import com.example.gestionafacil.R;
import java.util.List;

import android.util.Log;

public class DespachosFragment extends Fragment {


    private RecyclerView recyclerView;
    private DespachosAdapter adapter;
    private DespachosController despachoController;
    private SesionUsuario sessionManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_despachos, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerDespachos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        despachoController = new DespachosController();
        sessionManager = new SesionUsuario(requireContext());
        obtenerDespachos();
    }

    private void obtenerDespachos() {
        String e_id = String.valueOf(sessionManager.getEstablishmentId());
        String token = sessionManager.getToken();

        despachoController.obtenerDespachos("ordenes_pendientes", e_id, token, new DespachosController.DespachoCallback() {
            @Override
            public void onDespachosLoaded(List<Despacho> despachos) {
                for (Despacho despacho : despachos) {
                    Log.d("DespachosFragment", "ID: " + despacho.getId());
                    Log.d("DespachosFragment", "Denominación: " + despacho.getDenominacion());
                    adapter = new DespachosAdapter(requireContext(), despachos);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onDespachosLoadFailed(String errorMessage) {
                // Manejar el error al cargar los despachos
                Log.e("DespachosFragment", "Error al cargar los despachos: " + errorMessage);
            }
        });
    }
}