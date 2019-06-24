package com.principal.apptransito.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.principal.apptransito.R;
import com.principal.apptransito.objetos.Dictamen;
import com.principal.apptransito.objetos.Imagen;
import com.principal.apptransito.objetos.Reporte;
import com.principal.apptransito.utilidades.Instancias;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class InformacionReporte extends AppCompatActivity implements View.OnClickListener {

    private Reporte reporte;
    private Instancias misInstancias;
    private List<Imagen> imagenes;
    private RequestQueue queue;

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

    private ImageView imagen1View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_reporte);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        queue = Volley.newRequestQueue(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            reporte = (Reporte) bundle.getSerializable("reporte");
            misInstancias = (Instancias) bundle.getSerializable("instancias");
            imagenes = (List<Imagen>) bundle.getSerializable("imagen");
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
        imagen1View = findViewById(R.id.imageViewReporte1);

        //Bitmap bitmap = BitmapFactory.decodeByteArray(imagenes.get(1).getImagenEnBytes(), 0,
        //        imagenes.get(1).getImagenEnBytes().length);

        //imagen1View.setImageBitmap(bitmap);

        estatusView.setText("Estado : " + reporte.getEstatus());
        placasView.setText(reporte.getPlacasImplicado());
        marcaView.setText(reporte.getMarcaImplicado());
        modeloView.setText(reporte.getModeloImplicado());
        colorView.setText(reporte.getColorImplicado());
        nombreView.setText(reporte.getNombreImplicado());
        polizaView.setText(reporte.getPolizaImplicado());
        latitudView.setText(reporte.getLatitud());
        longitudView.setText(reporte.getLongitud());
        fechaView.setText(reporte.getFechaReporte().substring(0,9));

        if (reporte.getEstatus().equals("Dictaminado")) {
            dictamen.setEnabled(true);
        }

        dictamen.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.idVerDictamen) {
            conexionObtenerDictamen();
        }
    }

    private void conexionObtenerDictamen() {
        String url = "http://192.168.100.4:80/Dictamen/ObtenerDictamen/?idReporte=" + reporte.getIdReporte();

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Dictamen dictamen = new Dictamen();

                    dictamen.setIdFolio(response.getInt("idFolio"));
                    dictamen.setDescripcion(response.getString("descripcion"));
                    dictamen.setFecha(response.getString("fecha"));
                    dictamen.setIdPerito(response.getInt("idPerito"));
                    dictamen.setIdReporte(response.getInt("idReporte"));

                    Intent intento = new Intent(getApplicationContext(), VistaDictamen.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dictamen", dictamen);
                    intento.putExtras(bundle);
                    startActivity(intento);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast datosInvalidosLogin = Toast.makeText(getApplicationContext(), "Hubo un error en la conexión, inténtelo más tarde", Toast.LENGTH_SHORT);
                datosInvalidosLogin.show();
            }
        });

        queue.add(getRequest);
    }

}
