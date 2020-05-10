package com.example.nihilist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jorge
 */
public class Registrar extends AppCompatActivity {

    EditText dni;
    EditText email;
    EditText nombre;
    EditText apellido;
    Spinner tipo;
    EditText password;
    Button ayuda;
    ImageButton registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        registrar = (ImageButton) findViewById(R.id.registrar);
        ayuda = (Button) findViewById(R.id.ayuda);
        dni = (EditText) findViewById(R.id.getDNI);
        email = (EditText) findViewById(R.id.getEmail);
        nombre = (EditText) findViewById(R.id.getNombre);
        apellido = (EditText) findViewById(R.id.getApellido);
        password = (EditText) findViewById(R.id.getPass);

        // FUNCION SPINNER
        tipo = (Spinner) findViewById(R.id.getTipo);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.opciones, android.R.layout.simple_spinner_item);
        tipo.setAdapter(adapter);

        // FUNCIONES VISUALES
        dni.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dni.setText("");
                dni.setTextColor(getResources().getColor(R.color.colorAccent));
                return false;
            }
        });

        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                email.setText("");
                email.setTextColor(getResources().getColor(R.color.colorAccent));
                return false;
            }
        });

        nombre.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                nombre.setText("");
                nombre.setTextColor(getResources().getColor(R.color.colorAccent));
                return false;
            }
        });

        apellido.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                apellido.setText("");
                apellido.setTextColor(getResources().getColor(R.color.colorAccent));
                return false;
            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                password.setText("");
                password.setTextColor(getResources().getColor(R.color.colorAccent));
                return false;
            }
        });

        // LISTENER "registrar" PARA REGISTRAR USUARIO
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarDNI(dni.getText().toString())==false){
                    dni.setError("Este campo no cumple las condiciones [0-9]&[aA-zZ]");
                    Toast.makeText(getApplicationContext(), "Este campo no cumple las condiciones [0-9]&[aA-zZ]", Toast.LENGTH_SHORT).show();
                }else{
                    if (validarEmail(email.getText().toString())==false){
                        email.setError("Este campo no cumple las condiciones [A-Za-z0-9][@][a-z].[a-z]");
                    }else{
                        if (nombre.getText().toString().equals("")||nombre.getText().toString().equals("Example.")){
                            nombre.setError("Este campo no cumple las condiciones [A-Za-z]");
                        }else{
                            if (apellido.getText().toString().equals("")||apellido.getText().toString().equals("Example.")){
                                apellido.setError("Este campo no cumple las condiciones [A-Za-z]");
                            }else{
                                ejecutarServicioRegistro("https://sqliteludens.000webhostapp.com/connect/registrar.php");
                                Toast.makeText(getApplicationContext(), "Registrado con exito", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * METODO PARA EL REGISTRO DE UN NUEVO USUARIO
     * @param URL
     */
    private void ejecutarServicioRegistro(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operacion Exitosa", Toast.LENGTH_SHORT).show();

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
                parametros.put("email", email.getText().toString());
                parametros.put("id_usuario", dni.getText().toString());
                parametros.put("name", nombre.getText().toString());
                parametros.put("password", password.getText().toString());
                parametros.put("surname", apellido.getText().toString());
                parametros.put("tipo", tipo.getSelectedItem().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /**
     * METODO PARA VALIDAR EL DNI
     * @param dni
     * @return
     */
    public boolean validarDNI(String dni){
        String letraMayuscula="";
        if (dni.length()!=9 || Character.isLetter(dni.charAt(8))==false){
            return false;
        }

        letraMayuscula= (dni.substring(8).toUpperCase());
        if (soloNumeros(dni)==true && letraDNI(dni).equals(letraMayuscula)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * COMPLEMENTO DE validarDNI([String])
     * @param dni
     * @return
     */
    private boolean soloNumeros(String dni){
        int i, j=0;
        String numero="";
        String miDNI="";
        String [] unoNueve={"0","1","2","3","4","5","6","7","8","9"};
        for (i=0; i<dni.length()-1; i++){
            numero = dni.substring(i, i+1);
            for (j=0; j<unoNueve.length; j++){
                if(numero.equals(unoNueve[j])){
                    miDNI+=unoNueve[j];
                }
            }
        }
        if (miDNI.length()!=8){
            return false;
        } else{
            return true;
        }
    }

    /**
     * COMPLEMENTO DE validarDNI([String])
     * @param dni
     * @return
     */
    private String letraDNI(String dni){
        int miDNI = Integer.parseInt(dni.substring(0,8));
        int resto=0;
        String miLetra = "";
        String [] asignacionLetra = {"T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E"};
        resto= miDNI%23;
        miLetra=asignacionLetra[resto];
        return miLetra;
    }

    /**
     * METODO PARA VALIDAR EL EMAIL
     * @param email
     * @return
     */
    public boolean validarEmail(String email){

        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);

        if (mather.find() == true) {
            return true;
        } else {
            return false;
        }
    }
}
