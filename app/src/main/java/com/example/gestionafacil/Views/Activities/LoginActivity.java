package com.example.gestionafacil.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gestionafacil.Controllers.UsuarioController;
import com.example.gestionafacil.Controllers.UsuarioController.OnLoginListener;

import com.example.gestionafacil.Models.SesionUsuario;
import com.example.gestionafacil.Models.Usuario;
import com.example.gestionafacil.R;
import com.example.gestionafacil.Views.MainActivity;


public class LoginActivity extends AppCompatActivity {
    private EditText editTextRuc, editTextUsuario, editTextClave;
    private Button buttonLogin;
    private UsuarioController usuarioController;
    private SesionUsuario sesionUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar vistas
        editTextRuc = findViewById(R.id.editTextRuc);
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextClave = findViewById(R.id.editTextClave);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Inicializar UsuarioController
        usuarioController = new UsuarioController(this);
        sesionUsuario = new SesionUsuario(this); // Pasar el contexto de la actividad

        // Escuchar clics en el botón de inicio de sesión
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ruc = editTextRuc.getText().toString().trim();
                String usuario = editTextUsuario.getText().toString().trim();
                String clave = editTextClave.getText().toString().trim();

                // Realizar inicio de sesión
                usuarioController.login(ruc, usuario, clave, new OnLoginListener() {
                    @Override
                    public void onSuccess() {
                        // Inicio de sesión exitoso, mostrar un mensaje de éxito
                        String token = sesionUsuario.getToken();
                        int e_id = sesionUsuario.getEstablishmentId();
                        Toast.makeText(LoginActivity.this, "Token: " + e_id, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(String errorMessage) {
                        // Error en el inicio de sesión, mostrar un mensaje de error
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


}