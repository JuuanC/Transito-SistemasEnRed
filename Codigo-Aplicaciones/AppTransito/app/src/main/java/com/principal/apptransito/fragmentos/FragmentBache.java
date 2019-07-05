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

public class FragmentBache extends Fragment implements View.OnClickListener {

    private Instancias misInstancias;
    private Reporte reporte;

    private RequestQueue queue;

    private Button registrar;
    private TextView latitudView;
    private TextView longitudView;
    private EditText descripcionEdit;

    public FragmentBache() {
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
        getActivity().setTitle("Bache en el camino");
        queue = Volley.newRequestQueue(getContext());

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        View view = inflater.inflate(R.layout.fragment_fragment_bache, container, false);

        registrar = view.findViewById(R.id.idRegistrarBache);
        descripcionEdit = view.findViewById(R.id.idBacheDescripcion);
        latitudView = view.findViewById(R.id.idBacheLatitud);
        longitudView = view.findViewById(R.id.idBacheLongitud);

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
        if (v.getId() == R.id.idRegistrarBache) {
            reporte = new Reporte();
            reporte.setDescripcion(descripcionEdit.getText().toString().trim());
            reporte.setLongitud(longitudView.getText().toString().trim());
            reporte.setLatitud(latitudView.getText().toString().trim());
            reporte.setTipoReporte("Bache en el camino");
            Validaciones validaciones = new Validaciones();
            String resultado = validaciones.validarSemaforo(reporte);
            if ("".equals(resultado)) {
                enviarBache();
            } else {
                Toast datosInvalidosLogin = Toast.makeText(getContext(), resultado, Toast.LENGTH_SHORT);
                datosInvalidosLogin.show();
            }
        }
    }

    private void enviarBache() {
        String url = "http://192.168.43.238:80/Reporte/Registro/";

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
                params.put("placasImplicado", "Bache");
                params.put("nombreAseguradoraImplicado", "Bache");
                params.put("marcaImplicado", "Bache");
                params.put("modeloImplicado", "Bache");
                params.put("colorImplicado", "Bache");
                params.put("nombreImplicado", "Bache");
                params.put("polizaSeguro", "Bache");
                params.put("latitud", reporte.getLatitud());
                params.put("longitud", reporte.getLongitud());
                params.put("telefono", misInstancias.getConductor().getTelefono());
                params.put("placa", "Bache");
                params.put("tipoReporte", reporte.getTipoReporte());
                params.put("estatus", "Bache");
                params.put("descripcion", reporte.getDescripcion());

                return params;
            }
        };

        queue.add(stringRequest);
    }
}
