package com.principal.apptransito.fragmentos;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.principal.apptransito.activities.Login;
import com.principal.apptransito.utilidades.Instancias;
import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Vehiculo;
import com.principal.apptransito.utilidades.Validaciones;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVehiculo extends Fragment implements View.OnClickListener {

    private Instancias misInstancias;
    private Vehiculo vehiculo;
    private RequestQueue queue;

    private Button registrar;
    private EditText placasEdit;
    private EditText marcaEdit;
    private EditText modeloEdit;
    private EditText anioEdit;
    private EditText colorEdit;
    private EditText nombreAseguradoraEdit;
    private EditText numeroPolizaEdit;

    public FragmentVehiculo() {
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

        getActivity().setTitle("Registrar Vehiculo");
        View view = inflater.inflate(R.layout.fragment_vehiculo, container, false);

        queue = Volley.newRequestQueue(getContext());

        registrar = view.findViewById(R.id.idRegistrarVehiculo);
        placasEdit = view.findViewById(R.id.idPlacasVehiculo);
        marcaEdit = view.findViewById(R.id.idMarcaVehiculo);
        modeloEdit = view.findViewById(R.id.idModeloVehiculo);
        anioEdit = view.findViewById(R.id.idAnioVehiculo);
        colorEdit = view.findViewById(R.id.idColorVehiculo);
        nombreAseguradoraEdit = view.findViewById(R.id.idAseguradoraVehiculo);
        numeroPolizaEdit = view.findViewById(R.id.idPolizaVehiculo);

        registrar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.idRegistrarVehiculo) {
            String resultadoValidaciones = iniciarValidaciones();
            if ("".equals(resultadoValidaciones)) {

                registrarVehiculos();

                misInstancias.getVehiculos().add(vehiculo);
            } else {
                Toast datosInvalidosVehiculo = Toast.makeText(getActivity(), resultadoValidaciones, Toast.LENGTH_SHORT);
                datosInvalidosVehiculo.show();
            }
        }

    }

    private String iniciarValidaciones() {

        vehiculo =  new Vehiculo();
        vehiculo.setAnio(anioEdit.getText().toString().trim());
        vehiculo.setCelular(misInstancias.getConductor().getTelefono());
        vehiculo.setColor(colorEdit.getText().toString().trim());
        vehiculo.setMarca(marcaEdit.getText().toString().trim());
        vehiculo.setModelo(modeloEdit.getText().toString().trim());
        vehiculo.setNumeroAseguradora(nombreAseguradoraEdit.getText().toString().trim());
        vehiculo.setNumeroPoliza(numeroPolizaEdit.getText().toString().trim());
        vehiculo.setPlacas(placasEdit.getText().toString().trim());

        Validaciones validaciones = new Validaciones();

        return validaciones.validarVehiculo(vehiculo);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void registrarVehiculos() {
        String url = "http://192.168.100.4:80/Vehiculo/Registro/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast datosInvalidosLogin = Toast.makeText(getContext(), "Vehículo registrado con éxito", Toast.LENGTH_SHORT);
                datosInvalidosLogin.show();
                cargarMainActivity();
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
                params.put("placa", vehiculo.getPlacas());
                params.put("marca", vehiculo.getMarca());
                params.put("modelo", vehiculo.getModelo());
                params.put("anio", vehiculo.getAnio());
                params.put("color", vehiculo.getColor());
                params.put("nombreAseguradora", vehiculo.getNumeroAseguradora());
                params.put("numPoliza", vehiculo.getNumeroPoliza());
                params.put("telefono", vehiculo.getCelular());

                return params;
            }
        };

        queue.add(stringRequest);
    }

    private void cargarMainActivity() {
        getActivity().finish();
        startActivity(getActivity().getIntent());
    }

}
