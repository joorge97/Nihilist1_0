package com.example.nihilist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registrar extends AppCompatActivity {

    EditText dni, email, nombre, apellido, tipo;
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


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarDNI(dni.getText().toString())==false){
                    dni.setError("Este campo no cumple las condiciones [0-9]&[aA-zZ]");
                }
                if (validarEmail(email.getText().toString())==false){
                    email.setError("Este campo no cumple las condiciones [A-Za-z0-9][@][a-z].[a-z]");
                }
                if (nombre.getText().toString().equals("")||nombre.getText().toString().equals("Example.")){
                    nombre.setError("Este campo no cumple las condiciones [A-Za-z]");
                }
                if (apellido.getText().toString().equals("")||apellido.getText().toString().equals("Example.")){
                    apellido.setError("Este campo no cumple las condiciones [A-Za-z]");
                }
                if (validarTipo(tipo.getText().toString())==false){
                    tipo.setError("Este campo solo puede ser alumno, o profesor");
                }
            }
        });
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
     * VALIDA EL TIPO DE USUARIO
     * @param tipo
     * @return
     */
    public boolean validarTipo(String tipo){
        if (tipo.toUpperCase()!="ALUMNO" || tipo.toUpperCase()!="PROFESOR"){
            return false;
        }else {
            return true;
        }
    }
}
