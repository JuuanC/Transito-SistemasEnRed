package com.principal.apptransito;


import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDatos extends Fragment implements View.OnClickListener {

    private Conductor conductor;

    private Button fechaBoton;
    private Button modificarBoton;
    private TextView fechaView;
    private EditText celularEdit;
    private EditText password1Edit;
    private EditText password2Edit;
    private EditText nombreEdit;
    private EditText noLicenciaEdit;

    public FragmentDatos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            conductor = (Conductor) getArguments().getSerializable("conductor");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datos, container, false);

        fechaBoton = view.findViewById(R.id.idSeleccionarFecha);
        modificarBoton = view.findViewById(R.id.idModificarDatos);
        fechaView = view.findViewById(R.id.idFechaDatos);
        celularEdit = view.findViewById(R.id.idCelularDatos);
        password1Edit = view.findViewById(R.id.idPasswordDatos);
        password2Edit = view.findViewById(R.id.idPassword2Datos);
        nombreEdit = view.findViewById(R.id.idNombreDatos);
        noLicenciaEdit = view.findViewById(R.id.idNumeroLicenciaDatos);

        fechaBoton.setOnClickListener(this);
        modificarBoton.setOnClickListener(this);

        llenarDatos();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.idSeleccionarFecha:
                Calendar calendario = Calendar.getInstance();

                int dia = calendario.get(Calendar.DAY_OF_MONTH);
                int mes = calendario.get(Calendar.MONTH);
                int anio = calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        fechaView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }

                }, dia, mes, anio);
                datePickerDialog.show();
                break;

            case R.id.idModificarDatos:
                break;

            default:
                // No hacer nada.
                break;
        }
    }

    private void llenarDatos() {
        fechaView.setText(conductor.getFechaNacimiento());
        celularEdit.setText(conductor.getNoCelular());
        password1Edit.setText(conductor.getPassword());
        password2Edit.setText(conductor.getPassword());
        nombreEdit.setText(conductor.getNombre());
        noLicenciaEdit.setText(conductor.getNumeroLicencia());
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
