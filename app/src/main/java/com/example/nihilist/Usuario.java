package com.example.nihilist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Usuario extends AppCompatActivity {
    Spinner tipo;
    TextView id_usuario, nombre, apellido, email, password, noFunction1;
    Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        tipo = (Spinner) findViewById(R.id.setTipoUsuario);
        id_usuario = (TextView) findViewById(R.id.setIdUsuario);
        nombre = (TextView) findViewById(R.id.setNombreUsuario);
        apellido = (TextView) findViewById(R.id.setApellidoUsuario);
        email = (TextView) findViewById(R.id.setEmailUsuario);
        password = (TextView) findViewById(R.id.setPasswordUsuario);
        noFunction1 = (TextView) findViewById(R.id.noFunction1);
        enviar = (Button) findViewById(R.id.envCambios);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.opciones, android.R.layout.simple_spinner_item);
        tipo.setAdapter(adapter);

        cargarPreferencias();

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(Usuario.this);
                alerta.setMessage("¿Estas Seguro de cambiar tus datos?\t" +
                        "Recuerda elegir el tipo de usuario correctamente.")
                        .setCancelable(true)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Datos actualizados", Toast.LENGTH_SHORT);

                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Actualizar datos");
                titulo.show();
            }
        });

    }

    private void cargarPreferencias() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        id_usuario.setText(preferences.getString("user", "No existe"));
        nombre.setText(preferences.getString("name", "No existe"));
        apellido.setText(preferences.getString("surname", "No existe"));
        email.setText(preferences.getString("email", "No existe"));
        password.setText(preferences.getString("name", "No existe"));
        noFunction1.setText(noFunction1.getText()+": "+preferences.getString("tipo", "No existe"));

    }
}
