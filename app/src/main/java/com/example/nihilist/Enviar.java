package com.example.nihilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Enviar extends AppCompatActivity {

    RequestQueue requestQueue;
    ImageButton enviar, limpiar;
    TextView usuario;
    EditText mensaje;
    Spinner spinner;
    ArrayList<String> contactos = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);

        contactos.add("Selecciona:");

        usuario = (TextView) findViewById(R.id.usuario);
        spinner = (Spinner)  findViewById(R.id.comboUser);
        enviar = (ImageButton) findViewById(R.id.enviarMSG);
        limpiar = (ImageButton) findViewById(R.id.btnLimpiar);
        mensaje = (EditText) findViewById(R.id.mensaje);

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, contactos));

        cargarPreferencias(usuario);

        cargarUsuarios("https://sqliteludens.000webhostapp.com/connect/getallusers.php");

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String [] sep = spinner.getSelectedItem().toString().split("-");
                enviarMensaje("https://sqliteludens.000webhostapp.com/connect/sendmensajes.php?by_id="+usuario.getText().toString()
                        +"&&to_id="+sep[0]+"&&message_="+mensaje.getText().toString()+"");
                Toast toast = Toast.makeText(v.getContext(), "Mensaje enviado a: "+spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensaje.setText("");
            }
        });
    }

    /**
     * CARGA LAS PREFERENCIAS
     * @param usuario
     */
    private void cargarPreferencias(TextView usuario) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("user", "No existe");
        usuario.setText(user);
    }

    /**     
     * ENVIA MENSAJES
     * @param URL
     */
    private void enviarMensaje(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Mensaje enviado a: "+spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String [] sep = spinner.getSelectedItem().toString().split("-");
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("by_id", usuario.getText().toString());
                parametros.put("to_id", sep[0]);
                parametros.put("message_", mensaje.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /**
     * CARGA LOS USUARIOS EN LA PANTALLA DE MENSAJES
     * @param URL
     */
    private void cargarUsuarios(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        contactos.add(jsonObject.getString("id_usuario")+"-"+jsonObject.getString("name"));
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
