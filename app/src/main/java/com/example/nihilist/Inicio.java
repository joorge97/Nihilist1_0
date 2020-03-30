package com.example.nihilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Inicio extends AppCompatActivity {

    Button usuario, enviar, refrescar;
    ArrayAdapter adaptador;
    ArrayList<String> lista = new ArrayList<String>();
    ListView listView;
    RequestQueue requestQueue;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        usuario=(Button) findViewById(R.id.usuario);
        listView =(ListView)findViewById(R.id.list);
        enviar=(Button) findViewById(R.id.enviarMSG);
        refrescar=(Button)findViewById(R.id.refrescar);

        cargarPreferencias(usuario);
        cargarMensajes("https://sqliteludens.000webhostapp.com/connect/getmensajes.php?toid="+id);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Inicio.this, Enviar.class);
                startActivity(i);
            }
        });

        adaptador = new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista);
        listView.setAdapter(adaptador);
        refrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(v.getContext(), "Refrescando mensajes.", Toast.LENGTH_SHORT);
                toast.show();
                cargarMensajes("https://sqliteludens.000webhostapp.com/connect/getmensajes.php?toid="+id);
                adaptador = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,lista);
                listView.setAdapter(adaptador);
            }
        });
    }


    /**
     *  CARGAMOS LOS DATOS DEL USUARIO
     * @param usuario
     */
    private void cargarPreferencias(Button usuario) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("name", "No existe");
        id=preferences.getString("user", "No existe");
        usuario.setText(user);
    }

    /**
     * CARGAMOS LOS MENSAJES
     * @param URL
     * @return
     */
    private void cargarMensajes(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        lista.add("De: "+jsonObject.getString("name")+": "+jsonObject.getString("message"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
