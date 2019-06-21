package com.principal.apptransito.fragmentos;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.principal.apptransito.objetos.Reporte;
import com.principal.apptransito.utilidades.Instancias;
import com.principal.apptransito.utilidades.Validaciones;

import java.util.HashMap;
import java.util.Map;

public class FragmentSemaforo extends Fragment implements View.OnClickListener {

    private Instancias misInstancias;
    private Reporte reporte;

    private RequestQueue queue;

    private Button registrar;
    private TextView latitudView;
    private TextView longitudView;
    private EditText descripcionEdit;

    public FragmentSemaforo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            misInstancias = (Instancias) getArguments().getSerializable("conductor");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Semaforo descompuesto");
        queue = Volley.newRequestQueue(getContext());

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        View view = inflater.inflate(R.layout.fragment_fragment_semaforo, container, false);

        registrar = view.findViewById(R.id.idRegistrarSemaforo);
        descripcionEdit = view.findViewById(R.id.idSemaforoDescripcion);
        latitudView = view.findViewById(R.id.idSemaforoLatitud);
        longitudView = view.findViewById(R.id.idSemaforoLongitud);

        registrar.setOnClickListener(this);

        consultarGps();

        return view;
    }

    private synchronized void consultarGps() {

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                latitudView.setText("" + location.getLatitude());
                longitudView.setText("" + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {   }

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.idRegistrarSemaforo) {
            reporte = new Reporte();
            reporte.setDescripcion(descripcionEdit.getText().toString().trim());
            reporte.setLongitud(longitudView.getText().toString().trim());
            reporte.setLatitud(latitudView.getText().toString().trim());
            reporte.setTipoReporte("Semaforo descompuesto");
            Validaciones validaciones = new Validaciones();
            String resultado = validaciones.validarSemaforo(reporte);
            if ("".equals(resultado)) {
                enviarSemaforo();
            } else {
                Toast datosInvalidosLogin = Toast.makeText(getContext(), resultado, Toast.LENGTH_SHORT);
                datosInvalidosLogin.show();
            }
        }
    }

    private void enviarSemaforo() {
        String url = "http://192.168.100.4:80/Reporte/Registro/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                misInstancias.getListaReporte().add(reporte);
                Toast datosInvalidosLogin = Toast.makeText(getContext(), "Reporte enviado con éxito", Toast.LENGTH_SHORT);
                datosInvalidosLogin.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast datosInvalidosLogin = Toast.makeText(getContext(), "Hubo un error en la conexión, inténtelo más tarde", Toast.LENGTH_SHORT);
                datosInvalidosLogin.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("placasImplicado", "Semáforo");
                params.put("nombreAseguradoraImplicado", "Semáforo");
                params.put("marcaImplicado", "Semáforo");
                params.put("modeloImplicado", "Semáforo");
                params.put("colorImplicado", "Semáforo");
                params.put("nombreImplicado", "Semáforo");
                params.put("polizaSeguro", "Semáforo");
                params.put("latitud", reporte.getLatitud());
                params.put("longitud", reporte.getLongitud());
                params.put("telefono", misInstancias.getConductor().getTelefono());
                params.put("placa", "Semáforo");
                params.put("tipoReporte", reporte.getTipoReporte());
                params.put("estatus", "Semáforo");
                params.put("descripcion", reporte.getDescripcion());

                return params;
            }
        };

        queue.add(stringRequest);
    }
}
