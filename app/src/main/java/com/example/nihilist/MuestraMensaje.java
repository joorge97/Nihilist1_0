package com.example.nihilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MuestraMensaje extends AppCompatActivity {

    Intent intent;
    TextView mensaje;
    String mensajito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_mensaje);

        mensajito = (String) getIntent().getSerializableExtra("objetodata");
        mensaje=(TextView) findViewById(R.id.setMensaje);
        mensaje.setText(mensajito);
    }
}
