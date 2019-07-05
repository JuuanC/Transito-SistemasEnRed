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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.principal.apptransito.R;
import com.principal.apptransito.activities.InformacionOtroReporte;
import com.principal.apptransito.activities.InformacionReporte;
import com.principal.apptransito.objetos.Imagen;
import com.principal.apptransito.objetos.Reporte;
import com.principal.apptransito.utilidades.AdapterHistorial;
import com.principal.apptransito.utilidades.Instancias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class FragmentHistorial extends Fragment implements AdapterView.OnItemClickListener {

    private RequestQueue queue;
    private Instancias misInstancias;
    private AdapterHistorial adapter;
    private ListView lista;
    private Reporte reporteSeleccionado;
    private List<Imagen> imagenes;

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

        queue = Volley.newRequestQueue(getContext());
        conexionConsultarImagenes();

        adapter = new AdapterHistorial(getContext(), misInstancias.getListaReporte());
        lista = view.findViewById(R.id.idListaHistorial);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        reporteSeleccionado = (Reporte) adapter.getItem(position);

        Bundle bundle = new Bundle();
        bundle.putSerializable("reporte", reporteSeleccionado);
        bundle.putSerializable("instancias", misInstancias);
        // bundle.putSerializable("imagen", imagenes);

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

    private void conexionConsultarImagenes() {

        String url = "http://192.168.43.238:80/FotoReporte/ListaFotos/?idReporte=29";

        imagenes = new ArrayList<>();

        JsonArrayRequest getArray = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;

                    for (int a = 0; a < jsonArray.length(); a++) {

                        JSONObject obj = jsonArray.getJSONObject(a);

                        Imagen imagen = new Imagen();
                        imagen.setIdImagen(obj.getInt("idfoto"));
                        imagen.setImagenEnBytes(obj.getString("imagen").getBytes());
                        imagen.setIdReporte(obj.getInt("idReporte"));

                        imagenes.add(imagen);

                    }
                    System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA : " + imagenes.size());

                    //reporteSeleccionado.setImagenes(imagenes);misInstancias.getReporte().setImagenes(imagenes);

                    //System.out.println("Tamaño de la lista : " + misInstancias.getListaReporte().size());

                } catch (JSONException e) {
                    System.out.println("ERROOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOR");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(getArray);
    }

}
