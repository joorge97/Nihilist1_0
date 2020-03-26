package com.example.nihilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jorge
 */
public class MainActivity extends AppCompatActivity {

    Button registrar, iniciar;
    EditText getUsuario, getPass;
    RequestQueue requestQueue;
    Boolean session = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cargarPreferencias(getUsuario, getPass);

        registrar = (Button) findViewById(R.id.btnregistrar);
        iniciar = (Button) findViewById(R.id.btniniciar);
        getUsuario = (EditText) findViewById(R.id.getDNI);
        getPass = (EditText) findViewById(R.id.getPass);

        getUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsuario.setText("");
            }
        });
        getPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPass.setText("");
            }
        });

        // LOGEAR
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarUsuario("https://sqliteludens.000webhostapp.com/connect/validar_usuarios.php?id_usuario="
                        +getUsuario.getText().toString()+"&&password="+getPass.getText().toString()+"");
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Registrar.class);
                startActivity(i);
            }
        });
    }

    /**
     * GUARDAR PREFERENCIAS(USUARIOS)
     * @param id_usuario
     * @param password
     * @param tipo
     * @param name
     * @param surname
     * @param email
     * @param session
     */
    private void guardarPreferencuas(String id_usuario, String password, String tipo, String name, String surname, String email, Boolean session) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  preferences.edit();

        editor.putString("user", id_usuario);
        editor.putString("pass", password);
        editor.putString("tipo", tipo);
        editor.putString("name", name);
        editor.putString("surname", surname);
        editor.putString("email", email);
        editor.putBoolean("sesion", session);

        editor.commit();
    }

    private void cargarPreferencias(EditText getUsuario, EditText getPass){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        getUsuario.setText(preferences.getString("user", "Example."));
        getUsuario.setText(preferences.getString("pass", "Example."));
    }


    private void validarUsuario(String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                String id_usuario, tipo, name, surname, email, password;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        if (jsonObject.getString("id_usuario").equals(getUsuario.getText().toString())) {
                            Intent inte = new Intent(MainActivity.this, Inicio.class);
                            startActivity(inte);
                            id_usuario=jsonObject.getString("id_usuario");
                            tipo=jsonObject.getString("tipo");
                            name=jsonObject.getString("name");
                            surname=jsonObject.getString("surname");
                            email=jsonObject.getString("email");
                            password=jsonObject.getString("password");
                            session=true;
                            guardarPreferencuas(id_usuario, password, tipo, name, surname, email, session);
                        }
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
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }


}
