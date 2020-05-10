package com.example.nihilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

public class Chat extends AppCompatActivity {

    TextView usuario;
    ArrayAdapter adaptador;
    ArrayList<String> lista = new ArrayList<String>();
    ListView listView;
    RequestQueue requestQueue;
    String usuarioProc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        usuario = (TextView) findViewById(R.id.usuario);

        cargarPreferencias(usuario);
        usuarioProc=cargarDatosMensajes();
        String [] sep = usuarioProc.split(" ");
        cargarUsuarios("https://sqliteludens.000webhostapp.com/connect/getUniqueMessages.php?toid="+usuario.getText()+"&name="+sep[1]);
    }

    private void cargarPreferencias(TextView usuario) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("user", "No existe");
        usuario.setText(user);
    }

    private String cargarDatosMensajes() {
        SharedPreferences preferences = getSharedPreferences("mensaje", Context.MODE_PRIVATE);
        String user = preferences.getString("msge", "No existe");
        return user;
    }

    private void cargarUsuarios(String URL){
        listView =(ListView)findViewById(R.id.listaChat);
        for (int i=0; i<lista.size();i++){
            lista.remove(i);
        }
        adaptador = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,lista);
        listView.setAdapter(adaptador);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        lista.add("De: "+jsonObject.getString("name")+
                                "\nFecha: "+jsonObject.getString("fecha")
                                +"\n"+ jsonObject.getString("message"));
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

        try {
            Thread.sleep(5*1000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
