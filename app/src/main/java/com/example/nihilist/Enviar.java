package com.example.nihilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Enviar extends AppCompatActivity {

    Button usuario, enviar;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);

        usuario = (Button) findViewById(R.id.usuario);
        spinner = (Spinner)  findViewById(R.id.comboUser);
        enviar = (Button) findViewById(R.id.enviarMSG);

        String[] letra = {"Sweet","Acid","Salty"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));

        cargarPreferencias(usuario);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast toast = Toast.makeText(v.getContext(), "Mensaje enviado a: "+spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private void cargarPreferencias(Button usuario) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("user", "No existe");
        usuario.setText(user);
    }
}
