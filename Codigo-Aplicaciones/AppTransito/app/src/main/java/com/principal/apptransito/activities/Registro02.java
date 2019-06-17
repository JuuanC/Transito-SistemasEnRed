package com.principal.apptransito.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Conductor;
import com.principal.apptransito.utilidades.Validaciones;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Registro02 extends AppCompatActivity {

    private Conductor conductor;
    private Validaciones validar;
    private RequestQueue queue;

    private String noCelular;
    private String password;
    private EditText nombreEdit;
    private EditText apellidoPatEdit;
    private EditText apellidoMatEdit;
    private TextView fechaNacimientoText;
    private EditText numeroLicenciaEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro02);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recibirDatos();
        queue = Volley.newRequestQueue(this);


        nombreEdit = findViewById(R.id.idRegistroNombre);
        apellidoPatEdit = findViewById(R.id.idRegistroApellidoPat);
        apellidoMatEdit = findViewById(R.id.idRegistroApellidoMat);
        fechaNacimientoText = findViewById(R.id.idRegistroFechaNacimiento);
        numeroLicenciaEdit = findViewById(R.id.idRegistroNumeroLicencia);

    }

    private void recibirDatos() {
        Bundle bundle = getIntent().getExtras();
        noCelular = bundle.getString("celular");
        password = bundle.getString("password");
    }

    public void seleccionarFecha(View view) {

        Calendar calendario = Calendar.getInstance();

        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH);
        int anio = calendario.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                fechaNacimientoText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }

        }, dia, mes, anio);
        datePickerDialog.show();
    }

    public void intentarRegistrar(View view) {

        validar = new Validaciones();

        String nombre = nombreEdit.getText().toString().trim();
        String apellidoPat = apellidoPatEdit.getText().toString().trim();
        String apellidoMat = apellidoMatEdit.getText().toString().trim();
        String fechaNacimiento = (String) fechaNacimientoText.getText();
        String numeroLicencia = numeroLicenciaEdit.getText().toString().trim();

        String resultado = validar.validarRegistro02(nombre, apellidoPat, apellidoMat, fechaNacimiento, numeroLicencia);

        if ("".equals(resultado)) {

            conductor = new Conductor();
            conductor.setTelefono(noCelular);
            conductor.setPassword(password);
            conductor.setNombre(nombre + " " + apellidoPat + " " + apellidoMat);
            conductor.setFechaNacimiento(fechaNacimiento);
            conductor.setNumeroLicencia(numeroLicencia);

            realizarConexionRegistro();


        } else {
            Toast datosInvalidosLogin = Toast.makeText(this, resultado, Toast.LENGTH_SHORT);
            datosInvalidosLogin.show();
        }

    }

    private void realizarConexionRegistro() {

        String url = "https://192.164.1.95:80/Conductor/RegistrarConductor/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                volverLogin();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast datosInvalidosLogin = Toast.makeText(getApplicationContext(), "Hubo un error en la conexión, inténtelo más tarde", Toast.LENGTH_SHORT);
                datosInvalidosLogin.show();
                Intent intento = new Intent(getApplicationContext(), Login.class);
                intento.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intento);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("telefono", conductor.getTelefono());
                params.put("password", conductor.getPassword());
                params.put("nombre", conductor.getNombre());
                params.put("fecha", conductor.getFechaNacimiento());
                params.put("licencia", conductor.getNumeroLicencia());
                return params;
            }
        };

        queue.add(stringRequest);

    }

    void volverLogin() {
        Toast registrado = Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG);
        registrado.show();

        Intent intento = new Intent(this, Login.class);
        intento.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intento);
    }

}
