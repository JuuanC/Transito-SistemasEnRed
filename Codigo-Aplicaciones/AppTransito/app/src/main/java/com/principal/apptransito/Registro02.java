package com.principal.apptransito;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.Calendar;

public class Registro02 extends AppCompatActivity {

    private Validaciones validar;

    private String noCelular;
    private String password;
    private EditText nombreEdit;
    private EditText apellidoPatEdit;
    private EditText apellidoMatEdit;
    private TextView fechaNacimientoText;
    private EditText numeroLicenciaEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro02);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recibirDatos();


        nombreEdit = findViewById(R.id.idRegistroNombre);
        apellidoPatEdit = findViewById(R.id.idRegistroApellidoPat);
        apellidoMatEdit = findViewById(R.id.idRegistroApellidoMat);
        fechaNacimientoText = findViewById(R.id.idRegistroFechaNacimiento);
        numeroLicenciaEdit = findViewById(R.id.idRegistroNumeroLicencia);

    }

    private void recibirDatos() {
        Bundle bundle = getIntent().getExtras();
        noCelular = bundle.getString("celular");
        password = bundle.getString("password");
    }

    public void seleccionarFecha(View view) {

        Calendar calendario = Calendar.getInstance();

        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH);
        int anio = calendario.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                fechaNacimientoText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }

        }, dia, mes, anio);
        datePickerDialog.show();
    }

    public void intentarRegistrar(View view) {

        validar = new Validaciones();

        String nombre = nombreEdit.getText().toString().trim();
        String apellidoPat = apellidoPatEdit.getText().toString().trim();
        String apellidoMat = apellidoMatEdit.getText().toString().trim();
        String fechaNacimiento = (String) fechaNacimientoText.getText();
        String numeroLicencia = numeroLicenciaEdit.getText().toString().trim();

        String resultado = validar.validarRegistro02(nombre, apellidoPat, apellidoMat, fechaNacimiento, numeroLicencia);

        if ("".equals(resultado)) {

            Conductor conductor = new Conductor();
            conductor.setNoCelular(noCelular);
            conductor.setPassword(password);
            conductor.setNombre(nombre + " " + apellidoPat + " " + apellidoMat);
            conductor.setFechaNacimiento(fechaNacimiento);
            conductor.setNumeroLicencia(numeroLicencia);

            // 1. Intentar registrarse al servidor.
            // 2. Si el registro fue exitoso se abre la pantalla reporte.

            Toast registrado = Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG);
            registrado.show();

            Intent intento = new Intent(this, Login.class);
            intento.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intento);

        } else {
            Toast datosInvalidosLogin = Toast.makeText(this, resultado, Toast.LENGTH_SHORT);
            datosInvalidosLogin.show();
        }

    }

}
