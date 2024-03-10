package com.example.gestionafacil.Views;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.gestionafacil.Models.SesionUsuario;
import com.example.gestionafacil.R;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.TextView;

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

}