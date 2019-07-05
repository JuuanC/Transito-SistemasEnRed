package com.principal.apptransito.activities;

import android.content.pm.ActivityInfo;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Vehiculo;
import com.principal.apptransito.utilidades.DialogoEliminacion;
import com.principal.apptransito.utilidades.DialogoModificacion;
import com.principal.apptransito.utilidades.Instancias;
import com.principal.apptransito.utilidades.Validaciones;

import java.util.HashMap;
import java.util.Map;

public class ModificarVehiculo extends AppCompatActivity implements View.OnClickListener, DialogoModificacion.OnDialogListener, DialogoEliminacion.OnDialogListener{

    private static final String TAG = "EjemploDialogo";
    private static final String MESSAGE = "MensajeDialogo";

    private Instancias misInstancias;
    private Vehiculo vehiculo;

    private RequestQueue queue;

    private Button modificar;
    private Button eliminar;
    private EditText placasEdit;
    private EditText marcaEdit;
    private EditText modeloEdit;
    private EditText anioEdit;
    private EditText colorEdit;
    private EditText aseguradoraEdit;
    private EditText polizaEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_vehiculo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        queue = Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            vehiculo = (Vehiculo) bundle.getSerializable("vehiculo");
            misInstancias = (Instancias) bundle.getSerializable("instancias");
        }

        placasEdit = findViewById(R.id.idPlacasModificar);
        marcaEdit = findViewById(R.id.idMarcaModificar);
        modeloEdit = findViewById(R.id.idModeloModificar);
        anioEdit = findViewById(R.id.idAnioModificar);
        colorEdit = findViewById(R.id.idColorModificar);
        aseguradoraEdit = findViewById(R.id.idAseguradoraModificar);
        polizaEdit = findViewById(R.id.idPolizaModificar);
        modificar = findViewById(R.id.idModificarVehiculo);
        eliminar = findViewById(R.id.idEliminarVehiculo);

        placasEdit.setText(vehiculo.getPlacas());
        marcaEdit.setText(vehiculo.getMarca());
        modeloEdit.setText(vehiculo.getModelo());
        anioEdit.setText(vehiculo.getAnio());
        colorEdit.setText(vehiculo.getColor());
        aseguradoraEdit.setText(vehiculo.getNumeroAseguradora());
        polizaEdit.setText(vehiculo.getNumeroPoliza());

        modificar.setOnClickListener(this);
        eliminar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.idModificarVehiculo) {
            String resultadoValidaciones = iniciarValidaciones();
            if ("".equals(resultadoValidaciones)) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(TAG);

                if (fragment == null) {
                    fragment = new DialogoModificacion();

                    Bundle bundle = new Bundle();
                    bundle.putString(MESSAGE, "¿Está seguro que desea modificar los datos del vehículo?");
                    fragment.setArguments(bundle);
                }

                fragment.show(getSupportFragmentManager(), TAG);
            } else {
                Toast datosInvalidos = Toast.makeText(getApplicationContext(), resultadoValidaciones, Toast.LENGTH_SHORT);
                datosInvalidos.show();
            }
        } else if (v.getId() == R.id.idEliminarVehiculo) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            DialogFragment fragment = (DialogFragment) fragmentManager.findFragmentByTag(TAG);

            if (fragment == null) {
                fragment = new DialogoEliminacion();

                Bundle bundle = new Bundle();
                bundle.putString(MESSAGE, "¿Está seguro que desea eliminar este vehículo?");
                fragment.setArguments(bundle);
            }

            fragment.show(getSupportFragmentManager(), TAG);
        }

    }

    private String iniciarValidaciones() {
        Validaciones validaciones = new Validaciones();
        Vehiculo nuevoVehiculo =  new Vehiculo();
        nuevoVehiculo.setAnio(anioEdit.getText().toString().trim());
        nuevoVehiculo.setCelular(misInstancias.getConductor().getTelefono());
        nuevoVehiculo.setColor(colorEdit.getText().toString().trim());
        nuevoVehiculo.setMarca(marcaEdit.getText().toString().trim());
        nuevoVehiculo.setModelo(modeloEdit.getText().toString().trim());
        nuevoVehiculo.setNumeroAseguradora(aseguradoraEdit.getText().toString().trim());
        nuevoVehiculo.setNumeroPoliza(polizaEdit.getText().toString().trim());
        nuevoVehiculo.setPlacas(placasEdit.getText().toString().trim());
        return validaciones.validarVehiculo(nuevoVehiculo);
    }

    @Override
    public void confirmacionModificar() {
        String url = "http://192.168.100.4:80/Vehiculo/Actualizar/";
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast datosInvalidos = Toast.makeText(getApplicationContext(), "modificacion confirmada", Toast.LENGTH_SHORT);
                        datosInvalidos.show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast datosInvalidos = Toast.makeText(getApplicationContext(), "Error al modificar", Toast.LENGTH_SHORT);
                        datosInvalidos.show();
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("placaVieja", vehiculo.getPlacas());
                params.put("placa", placasEdit.getText().toString().trim());
                params.put("marca", marcaEdit.getText().toString().trim());
                params.put("modelo", modeloEdit.getText().toString().trim());
                params.put("anio", anioEdit.getText().toString().trim());
                params.put("color", colorEdit.getText().toString().trim());
                params.put("nombreAseguradora", aseguradoraEdit.getText().toString().trim());
                params.put("numPoliza", polizaEdit.getText().toString().trim());
                params.put("telefono" , misInstancias.getConductor().getTelefono());

                return params;
            }

        };

        queue.add(putRequest);
    }

    @Override
    public void cancelarModificacion() {
        Toast datosInvalidos = Toast.makeText(this, "modificacion cancelada", Toast.LENGTH_SHORT);
        datosInvalidos.show();
    }

    @Override
    public void confirmacionEliminacion() {
        String url = "http://192.168.43.238:80/Vehiculo/Eliminar/?placa="+placasEdit.getText().toString().trim();
        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast datosInvalidos = Toast.makeText(getApplicationContext(), "eliminacion confirmada", Toast.LENGTH_SHORT);
                        datosInvalidos.show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast datosInvalidos = Toast.makeText(getApplicationContext(), "Error en eliminación", Toast.LENGTH_SHORT);
                        datosInvalidos.show();

                    }
                }
        );

        queue.add(dr);
    }

    @Override
    public void cancelarEliminacion() {
        // No hacer nada.
        Toast datosInvalidos = Toast.makeText(this, "eliminacion cancelada", Toast.LENGTH_SHORT);
        datosInvalidos.show();
    }
}
