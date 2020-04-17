package com.example.nihilist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
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

    Button registrar, iniciar, ayuda;
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
        ayuda = (Button) findViewById(R.id.btnAyuda);

        getUsuario.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getUsuario.setText("");
                return false;
            }
        });
        getPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getPass.setText("");
                return false;
            }
        });

        // LOGEAR
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarUsuario("https://sqliteludens.000webhostapp.com/connect/validar_usuarios.php?id_usuario="
                        +getUsuario.getText().toString()+"&&password="+getPass.getText().toString()+"");
                iniciar.setBackgroundColor(R.drawable.boton_redondo_secundario);
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Registrar.class);
                startActivity(i);
                registrar.setBackgroundColor(R.drawable.boton_redondo_secundario);
            }
        });

        // BOTON DE AYUDA
        ayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                alerta.setMessage("Introduce tu usuario y contraseña")
                        .setCancelable(true);
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Ayuda");
                titulo.show();
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

    /**
     * CARGA EL USUARIO Y LA PASSWORD POR DEFECTO
     * @param getUsuario
     * @param getPass
     */
    private void cargarPreferencias(EditText getUsuario, EditText getPass){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        getUsuario.setText(preferences.getString("user", "Example."));
        getUsuario.setText(preferences.getString("pass", "Example."));
    }


    /**
     * METODO PARA VALIDAR EL ACCESO DEL USUARIO
     * @param URL
     */
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
