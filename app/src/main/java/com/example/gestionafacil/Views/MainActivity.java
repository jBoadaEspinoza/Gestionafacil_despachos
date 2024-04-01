package com.example.gestionafacil.Views;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.gestionafacil.Models.SesionUsuario;
import com.example.gestionafacil.R;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestionafacil.Views.Activities.LoginActivity;
import com.example.gestionafacil.Views.Fragments.DespachosFragment;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private TextView textViewEstablishmentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Obtener una instancia de TextView para el nombre del establecimiento
        textViewEstablishmentName = navigationView.getHeaderView(0).findViewById(R.id.textViewEstablishmentName);


        // Obtener el nombre del establecimiento de la sesión del usuario y establecerlo en el TextView
        SesionUsuario sesionUsuario = new SesionUsuario(this);
        String establishmentName = sesionUsuario.getEstablishmentName();
        textViewEstablishmentName.setText(establishmentName);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new DespachosFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            // Cerrar la sesión del usuario
            SesionUsuario sesionUsuario = new SesionUsuario(this);
            sesionUsuario.logout();


            // Abrir LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        drawerLayout.closeDrawers(); // Cerrar el cajón de navegación después de hacer clic
        return true;
    }

    private boolean doubleBackToExitPressedOnce = false;
    private static final int TIME_INTERVAL = 2000; // Intervalo de tiempo en milisegundos para el doble clic

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.main_content);

        if (currentFragment instanceof DespachosFragment) {
            if (doubleBackToExitPressedOnce) {
                new AlertDialog.Builder(this)
                        .setTitle("Salir de la aplicación")
                        .setMessage("¿Estás seguro de que deseas salir de la aplicación?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish(); // Cierra la aplicación
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            } else {
                // Si el fragmento actual es DespachosFragment, pero no se ha presionado dos veces
                // entonces establece doubleBackToExitPressedOnce como true y muestra el mensaje Toast
                doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Presiona otra vez para salir", Toast.LENGTH_SHORT).show();
                // Si el fragmento actual no es DespachosFragment, carga nuevamente el fragmento
                Fragment despachosFragment = new DespachosFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_content, despachosFragment)
                        .commit();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, TIME_INTERVAL);
            }
        }else {
            // Si el fragmento actual no es DespachosFragment, realiza el comportamiento predeterminado de onBackPressed
            super.onBackPressed();
        }
    }

}