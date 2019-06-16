package com.principal.apptransito.activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Reporte;
import com.principal.apptransito.utilidades.Instancias;

public class InformacionReporte extends AppCompatActivity {

    private Reporte reporte;
    private Instancias misInstancias;

    private Button dictamen;
    private TextView estatusView;
    private TextView placasView;
    private TextView marcaView;
    private TextView modeloView;
    private TextView colorView;
    private TextView nombreView;
    private TextView polizaView;
    private TextView latitudView;
    private TextView longitudView;
    private TextView fechaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_reporte);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            reporte = (Reporte) bundle.getSerializable("reporte");
            misInstancias = (Instancias) bundle.getSerializable("instancias");
        }

        dictamen = findViewById(R.id.idVerDictamen);
        estatusView = findViewById(R.id.idInformacionEstatus);
        placasView = findViewById(R.id.idInformacionPlacas);
        marcaView = findViewById(R.id.idInformacionMarca);
        modeloView = findViewById(R.id.idInformacionModelo);
        colorView = findViewById(R.id.idInformacionColor);
        nombreView = findViewById(R.id.idInformacionNombre);
        polizaView = findViewById(R.id.idInformacionPoliza);
        latitudView = findViewById(R.id.idInformacionLatitud);
        longitudView = findViewById(R.id.idInformacionLongitud);
        fechaView = findViewById(R.id.idInformacionFecha);

        estatusView.setText("Estado : " + reporte.getEstatus());
        placasView.setText(reporte.getPlacasImplicado());
        marcaView.setText(reporte.getMarcaImplicado());
        modeloView.setText(reporte.getModeloImplicado());
        colorView.setText(reporte.getColorImplicado());
        nombreView.setText(reporte.getNombreImplicado());
        polizaView.setText(reporte.getPolizaImplicado());
        latitudView.setText(reporte.getLatitud());
        longitudView.setText(reporte.getLongitud());
        fechaView.setText(reporte.getFechaReporte());

        if (reporte.getEstatus().equals("dictaminado")) {
            dictamen.setEnabled(true);
        }

    }
}
