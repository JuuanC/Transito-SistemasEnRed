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

import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Vehiculo;
import com.principal.apptransito.utilidades.DialogoConfirmacion;
import com.principal.apptransito.utilidades.DialogoEliminacion;
import com.principal.apptransito.utilidades.DialogoModificacion;
import com.principal.apptransito.utilidades.Instancias;
import com.principal.apptransito.utilidades.Validaciones;

public class ModificarVehiculo extends AppCompatActivity implements View.OnClickListener, DialogoModificacion.OnDialogListener, DialogoEliminacion.OnDialogListener{

    private static final String TAG = "EjemploDialogo";
    private static final String MESSAGE = "MensajeDialogo";

    private Instancias misInstancias;
    private Vehiculo vehiculo;

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
        nuevoVehiculo.setCelular(misInstancias.getConductor().getNoCelular());
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
        // TODO mandar al servidor el carro modificado y después meterlo a la lista de carros actuales.
        Toast datosInvalidos = Toast.makeText(this, "modificacion confirmada", Toast.LENGTH_SHORT);
        datosInvalidos.show();
    }

    @Override
    public void cancelarModificacion() {
        // No hacer nada.
        Toast datosInvalidos = Toast.makeText(this, "modificacion cancelada", Toast.LENGTH_SHORT);
        datosInvalidos.show();
    }

    @Override
    public void confirmacionEliminacion() {
        // TODO mandar al servidor una peticion con las placas del carro.
        Toast datosInvalidos = Toast.makeText(this, "eliminacion confirmada", Toast.LENGTH_SHORT);
        datosInvalidos.show();
    }

    @Override
    public void cancelarEliminacion() {
        // No hacer nada.
        Toast datosInvalidos = Toast.makeText(this, "eliminacion cancelada", Toast.LENGTH_SHORT);
        datosInvalidos.show();
    }
}
