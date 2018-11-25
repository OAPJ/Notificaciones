package com.example.ovall.practia_iii;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity implements View.OnClickListener {
    //Creaamos una cola de peticiones
    RequestQueue colaSolicitud;
    //Declaramos las variables
    Button btnLoginR, btnRegistrarseR;
    EditText etNombreR, etCorreoR, etClaveR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnLoginR = (Button) findViewById(R.id.btLoginR);
        btnRegistrarseR = (Button) findViewById(R.id.btRegistrarseR);
        etClaveR = (EditText) findViewById(R.id.etClaveR);
        etCorreoR = (EditText) findViewById(R.id.etCorreoR);
        etNombreR = (EditText) findViewById(R.id.etNombre);

        btnRegistrarseR.setOnClickListener(this);
        btnLoginR.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btRegistrarse){
            if(etNombreR.getText().toString().isEmpty() && etCorreoR.getText().toString().isEmpty() && etClaveR.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Faltan datos", Toast.LENGTH_SHORT).show();
            }else{
                compararCorreo();
            }
        }
        else {
            Intent intent = new Intent(Registro.this ,MainActivity.class);
            startActivity(intent);
        }
    }

    private void compararCorreo() {
        //Hacemos una solicitud por GET
        String URL = "http://192.168.0.16/getCorreo.php?correo="+etCorreoR.getText().toString();
        //String URL = "http://192.168.0.16/getCorreo.php?correo=juan@gmail.com";

        JsonObjectRequest getSolicitudUsuario = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject respuestaUsuario = response;
                        try {
                            int estado = respuestaUsuario.getInt("estado");

                            if(estado==1) {
                                //Toast.makeText(getApplicationContext(),"2", Toast.LENGTH_SHORT).show();
                                JSONArray arrayUsuarioJSON = respuestaUsuario.getJSONArray("usuario");

                                //quiero todos los objeos de  arrayJSON
                                for(int i=0; i<arrayUsuarioJSON.length();i++){
                                    //Obterner cada uno de ellos
                                    JSONObject empleadoJSOn = arrayUsuarioJSON.getJSONObject(i);
                                    String correo = empleadoJSOn.getString("correo");

                                    //Toast.makeText(getApplicationContext(),"A "+correo, Toast.LENGTH_SHORT).show();
                                    if(correo.equals("null")){
                                        Toast.makeText(getApplicationContext(),"No exixte", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),"El correo ya fue utilizado", Toast.LENGTH_SHORT).show();
                                    }
                                    //AL final lo metemos a un arreglo de objetos tipo usuario
                                }

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                            }

                        }catch (JSONException e){
                            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        );

        colaSolicitud.add(getSolicitudUsuario);
    }
}
