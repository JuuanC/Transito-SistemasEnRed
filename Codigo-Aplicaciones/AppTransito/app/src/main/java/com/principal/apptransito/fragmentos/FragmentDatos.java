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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.principal.apptransito.utilidades.Instancias;
import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Conductor;
import com.principal.apptransito.utilidades.DialogoConfirmacion;
import com.principal.apptransito.utilidades.Validaciones;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FragmentDatos
        extends Fragment
        implements
            View.OnClickListener,
            DialogoConfirmacion.OnInputSelected {

    private static final String MESSAGE = "MensajeDelDialogo";

    private Instancias misInstancias;
    private RequestQueue queue;

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

        queue = Volley.newRequestQueue(getContext());
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
                conductorValidar.setTelefono(celular);
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
        celularEdit.setText(misInstancias.getConductor().getTelefono());
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
        String url = "http://192.168.100.4:80/Conductor/Actualizar/";

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                misInstancias.getConductor().setPassword(password1);
                misInstancias.getConductor().setNumeroLicencia(licencia);
                misInstancias.getConductor().setTelefono(celular);
                misInstancias.getConductor().setFechaNacimiento(fecha);
                misInstancias.getConductor().setNombre(nombre);

                Toast datosInvalidosLogin = Toast.makeText(getContext(), "Actualización con éxito.", Toast.LENGTH_SHORT);
                datosInvalidosLogin.show();

                cargarMainActivity();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast datosInvalidosLogin = Toast.makeText(getContext(), "Hubo un error en la conexión, inténtelo más tarde.", Toast.LENGTH_SHORT);
                datosInvalidosLogin.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nombre", nombreEdit.getText().toString().trim());
                params.put("fechaNacimiento", fechaView.getText().toString().trim());
                params.put("numLicencia", noLicenciaEdit.getText().toString().trim());
                params.put("telefono", celularEdit.getText().toString().trim());
                params.put("contrasenia", password1Edit.getText().toString().trim());

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
