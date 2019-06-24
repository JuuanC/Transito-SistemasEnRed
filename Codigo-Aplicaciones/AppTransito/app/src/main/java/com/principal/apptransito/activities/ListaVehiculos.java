package com.principal.apptransito.activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Vehiculo;
import com.principal.apptransito.utilidades.Adapter;
import com.principal.apptransito.utilidades.Instancias;

import java.util.HashMap;
import java.util.Map;

public class ListaVehiculos extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Adapter adapter;
    private Instancias misInstancias;
    private ListView lista;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vehiculos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        queue = Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            misInstancias = (Instancias) bundle.getSerializable("conductor");
        }

        adapter = new Adapter(this, misInstancias.getVehiculos());
        lista = findViewById(R.id.idListaVehiculosReporte);

        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Vehiculo vehiculoSeleccionado = (Vehiculo) adapter.getItem(position);
        misInstancias.getReporte().setPlacas(vehiculoSeleccionado.getPlacas());
        realizarConexionRegistro();
    }

    private void realizarConexionRegistro() {

        String url = "http://192.168.100.4:80/Reporte/Registro/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast datosInvalidosLogin = Toast.makeText(getApplicationContext(), "Reporte enviado con éxito", Toast.LENGTH_SHORT);
                datosInvalidosLogin.show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast datosInvalidosLogin = Toast.makeText(getApplicationContext(), "Hubo un error en la conexión, inténtelo más tarde", Toast.LENGTH_SHORT);
                datosInvalidosLogin.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                String[] imagen = new String[8];
                imagen[0] = "";
                imagen[1] = "";
                imagen[2] = "";
                imagen[3] = "";
                imagen[4] = "";
                imagen[5] = "";
                imagen[6] = "";
                imagen[7] = "";

                for (int i = 0; i < misInstancias.getReporte().getImagenes().size() ; i++) {
                     byte[] byteArray = misInstancias.getReporte().getImagenes().get(i).getImagenEnBytes();
                    imagen[i] = Base64.encodeToString(byteArray, Base64.DEFAULT);
                }

                params.put("placasImplicado", misInstancias.getReporte().getPlacasImplicado());
                params.put("nombreAseguradoraImplicado", "BANCOMER");
                params.put("marcaImplicado", misInstancias.getReporte().getMarcaImplicado());
                params.put("modeloImplicado", misInstancias.getReporte().getModeloImplicado());
                params.put("colorImplicado", misInstancias.getReporte().getColorImplicado());
                params.put("nombreImplicado", misInstancias.getReporte().getNombreImplicado());
                params.put("polizaSeguro", misInstancias.getReporte().getPolizaImplicado());
                params.put("latitud", misInstancias.getReporte().getLatitud());
                params.put("longitud", misInstancias.getReporte().getLongitud());
                params.put("telefono", misInstancias.getConductor().getTelefono());
                params.put("placa", misInstancias.getReporte().getPlacas());
                params.put("tipoReporte", misInstancias.getReporte().getTipoReporte());
                params.put("estatus", misInstancias.getReporte().getEstatus());
                params.put("descripcion", "accidente");
                params.put("imagen1", imagen[0]);
                params.put("imagen2", imagen[1]);
                params.put("imagen3", imagen[2]);
                params.put("imagen4", imagen[3]);
                params.put("imagen5", imagen[4]);
                params.put("imagen6", imagen[5]);
                params.put("imagen7", imagen[6]);
                params.put("imagen8", imagen[7]);

                return params;

            }
        };

        queue.add(stringRequest);

    }

}
