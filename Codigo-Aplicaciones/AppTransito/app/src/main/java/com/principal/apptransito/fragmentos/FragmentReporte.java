package com.principal.apptransito.fragmentos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.principal.apptransito.R;
import com.principal.apptransito.activities.ListaVehiculos;
import com.principal.apptransito.objetos.Imagen;
import com.principal.apptransito.utilidades.Instancias;
import com.principal.apptransito.utilidades.Validaciones;
import com.principal.apptransito.objetos.Reporte;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FragmentReporte extends Fragment {

    private static final String IMAGENES_LLENAS = "Ya tiene el máximo de fotos : 8";
    private static final String TIPO_REPORTE = "Accidente vehicular";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;

    private Instancias misInstancias;
    private Validaciones validacion;
    private Imagen imagen;
    private List<Imagen> listaImagenes;

    private boolean[] menuFotos;
    private byte[][] imagenesEnBytes;
    private int banderaImagenes;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private ImageView imageView7;
    private ImageView imageView8;
    private EditText placasEdit;
    private EditText marcaEdit;
    private EditText modeloEdit;
    private EditText colorEdit;
    private EditText nombreEdit;
    private EditText polizaEdit;
    private TextView latidudView;
    private TextView longitudView;
    private TextView fechaView;
    private Button registrarReporte;
    private Button tomarFoto;
    private Button limpiarFotos;

    private OnFragmentInteractionListener mListener;

    public FragmentReporte() {
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

        getActivity().setTitle("Accidente vehícular");

        listaImagenes = new ArrayList<>();

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        View vista = inflater.inflate(R.layout.fragment_reporte, container, false);

        consultarGps();

        menuFotos = new boolean[]{false, false, false, false, false, false, false, false};
        imagenesEnBytes = new byte[8][];
        banderaImagenes = 0;
        placasEdit = vista.findViewById(R.id.idPlacas);
        marcaEdit = vista.findViewById(R.id.idMarca);
        modeloEdit = vista.findViewById(R.id.idModelo);
        colorEdit = vista.findViewById(R.id.idColor);
        nombreEdit = vista.findViewById(R.id.idNombreConductor);
        polizaEdit = vista.findViewById(R.id.idPolizaSeguro);
        latidudView = vista.findViewById(R.id.idLatitud);
        longitudView = vista.findViewById(R.id.idLongitud);
        fechaView = vista.findViewById(R.id.idFechaReporte);
        imageView1 = vista.findViewById(R.id.imageView1);
        imageView2 = vista.findViewById(R.id.imageView2);
        imageView3 = vista.findViewById(R.id.imageView3);
        imageView4 = vista.findViewById(R.id.imageView4);
        imageView5 = vista.findViewById(R.id.imageView5);
        imageView6 = vista.findViewById(R.id.imageView6);
        imageView7 = vista.findViewById(R.id.imageView7);
        imageView8 = vista.findViewById(R.id.imageView8);
        tomarFoto = vista.findViewById(R.id.idTomarFoto);
        limpiarFotos = vista.findViewById(R.id.idLimpiarFotos);
        registrarReporte = vista.findViewById(R.id.idSiguienteReporte);

        tomarFoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (hayEspacioFotos()) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,
                            CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                } else {
                    Toast datosInvalidosLogin = Toast.makeText(getActivity(), IMAGENES_LLENAS, Toast.LENGTH_SHORT);
                    datosInvalidosLogin.show();
                }
            }

        });

        registrarReporte.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validacion = new Validaciones();
                String latitud = latidudView.getText().toString().trim();
                String longitud = longitudView.getText().toString().trim();
                Reporte reporte = new Reporte();
                reporte.setNoCelular(misInstancias.getConductor().getTelefono());
                reporte.setLatitud(latitud);
                reporte.setLongitud(longitud);
                reporte.setPlacasImplicado(placasEdit.getText().toString().trim());
                reporte.setNombreImplicado(nombreEdit.getText().toString().trim());
                reporte.setPolizaImplicado(polizaEdit.getText().toString().trim());
                reporte.setMarcaImplicado(marcaEdit.getText().toString().trim());
                reporte.setModeloImplicado(modeloEdit.getText().toString().trim());
                reporte.setColorImplicado(colorEdit.getText().toString().trim());
                reporte.setFechaReporte(fechaView.getText().toString().trim());
                reporte.setEstatus("Por Dictaminar");
                reporte.setTipoReporte(TIPO_REPORTE);

                String resultado = validacion.validarReporte(reporte, menuFotos[3]);

                if ("".equals(resultado)) {
                    reporte.setImagenes(listaImagenes);
                    misInstancias.setReporte(reporte);
                    Intent intento = new Intent(v.getContext(), ListaVehiculos.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("conductor", misInstancias);
                    intento.putExtras(bundle);
                    startActivity(intento);
                } else {
                    Toast datosInvalidosLogin = Toast.makeText(getActivity(), resultado, Toast.LENGTH_SHORT);
                    datosInvalidosLogin.show();
                }



            }

        });

        limpiarFotos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imageView1.setImageResource(R.drawable.image_not_fund);
                imageView2.setImageResource(R.drawable.image_not_fund);
                imageView3.setImageResource(R.drawable.image_not_fund);
                imageView4.setImageResource(R.drawable.image_not_fund);
                imageView5.setImageResource(R.drawable.image_not_fund);
                imageView6.setImageResource(R.drawable.image_not_fund);
                imageView7.setImageResource(R.drawable.image_not_fund);
                imageView8.setImageResource(R.drawable.image_not_fund);

                for (int i = 0; i < 8; i++) {
                    menuFotos[i] = false;
                }

                imagenesEnBytes = new byte[8][];
                listaImagenes.clear();
            }
        });

        return vista;

    }

    private synchronized void consultarGps() {

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                latidudView.setText("" + location.getLatitude());
                longitudView.setText("" + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {   }

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }

    private boolean hayEspacioFotos() {
        if (menuFotos[7]) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                imagen = new Imagen();
                imagen.setImagenEnBytes(byteArray);
                listaImagenes.add(imagen);

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);

                pintarImagen(bitmap);

            }
        }
    }

    private void pintarImagen(Bitmap bitmap) {
        for (int i = 0; i < 8; i++) {
            if (!menuFotos[i]) {
                switch (i) {
                    case 0:
                        imageView1.setImageBitmap(bitmap);
                        menuFotos[i] = true;
                        break;
                    case 1:
                        imageView2.setImageBitmap(bitmap);
                        menuFotos[i] = true;
                        break;
                    case 2:
                        imageView3.setImageBitmap(bitmap);
                        menuFotos[i] = true;
                        break;
                    case 3:
                        imageView4.setImageBitmap(bitmap);
                        menuFotos[i] = true;
                        break;
                    case 4:
                        imageView5.setImageBitmap(bitmap);
                        menuFotos[i] = true;
                        break;
                    case 5:
                        imageView6.setImageBitmap(bitmap);
                        menuFotos[i] = true;
                        break;
                    case 6:
                        imageView7.setImageBitmap(bitmap);
                        menuFotos[i] = true;
                        break;
                    case 7:
                        imageView8.setImageBitmap(bitmap);
                        menuFotos[i] = true;
                        break;
                }
                break;
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
