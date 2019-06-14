package com.principal.apptransito;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registro01 extends AppCompatActivity {

    private Conductor conductor;
    private Validaciones validar;
    private EditText noCelularEdit;
    private EditText passwordEdit;
    private EditText passwordEdit2;
    private Button entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro01);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        noCelularEdit = findViewById(R.id.idRegistroCelular);
        passwordEdit = findViewById(R.id.idRegistroPassword);
        passwordEdit2 = findViewById(R.id.idRegistroPassword2);
        entrar = findViewById(R.id.idRegistroSiguiente);

    }

    public void siguienteMenuRegistro(View view) {

        validar = new Validaciones();

        String noCelular = noCelularEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        String password2 = passwordEdit2.getText().toString().trim();

        String resultado = validar.validarRegistro01(noCelular, password, password2);

        if ("".equals(resultado)) {
            Intent intento = new Intent(view.getContext(), Registro02.class);
            intento.putExtra("celular", noCelular);
            intento.putExtra("password", password);
            startActivity(intento);
        } else {
            Toast datosInvalidosLogin = Toast.makeText(this, resultado, Toast.LENGTH_SHORT);
            datosInvalidosLogin.show();
        }

    }
}
