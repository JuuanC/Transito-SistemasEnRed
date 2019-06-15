package com.principal.apptransito.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Conductor;
import com.principal.apptransito.utilidades.Instancias;
import com.principal.apptransito.utilidades.Validaciones;

public class Login extends AppCompatActivity {

    private Conductor conductor;
    private Instancias misInstancias;

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

            if (realizarConexionLogin()) {

                misInstancias = new Instancias();
                misInstancias.getConductor().setNoCelular(noCelular);
                misInstancias.getConductor().setPassword(password);
                misInstancias.getConductor().setNombre("Luis Gerardo Bonilla Ramírez");
                misInstancias.getConductor().setFechaNacimiento("26/08/1996");
                misInstancias.getConductor().setNumeroLicencia("723819293");

                // TODO (1) Si el usuario existe, regresar el usuario de la sesión y asignarla a las instancias.

                Intent intento = new Intent(view.getContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("conductor", misInstancias);
                intento.putExtras(bundle);
                startActivity(intento);
            }

        } else {
           Toast datosInvalidosLogin = Toast.makeText(this, resultado, Toast.LENGTH_SHORT);
           datosInvalidosLogin.show();
        }
    }

    public void desplegarRegistrarse(View view) {
        Intent intento = new Intent(view.getContext(), Registro01.class);
        startActivityForResult(intento, 0);
    }

    private boolean realizarConexionLogin() {
        // Lodigca para conectarse al servidor
        return true;
    }

}
