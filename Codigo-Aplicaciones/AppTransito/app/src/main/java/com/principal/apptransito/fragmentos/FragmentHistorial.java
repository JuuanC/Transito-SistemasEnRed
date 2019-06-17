package com.principal.apptransito.fragmentos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.principal.apptransito.R;
import com.principal.apptransito.activities.InformacionOtroReporte;
import com.principal.apptransito.activities.InformacionReporte;
import com.principal.apptransito.objetos.Reporte;
import com.principal.apptransito.utilidades.AdapterHistorial;
import com.principal.apptransito.utilidades.Instancias;


public class FragmentHistorial extends Fragment implements AdapterView.OnItemClickListener {

    private Instancias misInstancias;
    private AdapterHistorial adapter;
    private ListView lista;

    public FragmentHistorial() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            misInstancias = (Instancias) getArguments().getSerializable("conductor");
            System.out.println("Tamaño de la lista : " + misInstancias.getListaReporte().size());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_reportes, container, false);
        getActivity().setTitle("Historial de reportes");

        adapter = new AdapterHistorial(getContext(), misInstancias.getListaReporte());
        lista = view.findViewById(R.id.idListaHistorial);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Reporte reporteSeleccionado = (Reporte) adapter.getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("reporte", reporteSeleccionado);
        bundle.putSerializable("instancias", misInstancias);
        if ("Accidente vehicular".equals(reporteSeleccionado.getTipoReporte())) {
            Intent intento = new Intent(view.getContext(), InformacionReporte.class);
            intento.putExtras(bundle);
            startActivity(intento);
        } else {
            Intent intento = new Intent(view.getContext(), InformacionOtroReporte.class);
            intento.putExtras(bundle);
            startActivity(intento);
        }
    }

}
