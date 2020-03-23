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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jorge
 */
public class MainActivity extends AppCompatActivity {

    Button registrar, iniciar;
    EditText getUsuario, getPass;

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
                validarUsuario("https://sqliteludens.000webhostapp.com/connect/validar_usuario.php");
                guardarPreferencuas(getUsuario, getPass);
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
     * @param getUsuario
     * @param getPass
     */
    private void guardarPreferencuas(EditText getUsuario, EditText getPass) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String usuario=getUsuario.getText().toString();
        String contras=getPass.getText().toString();

        SharedPreferences.Editor editor =  preferences.edit();

        editor.putString("user", usuario);
        editor.putString("pass", contras);

        editor.commit();
    }

    /**
     * ACCESO A EL USUARIO
     * @param URL
     */
    private void validarUsuario (String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    Intent i = new Intent(getApplicationContext(), Inicio.class);
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<String, String>();
                parametros.put("usuario", getUsuario.getText().toString());
                parametros.put("password", getPass.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
