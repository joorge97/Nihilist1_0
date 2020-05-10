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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Usuario extends AppCompatActivity {
    Spinner tipo;
    TextView id_usuario, nombre, apellido, email, password, noFunction1;
    ImageButton enviar;

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
        enviar = (ImageButton) findViewById(R.id.envCambios);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.opciones, android.R.layout.simple_spinner_item);
        tipo.setAdapter(adapter);

        cargarPreferencias();

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Datos actualizados", Toast.LENGTH_SHORT);
                actualizarUser("https://sqliteludens.000webhostapp.com/connect/actualizarDatosUsuarios.php?" +
                        "id="+id_usuario.getText().toString()+
                        "&&tipo="+tipo.getSelectedItem().toString()+"" +
                        "&&name="+nombre.getText().toString()+
                        "&&surname="+apellido.getText().toString()+
                        "&&email="+email.getText().toString()+
                        "&&password="+password.getText().toString()+"");
                toast.show();
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

    private void actualizarUser(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Datos actualizados ", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("id", id_usuario.getText().toString());
                parametros.put("tipo", tipo.getSelectedItem().toString());
                parametros.put("name", nombre.getText().toString());
                parametros.put("surname", apellido.getText().toString());
                parametros.put("email", email.getText().toString());
                parametros.put("password", password.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
