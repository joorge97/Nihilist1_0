package com.example.nihilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Enviar extends AppCompatActivity {

    Button usuario, enviar;
    EditText mensaje;
    Spinner spinner;
    String [] letra= {"Sweet","Acid","Salty"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);

        usuario = (Button) findViewById(R.id.usuario);
        spinner = (Spinner)  findViewById(R.id.comboUser);
        enviar = (Button) findViewById(R.id.enviarMSG);
        mensaje = (EditText) findViewById(R.id.mensaje);

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));

        cargarPreferencias(usuario);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensaje("https://sqliteludens.000webhostapp.com/connect/sendmensajes.php?by_id="+usuario.getText().toString()
                        +"&&to_id="+spinner.getSelectedItem().toString()+"&&message_="+mensaje.getText().toString()+"");
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
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("by_id", usuario.getText().toString());
                parametros.put("to_id", spinner.getSelectedItem().toString());
                parametros.put("message_", mensaje.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
