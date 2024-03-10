package com.example.gestionafacil.Views.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.gestionafacil.Controllers.DespacharController;
import com.example.gestionafacil.Controllers.MesasController;
import com.example.gestionafacil.Controllers.MozosController;
import com.example.gestionafacil.Models.GrupoMesa;
import com.example.gestionafacil.Models.Mesa;
import com.example.gestionafacil.Models.Mozo;
import com.example.gestionafacil.Models.SesionUsuario;
import com.example.gestionafacil.R;
import com.example.gestionafacil.Views.DespachoDialog;
import com.example.gestionafacil.Views.MainActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
    private MesasAdapter2 mesasAdapter;

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
        mesasAdapter = new MesasAdapter2(new ArrayList<>());
        recyclerViewMesas.setAdapter(mesasAdapter);

        // Obtener las mesas correspondientes al despacho
        obtenerMesas();
        // Configurar el clic del botón de despachar
        Button btnDespachar = rootView.findViewById(R.id.btnDespachar);
        btnDespachar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Imprimir la lista de mozos seleccionados
                mesasAdapter.imprimirMozosAgregados();

                // Obtener los mozos seleccionados del adaptador
                List<Mozo> mozosSeleccionados = mesasAdapter.getMozosAgregados();

                // Crear un JsonArray para almacenar los IDs de los mozos seleccionados
                JsonArray idsMozos = new JsonArray();
                for (Mozo mozo : mozosSeleccionados) {
                    idsMozos.add(mozo.getId());
                }

                // Obtener el token de la sesión de usuario (si es necesario)
                String token = sessionManager.getToken(); // Asegúrate de tener implementado el manejo del token

                if(mozosSeleccionados.size() > 0){
                    mostrarAlerta(getContext(), mozosSeleccionados.size());
                }else{
                    mostrarAlertaVacio(getContext());
                }
                //despacharPedidos(idsMozos, token);
            }
        });

        // Obtener referencia al CheckBox "Todos"
        CheckBox checkBoxTodos = rootView.findViewById(R.id.checkboxGlobal);
        // Agregar un listener al CheckBox "Todos"
        checkBoxTodos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Si el checkbox global está marcado
                if (isChecked) {
                    mesasAdapter.selectAllItems();
                } else {
                    checkBoxTodos.setChecked(false);
                }
            }
        });
        mesasAdapter.setCheckBoxGlobal(checkBoxTodos);




        return rootView;
    }
    private void obtenerMesas() {
        // Aquí obtienes las mesas y actualizas el adaptador
        MesasController mesasController = new MesasController(requireContext());
        // Obtener el token de la sesión del usuario
        sessionManager = new SesionUsuario(requireContext());
        String token = sessionManager.getToken();

        mesasController.obtenerMesas("cantidad_de_articulos_agrupado_por_mesa", id_despacho, token, new MesasController.MesasCallback() {
            @Override
            public void onMesasLoaded(List<Mesa> mesas) {
                // Actualizar el adaptador con las mesas obtenidas
                List<GrupoMesa> grupos = new ArrayList<>();
                for (Mesa mesa : mesas) {
                    grupos.add(new GrupoMesa(mesa, new ArrayList<>(), false));
                    obtenerMozos("detalle_segun_articulo_y_mesa", id_despacho, mesa.getMesa_id(), token);
                }
                mesasAdapter.setMesaList(grupos);
            }

            @Override
            public void onMesassLoadFailed(String errorMessage) {
                // Manejar el fallo al cargar las mesas
            }
        });
    }
    private void obtenerMozos(String operacion, String aId, String mId, String token) {
        // Aquí obtienes los mozos y los agregas al grupo de mesa correspondiente
        MozosController mozosController = new MozosController();
        mozosController.obtenerMozos(operacion, aId, mId, token, new MozosController.MozosCallback() {
            @Override
            public void onMozosLoaded(List<Mozo> mozos) {
                // Agregar mozos al grupo de mesa correspondiente en el adaptador
                mesasAdapter.addMozosToMesa(mId, mozos);
            }

            @Override
            public void onMozosLoadFailed(String errorMessage) {
                // Aquí puedes manejar el fallo al cargar los mozos
            }
        });
    }

    // Método para despachar los pedidos
    private void despacharPedidos(JsonArray idsMozos, String token) {
        DespacharController despacharController = new DespacharController(getContext());
        despacharController.despacharPedido(idsMozos, token, new DespacharController.DespacharCallback() {
            @Override
            public void onDespachoSuccess(JsonObject responseBody) {

            }

            @Override
            public void onDespachoFailure(String errorMessage) {
                // La solicitud de despacho falló
            }
        });
    }
    private void mostrarDialogoDespachoExitoso(int cantidadDespachos) {
        DespachoDialog dialog = new DespachoDialog(getContext(), cantidadDespachos);
        dialog.show();
    }

    public static void mostrarAlerta(Context context, int cantidadDespachos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Despachos enviados con exito");

        // Crear un ImageView para mostrar el GIF
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200)); // Ajusta el tamaño del ImageView según sea necesario

        // Cargar el GIF usando Glide y mostrarlo en el ImageView
        Glide.with(context)
                .asGif()
                .load(R.drawable.system_regular_31_check)
                .into(imageView);

        // Agregar el ImageView al AlertDialog
        builder.setView(imageView);
        builder.setMessage("Despachos enviados: " + cantidadDespachos);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // El usuario hizo clic en el botón "OK", cierra el diálogo
                dialog.dismiss();

                // Iniciar la actividad MainActivity
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpia todas las actividades anteriores
                context.startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void mostrarAlertaVacio(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("No a seleccionado ningun pedido");

        // Crear un ImageView para mostrar el GIF
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // El usuario hizo clic en el botón "OK", cierra el diálogo
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}