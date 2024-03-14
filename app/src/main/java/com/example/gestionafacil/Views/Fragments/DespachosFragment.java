package com.example.gestionafacil.Views.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.gestionafacil.Controllers.AreasDespachoController;
import com.example.gestionafacil.Controllers.BuscarController;
import com.example.gestionafacil.Controllers.DespachosController;
import com.example.gestionafacil.Models.AreaDespacho;
import com.example.gestionafacil.Models.Despacho;
import com.example.gestionafacil.Models.SesionUsuario;
import com.example.gestionafacil.R;
import com.example.gestionafacil.Views.Activities.LoginActivity;
import com.example.gestionafacil.Views.Fragments.Adapters.DespachosAdapter;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class DespachosFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener,
        DespachosAdapter.OnDespachoClickListener{


    private RecyclerView recyclerView;
    private DespachosAdapter adapter;
    private DespachosController despachoController;
    private BuscarController buscarController;

    private SesionUsuario sessionManager;
    private DrawerLayout drawerLayout;
    private List<Despacho> listdespachos;


    public DespachosFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_despachos, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerDespachos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener una referencia al SearchView
        SearchView searchView = rootView.findViewById(R.id.searchView);

        // Configurar el listener para el cambio en el texto de búsqueda
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Realizar la búsqueda cuando se envíe el texto de búsqueda (pulsar el botón Enter)
                buscarDespachos(query);
                return true; // Devolver true para indicar que la acción fue manejada
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty() && newText.length() >= 3) {
                    buscarDespachos(newText);
                } else {
                    mostrarTextoSinResultados(false);
                    obtenerDespachos();
                }
                return true; // Devolver false para indicar que no se maneja la acción
            }
        });

        // Obtener el DrawerLayout desde la actividad principal
        drawerLayout = getActivity().findViewById(R.id.drawer_layout);

        // Configurar el Toolbar específico del fragmento
        Toolbar toolbar = rootView.findViewById(R.id.toolbarFragment);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);


        // Configurar el ActionBarDrawerToggle para controlar la apertura y el cierre del DrawerLayout
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(requireActivity(), drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Configurar el clic del botón de opciones
        ImageView btnOptions = toolbar.findViewById(R.id.btnOptions);
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar el menú emergente de opciones
                showOptionsMenu(v);
            }
        });





        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        despachoController = new DespachosController(requireContext());
        sessionManager = new SesionUsuario(requireContext());
        obtenerDespachos();
    }
    private void buscarDespachos(String query) {
        String e_id = String.valueOf(sessionManager.getEstablishmentId());
        String token = sessionManager.getToken();
        String operacion = "ordenes_pendientes";
        String aDenominacion = query; // Utiliza el texto de búsqueda como la denominación del área

        // Realizar la búsqueda utilizando el BuscarController
        BuscarController buscarController = new BuscarController(requireContext());
        buscarController.buscarDespachos(operacion, e_id, token, aDenominacion, new BuscarController.DespachoCallback() {
            @Override
            public void onDespachosLoaded(List<Despacho> despachos) {
                // Manejar la carga de los despachos encontrados
                  if (despachos.isEmpty()) {
                    // Si la lista de despachos está vacía, mostrar un diálogo
                      mostrarTextoSinResultados(true);
                } else {
                    adapter.setDespachos(despachos);
                      mostrarTextoSinResultados(false);
                }
            }

            @Override
            public void onDespachosLoadFailed(String errorMessage) {
                // Manejar el error al cargar los despachos
                Toast.makeText(requireContext(), "Error al buscar despachos: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void obtenerDespachos() {
        listdespachos = new ArrayList<>();
        String e_id = String.valueOf(sessionManager.getEstablishmentId());
        String token = sessionManager.getToken();
        //String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRlX2NyZWF0ZWQiOjE3MDk2NzUwMjQsImRhdGVfZXhwaXJhdGlvbiI6MTcwOTY3NjgyNCwidXNlcl9pZCI6IjU4IiwiYnVzaW5lc3NfaWQiOiIzIn0.-F-nm0Uxar15dTNspq3q3Ho8yGKoPhgvodB1Qka_PAU";

        despachoController.obtenerDespachos("ordenes_pendientes", e_id, token, new DespachosController.DespachoCallback() {
            @Override
            public void onDespachosLoaded(List<Despacho> despachos) {
                for (Despacho despacho : despachos) {
                    Log.d("DespachosFragment", "ID: " + despacho.getId());
                    Log.d("DespachosFragment", "Denominación: " + despacho.getDenominacion());


                }
                // Actualizar la lista de despachos con los despachos cargados
                listdespachos.addAll(despachos);
                adapter = new DespachosAdapter(requireContext(), despachos, DespachosFragment.this);
                adapter.setOnDespachoClickListener(DespachosFragment.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onDespachosLoadFailed(String errorMessage) {
                // Manejar el error al cargar los despachos
                Log.e("DespachosFragment", "Error al cargar los despachos: " + errorMessage);
            }
        });
    }

    private void mostrarTextoSinResultados(boolean mostrar) {
        TextView textoNoResultados = requireView().findViewById(R.id.textNoResults);
        RecyclerView recyclerDespachos = requireView().findViewById(R.id.recyclerDespachos);

        if (mostrar) {
            textoNoResultados.setVisibility(View.VISIBLE);
            textoNoResultados.bringToFront(); // Trae el texto al frente para que se muestre encima de todo
            textoNoResultados.setGravity(Gravity.CENTER); // Centra el texto horizontalmente
            recyclerDespachos.setVisibility(View.GONE);
        } else {
            textoNoResultados.setVisibility(View.GONE);
            recyclerDespachos.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            // Cerrar la sesión del usuario
            sessionManager.logout();

            // Abrir LoginActivity
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        drawerLayout.closeDrawers(); // Cerrar el cajón de navegación después de hacer clic
        return true;
    }

    // Método para mostrar el menú emergente de opciones
    private void showOptionsMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        getActivity().getMenuInflater().inflate(R.menu.options_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Manejar los clics de los elementos del menú
                int id = item.getItemId();
                if (id == R.id.menu_search_filters) {
                    // Acción para Filtros de Búsqueda

                    Toast.makeText(requireContext(), "Filtros de Búsqueda seleccionados", Toast.LENGTH_SHORT).show();
                    showFilterOptionsDialog();
                    return true;
                } else if (id == R.id.menu_help_feedback) {
                    // Acción para Ayudas y Comentarios
                    Toast.makeText(requireContext(), "Ayudas y Comentarios seleccionados", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void showFilterOptionsDialog() {
        // Crear un AlertDialog.Builder y establecer el diseño personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_filtros, null);
        builder.setView(dialogView);

        // Configurar el AlertDialog y mostrarlo
        AlertDialog dialog = builder.create();
        dialog.show();

        // Obtener una referencia al Spinner
        Spinner spinnerOptions = dialogView.findViewById(R.id.spinnerOptions);

        // Obtener los datos de las áreas utilizando el controlador de áreas
        AreasDespachoController areasDespachoController = new AreasDespachoController(requireContext());
        String token = sessionManager.getToken();

        areasDespachoController.obtenerAreasDespacho(token, new AreasDespachoController.AreaDespachoCallback() {
            @Override
            public void onAreasDespachoLoaded(List<AreaDespacho> areasDespacho) {
                // Crear un adaptador personalizado para el Spinner
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Agregar la opción "Todos" al adaptador
                adapter1.add("Todos");
                // Agregar las denominaciones singulares al adaptador
                for (AreaDespacho area : areasDespacho) {
                    adapter1.add(area.getDenominacionSingular());
                }

                // Establecer el adaptador en el Spinner
                spinnerOptions.setAdapter(adapter1);

                // Configurar acciones para los botones Cancelar y Aceptar
                Button btnCancel = dialogView.findViewById(R.id.btnCancel);
                Button btnAccept = dialogView.findViewById(R.id.btnAccept);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Acción para el botón Cancelar
                        dialog.dismiss(); // Cerrar el diálogo
                    }
                });
                btnAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String areaSeleccionada = spinnerOptions.getSelectedItem().toString();
                        List<Despacho> despachosFiltrados = new ArrayList<>();

                        // Verificar si se seleccionó la opción "Todos"
                        if (areaSeleccionada.equals("Todos")) {
                            // Cargar la lista original sin filtrar
                            despachosFiltrados = listdespachos;
                        } else {
                            // Filtrar los despachos por el área seleccionada
                            despachosFiltrados = new ArrayList<>();
                            for (Despacho despacho : listdespachos) {
                                if (despacho.getAreaDeDespacho().equals(areaSeleccionada)) {
                                    despachosFiltrados.add(despacho);
                                }
                            }
                        }
                        // Verificar si se encontraron despachos para el área seleccionada
                        if (despachosFiltrados.isEmpty()) {
                            Toast.makeText(requireContext(), "No se encontraron despachos para el área seleccionada", Toast.LENGTH_SHORT).show();
                        } else {
                            // Actualizar el adaptador del RecyclerView con los despachos filtrados
                            adapter.setDespachos(despachosFiltrados);
                            dialog.dismiss(); // Cerrar el diálogo
                        }
                    }
                });

            }

            @Override
            public void onAreasDespachoLoadFailed(String errorMessage) {
                // Manejar el error al cargar las áreas de despacho
                Toast.makeText(requireContext(), "Error al cargar las áreas de despacho: " + errorMessage, Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // Cerrar el diálogo en caso de error
            }
        });
    }

    @Override
    public void onDespachoClick(String titulo,String idDespacho) {
        // Manejar el clic en el despacho aquí
        // Puedes abrir un nuevo fragmento con el nombre del despacho como título
        // Utiliza el FragmentManager para reemplazar el fragmento actual con el nuevo fragmento
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DetalleDespachoFragment detalleDespachoFragment = DetalleDespachoFragment.newInstance(titulo, idDespacho);
        fragmentTransaction.replace(R.id.main_content, detalleDespachoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}