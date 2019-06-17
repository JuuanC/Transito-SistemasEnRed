package com.principal.apptransito.utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Reporte;

import java.util.List;

public class AdapterHistorial extends BaseAdapter {
    private Context contexto;
    private List<Reporte> listaReportes;

    public AdapterHistorial(Context contexto, List<Reporte> listaReportes) {
        this.contexto = contexto;
        this.listaReportes = listaReportes;
    }

    @Override
    public int getCount() {
        return listaReportes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaReportes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_historial, null);
        }

        TextView placaEdit = convertView.findViewById(R.id.idHistorialPlaca);
        TextView fechaEdit = convertView.findViewById(R.id.idHistorialFecha);

        placaEdit.setText(listaReportes.get(position).getPlacas());
        fechaEdit.setText(listaReportes.get(position).getFechaReporte());

        return convertView;
    }
}
