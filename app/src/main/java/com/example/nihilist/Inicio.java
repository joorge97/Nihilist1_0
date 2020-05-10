package com.example.nihilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
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

public class Inicio extends AppCompatActivity {

    TextView usuario;
    ImageButton btnUsuario, enviarMSG;
    Button enviar, refrescar;
    ArrayAdapter adaptador;
    ArrayList<String> lista = new ArrayList<String>();
    ListView listView;
    RequestQueue requestQueue;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        btnUsuario=(ImageButton) findViewById(R.id.btnUsuario);
        enviarMSG=(ImageButton) findViewById(R.id.enviarMSG);
        refrescar=(Button)findViewById(R.id.refrescar);
        usuario=(TextView) findViewById(R.id.usuario);

        cargarPreferencias(usuario);

        // RELLENA EL LISTVIEW
        cargarUsuarios("https://sqliteludens.000webhostapp.com/connect/obtMensajesOneUser.php?toid="+id);

        enviarMSG.setOnClickListener(new View.OnClickListener() {
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
                cargarUsuarios("https://sqliteludens.000webhostapp.com/connect/obtMensajesOneUser.php?toid="+id);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent inteni = new Intent(Inicio.this, Chat.class);
                    guardarUsuario("de salty");
                    //(String) listView.getSelectedItem()
                    startActivity(inteni);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btnUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUsuarioConf= new Intent(Inicio.this, Usuario.class);
                startActivity(intentUsuarioConf);
            }
        });
    }


    /**
     *  CARGAMOS LOS DATOS DEL USUARIO
     * @param usuario
     */
    private void cargarPreferencias(TextView usuario) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("name", "No existe");
        id=preferences.getString("user", "No existe");
        usuario.setText("   "+user);
    }

    /**
     * CARGAMOS LOS MENSAJES
     * @param URL
     * @return
     */
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
                        lista.add("De: "+jsonObject.getString("name")+" "+jsonObject.getString("fecha"));
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

    /**
     * GUARDA EL USUARIO PARA LA VENTANA DE MOSTRAR MENSAJES COMPLETOS
     * @param msge
     */
    private void guardarUsuario(String msge) {
        SharedPreferences preferences = getSharedPreferences("mensaje", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  preferences.edit();

        editor.putString("msge", msge);

        editor.commit();
    }
}
