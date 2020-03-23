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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registrar extends AppCompatActivity {

    EditText dni, email, nombre, apellido, tipo, password;
    Button registrar, ayuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        registrar = (Button) findViewById(R.id.registrar);
        ayuda = (Button) findViewById(R.id.ayuda);
        dni = (EditText) findViewById(R.id.getDNI);
        email = (EditText) findViewById(R.id.getEmail);
        nombre = (EditText) findViewById(R.id.getNombre);
        apellido = (EditText) findViewById(R.id.getApellido);
        tipo = (EditText) findViewById(R.id.getTipo);
        password = (EditText) findViewById(R.id.getPass);



        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarDNI(dni.getText().toString())==false){
                    dni.setError("Este campo no cumple las condiciones [0-9]&[aA-zZ]");
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
                                ejecutarServicioRegistro("https://sqliteludens.000webhostapp.com/connect/insertar.php");
                                guardarPreferencuas(nombre, password);
                                Intent i = new Intent(Registrar.this, Inicio.class);
                                startActivity(i);
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
                parametros.put("tipo", tipo.getText().toString());
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
}
