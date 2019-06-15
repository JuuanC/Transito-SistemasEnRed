package com.principal.apptransito.fragmentos;


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
import android.widget.Toast;

import com.principal.apptransito.utilidades.Instancias;
import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Conductor;
import com.principal.apptransito.utilidades.DialogoConfirmacion;
import com.principal.apptransito.utilidades.Validaciones;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDatos
        extends Fragment
        implements
            View.OnClickListener,
            DialogoConfirmacion.OnInputSelected {

    private static final String TAG = "MainFragment";
    private static final String MESSAGE = "MensajeDelDialogo";

    private Instancias misInstancias;

    private Button fechaBoton;
    private Button modificarBoton;
    private TextView fechaView;
    private EditText celularEdit;
    private EditText password1Edit;
    private EditText password2Edit;
    private EditText nombreEdit;
    private EditText noLicenciaEdit;
    private String fecha;
    private String celular;
    private String password1;
    private String password2;
    private String nombre;
    private String licencia;

    public FragmentDatos() {
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

        getActivity().setTitle("Mis Datos");

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
                crearCalendario(v);
                break;

            case R.id.idModificarDatos:
                fecha = fechaView.getText().toString().trim();
                celular = celularEdit.getText().toString().trim();
                password1 = password1Edit.getText().toString().trim();
                password2 = password2Edit.getText().toString().trim();
                nombre = nombreEdit.getText().toString().trim();
                licencia = noLicenciaEdit.getText().toString().trim();

                Conductor conductorValidar = new Conductor();
                conductorValidar.setNombre(nombre);
                conductorValidar.setNoCelular(celular);
                conductorValidar.setFechaNacimiento(fecha);
                conductorValidar.setPassword(password1);
                conductorValidar.setNumeroLicencia(licencia);

                Validaciones validaciones = new Validaciones();
                String resultado = validaciones.validarActualizarDatos(conductorValidar, password2);

                if ("".equals(resultado)) {

                    Bundle bundle = new Bundle();
                    bundle.putString(MESSAGE, "¿Está seguro que desea modificar sus datos?");
                    DialogoConfirmacion dialogoConfirmacion = new DialogoConfirmacion();
                    dialogoConfirmacion.setArguments(bundle);
                    dialogoConfirmacion.setTargetFragment(FragmentDatos.this, 1);
                    dialogoConfirmacion.show(getFragmentManager(), "MyCustomDialog");

                } else {
                    Toast datosInvalidos = Toast.makeText(getActivity(), resultado, Toast.LENGTH_SHORT);
                    datosInvalidos.show();
                }

                break;

            default:
                // No hacer nada.
                break;
        }
    }

    private void crearCalendario(View v) {
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
    }

    private void llenarDatos() {
        fechaView.setText(misInstancias.getConductor().getFechaNacimiento());
        celularEdit.setText(misInstancias.getConductor().getNoCelular());
        password1Edit.setText(misInstancias.getConductor().getPassword());
        password2Edit.setText(misInstancias.getConductor().getPassword());
        nombreEdit.setText(misInstancias.getConductor().getNombre());
        noLicenciaEdit.setText(misInstancias.getConductor().getNumeroLicencia());
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void confirmarMensaje() {
        if (realizarConexionActualizarDatos()) {

            // TODO (2) Realizar actualización de datos, y obtener el conductor actualizado del servidor y almacenarlo en mis instancias

            // Simulación de nuevos datos.
            misInstancias.getConductor().setPassword(password1);
            misInstancias.getConductor().setNumeroLicencia(licencia);
            misInstancias.getConductor().setNoCelular(celular);
            misInstancias.getConductor().setFechaNacimiento(fecha);
            misInstancias.getConductor().setNombre(nombre);
        }
    }

    private boolean realizarConexionActualizarDatos() {
        // Lodigca para conectarse al servidor
        return true;
    }

}
