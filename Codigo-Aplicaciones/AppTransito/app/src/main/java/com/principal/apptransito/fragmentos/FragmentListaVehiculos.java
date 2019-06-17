package com.principal.apptransito.fragmentos;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.principal.apptransito.R;
import com.principal.apptransito.activities.ModificarVehiculo;
import com.principal.apptransito.objetos.Vehiculo;
import com.principal.apptransito.utilidades.Adapter;
import com.principal.apptransito.utilidades.Instancias;

public class FragmentListaVehiculos extends Fragment implements AdapterView.OnItemClickListener {

    private Adapter adapter;
    private Instancias misInstancias;
    private ListView lista;

    public FragmentListaVehiculos() {
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

        adapter = new Adapter(getContext(), misInstancias.getVehiculos());
        lista = view.findViewById(R.id.idListaVehiculos);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Vehiculo vehiculoSeleccionado = (Vehiculo) adapter.getItem(position);
        Intent intento = new Intent(view.getContext(), ModificarVehiculo.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("vehiculo", vehiculoSeleccionado);
        bundle.putSerializable("instancias", misInstancias);
        intento.putExtras(bundle);
        startActivity(intento);
    }
}
