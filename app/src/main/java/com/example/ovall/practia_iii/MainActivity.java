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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Creaamos una cola de peticiones
    RequestQueue colaSolicitud;
    //Declaramos las variables
    Button btnLogin, btnRegistro;
    EditText etCorreo, etClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colaSolicitud = Volley.newRequestQueue(this);
        btnLogin = (Button) findViewById(R.id.btLogin);
        btnRegistro = (Button) findViewById(R.id.btRegistrarse);
        etClave = (EditText) findViewById(R.id.etClave);
        etCorreo = (EditText) findViewById(R.id.etCorreo);

        btnRegistro.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btLogin){
            consultarUsuario();
        }
        if(v.getId() == R.id.btRegistrarse){
            Intent intent = new Intent(MainActivity.this, Registro.class);
            startActivity(intent);
        }
    }

    public void consultarUsuario() {
        //Hacemos una solicitud por GET
        //String URL = "http://192.168.0.16/getUsuario.php?correo="+etCorreo.getText().toString()+"&clave="+etClave.getText().toString();
        String URL = "http://192.168.43.55/getUsuario.php?correo="+etCorreo.getText().toString()+"&clave="+etClave.getText().toString();
        //String URL = "http://192.168.0.16/getUsuario.php?correo=juan@gmail.com&clave=juan98";

        //Toast.makeText(getApplicationContext(),"URL", Toast.LENGTH_SHORT).show();
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
                            String mensaje = respuestaUsuario.getString("mensaje");

                            if(estado==1) {
                                //Toast.makeText(getApplicationContext(),"2", Toast.LENGTH_SHORT).show();
                                JSONArray arrayUsuarioJSON = respuestaUsuario.getJSONArray("usuario");

                                //quiero todos los objeos de  arrayJSON
                                for(int i=0; i<arrayUsuarioJSON.length();i++){
                                    //Obterner cada uno de ellos
                                    JSONObject empleadoJSOn = arrayUsuarioJSON.getJSONObject(i);
                                    String correo = empleadoJSOn.getString("correo");
                                    String clave = empleadoJSOn.getString("clave");

                                    //Toast.makeText(getApplicationContext(),"A "+correo, Toast.LENGTH_SHORT).show();
                                    if(correo.equals("null") || clave.equals("null")){
                                        Toast.makeText(getApplicationContext(),"Datos Incorrectos", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Intent intent = new Intent(MainActivity.this, Bienvenido.class);
                                        startActivity(intent);
                                    }
                                    //AL final lo metemos a un arreglo de objetos tipo usuario
                                }

                            }
                            else{
                                //Toast.makeText(getApplicationContext(),"3", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
                            }

                        }catch (JSONException e){
                            //Toast.makeText(getApplicationContext(),"4", Toast.LENGTH_SHORT).show();
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
