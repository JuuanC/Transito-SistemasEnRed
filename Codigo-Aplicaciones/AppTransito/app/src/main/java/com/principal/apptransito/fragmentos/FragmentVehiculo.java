package com.principal.apptransito.fragmentos;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.principal.apptransito.utilidades.Instancias;
import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Vehiculo;
import com.principal.apptransito.utilidades.Validaciones;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVehiculo extends Fragment implements View.OnClickListener {

    private Instancias misInstancias;
    private Vehiculo vehiculo;

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
                // TODO (3) Realizar conexión al servidor para registrar vehículo.

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

}
