package com.principal.apptransito.activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Reporte;
import com.principal.apptransito.utilidades.Instancias;

public class InformacionOtroReporte extends AppCompatActivity {

    private Instancias misInstancias;
    private RequestQueue queue;
    private Reporte reporte;
    private TextView longitudView;
    private TextView latitudView;
    private TextView descripcionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_otro_reporte);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        queue = Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            reporte = (Reporte) bundle.getSerializable("reporte");
            misInstancias = (Instancias) bundle.getSerializable("instancias");
        }

        if ("Semaforo descompuesto".equals(reporte.getTipoReporte())) {
            setTitle("Información Semaforo");
        } else {
            setTitle("Información Bache");
        }

        descripcionView = findViewById(R.id.idOtroDescripcion);
        longitudView = findViewById(R.id.idOtroLongitud);
        latitudView = findViewById(R.id.idOtroLatitud);

        descripcionView.setText(reporte.getDescripcion());
        longitudView.setText(reporte.getLongitud());
        latitudView.setText(reporte.getLatitud());

    }

}
