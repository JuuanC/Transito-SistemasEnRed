package com.principal.apptransito.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Vehiculo;
import com.principal.apptransito.utilidades.Adapter;
import com.principal.apptransito.utilidades.Instancias;

/**
 * A simple {@link Fragment} subclass.
 */
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
        System.out.println("Placa : " + vehiculoSeleccionado.getPlacas());
        System.out.println("Marca : " + vehiculoSeleccionado.getMarca());
        System.out.println("Modelo : " + vehiculoSeleccionado.getModelo());
    }
}
