package com.example.nihilist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Inicio extends AppCompatActivity {

    Button usuario, enviar, refrescar;
    ArrayAdapter adaptador;
    ArrayList<String> lista = new ArrayList<String>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        usuario=(Button) findViewById(R.id.usuario);
        listView =(ListView)findViewById(R.id.list);
        enviar=(Button) findViewById(R.id.enviarMSG);
        refrescar=(Button)findViewById(R.id.refrescar);

        cargarPreferencias(usuario);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Inicio.this, Enviar.class);
                startActivity(i);
            }
        });

        refrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(v.getContext(), "Refrescando mensajes.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        // desarrollo de la bbdd
        lista.add("HOLA");
        lista.add("HOLA");
        lista.add("HOLA");
        lista.add("HOLA");
        lista.add("HOLA");
        lista.add("HOLA");
        lista.add("HOLA");
        lista.add("HOLA");
        lista.add("HOLA");
        lista.add("HOLA");
        lista.add("HOLA");
        lista.add("HOLA");
        lista.add("HOLA");
        lista.add("HOLA");
        lista.add("HOLA");
        lista.add("HOLA");

        adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
        listView.setAdapter(adaptador);

    }



    private void cargarPreferencias(Button usuario) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("name", "No existe");
        usuario.setText(user);
    }

    private void cargarMensajes(){

    }
}
