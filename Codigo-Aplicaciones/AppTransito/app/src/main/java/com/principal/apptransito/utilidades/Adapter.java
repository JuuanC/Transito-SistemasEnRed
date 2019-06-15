package com.principal.apptransito.utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Vehiculo;

import java.util.List;

public class Adapter extends BaseAdapter {

    private Context contexto;
    private List<Vehiculo> listaVehiculos;

    public Adapter(Context contexto, List<Vehiculo> listaVehiculos) {
        this.contexto = contexto;
        this.listaVehiculos = listaVehiculos;
    }

    @Override
    public int getCount() {
        return listaVehiculos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaVehiculos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_vehiculo, null);
        }

        TextView placaEdit = convertView.findViewById(R.id.idItemPlaca);
        TextView marcaEdit = convertView.findViewById(R.id.idItemMarca);

        placaEdit.setText(listaVehiculos.get(position).getPlacas());
        marcaEdit.setText(listaVehiculos.get(position).getMarca());

        return convertView;
    }
}
