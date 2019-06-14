package com.principal.apptransito;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private Conductor conductor;
    private Validaciones validar;
    private EditText noCelularEdit;
    private EditText passwordEdit;
    private Button entrar;
    private Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        noCelularEdit = findViewById(R.id.idCelular);
        passwordEdit = findViewById(R.id.idPassword);
        entrar = findViewById(R.id.idEntrar);
        registrar = findViewById(R.id.idRegistrar);

    }

    public void intentarEntrar(View view) {

        validar = new Validaciones();

        String noCelular = noCelularEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        String resultado = validar.validarLogin(noCelular, password);

        if ("".equals(resultado)) {
            // 1. comprobar si el usuario existe.
            // 2. Si existe entra, sino no entra si no se logra una conexión no entra.

            conductor = new Conductor();
            conductor.setNoCelular(noCelular);
            conductor.setPassword(password);
            conductor.setNombre("Luis Gerardo Bonilla Ramírez");
            conductor.setFechaNacimiento("26/08/1996");
            conductor.setNumeroLicencia("723819293");

            Intent intento = new Intent(view.getContext(), MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("conductor", conductor);
            intento.putExtras(bundle);
            startActivity(intento);

        } else {
           Toast datosInvalidosLogin = Toast.makeText(this, resultado, Toast.LENGTH_SHORT);
           datosInvalidosLogin.show();
        }
    }

    public void desplegarRegistrarse(View view) {
        Intent intento = new Intent(view.getContext(), Registro01.class);
        startActivityForResult(intento, 0);
    }

}
