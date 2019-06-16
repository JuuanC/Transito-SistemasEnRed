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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_lista_vehiculos, container, false);
        getActivity().setTitle("Historial de reportes");

        adapter = new AdapterHistorial(getContext(), misInstancias.getListaReporte());
        lista = view.findViewById(R.id.idListaVehiculos);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Reporte vehiculoSeleccionado = (Reporte) adapter.getItem(position);
        Intent intento = new Intent(view.getContext(), InformacionReporte.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("reporte", vehiculoSeleccionado);
        bundle.putSerializable("instancias", misInstancias);
        intento.putExtras(bundle);
        startActivity(intento);
    }

}
