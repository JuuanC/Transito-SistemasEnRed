package com.principal.apptransito.activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Dictamen;

public class VistaDictamen extends AppCompatActivity {

    private Dictamen dictamen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_dictamen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            dictamen = (Dictamen) bundle.getSerializable("dictamen");
        }

        TextView folioView = findViewById(R.id.idDictamenFolio);
        TextView fechaView = findViewById(R.id.idDictamenFecha);
        TextView descripcionView = findViewById(R.id.idDictamenDescripcion);

        folioView.setText("Folio : " + dictamen.getIdFolio());
        fechaView.setText("Fecha : " + dictamen.getFecha());
        descripcionView.setText(dictamen.getDescripcion());

    }

}
