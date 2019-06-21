package com.principal.apptransito.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Conductor;
import com.principal.apptransito.utilidades.Instancias;
import com.principal.apptransito.utilidades.Validaciones;

import org.json.JSONException;
import org.json.JSONObject;


public class Login extends AppCompatActivity {

    private Instancias misInstancias;
    private RequestQueue queue;

    final String TAG = Conductor.class.getSimpleName();

    private Validaciones validar;
    private EditText noCelularEdit;
    private EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        queue = Volley.newRequestQueue(this);

        misInstancias = new Instancias();

        noCelularEdit = findViewById(R.id.idCelular);
        passwordEdit = findViewById(R.id.idPassword);

    }

    public void intentarEntrar(View view) {

        validar = new Validaciones();

        String noCelular = noCelularEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        String resultado = validar.validarLogin(noCelular, password);

        if ("".equals(resultado)) {

            cargarWebService(noCelular, password);

        } else {
           Toast datosInvalidosLogin = Toast.makeText(this, resultado, Toast.LENGTH_SHORT);
           datosInvalidosLogin.show();
        }
    }

    public void desplegarRegistrarse(View view) {
        Intent intento = new Intent(view.getContext(), Registro01.class);
        startActivityForResult(intento, 0);
    }

    private void cargarWebService(String celular, String password) {
        final Conductor conductor = new Conductor();
        String url = "http://192.168.100.4:80/Conductor/ValidarUsuario/?telefono="+celular+"&password="+password;
        //String url = "https://api.myjson.com/bins/muotx";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    conductor.setTelefono(response.getString("telefono"));
                    conductor.setPassword(response.getString("contrasenia"));
                    conductor.setNumeroLicencia(response.getString("numLicencia"));
                    conductor.setFechaNacimiento(response.getString("fechaNacimiento"));
                    conductor.setNombre(response.getString("nombre"));

                    misInstancias.setConductor(conductor);

                    Intent intento = new Intent(getApplicationContext(), MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("conductor", misInstancias);
                    intento.putExtras(bundle);
                    startActivity(intento);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast datosInvalidosLogin = Toast.makeText(getApplicationContext(), "Hubo un error en la conexión, inténtelo más tarde", Toast.LENGTH_SHORT);
                datosInvalidosLogin.show();
            }
        });

        queue.add(getRequest);

    }

}
