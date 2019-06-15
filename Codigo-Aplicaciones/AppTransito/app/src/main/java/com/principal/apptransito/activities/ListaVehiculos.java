package com.principal.apptransito.activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Vehiculo;
import com.principal.apptransito.utilidades.Adapter;
import com.principal.apptransito.utilidades.Instancias;

public class ListaVehiculos extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Adapter adapter;
    private Instancias misInstancias;
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vehiculos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            misInstancias = (Instancias) bundle.getSerializable("conductor");
        }

        adapter = new Adapter(this, misInstancias.getVehiculos());
        lista = findViewById(R.id.idListaVehiculosReporte);

        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Vehiculo vehiculoSeleccionado = (Vehiculo) adapter.getItem(position);
        misInstancias.getReporte().setPlacas(vehiculoSeleccionado.getPlacas());
        // TODO Lógica para enviar el reporte al servidor con misInstancias.getReporte();
    }
}
