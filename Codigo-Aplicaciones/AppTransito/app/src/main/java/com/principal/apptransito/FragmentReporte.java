package com.principal.apptransito;

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

import java.io.ByteArrayOutputStream;

public class FragmentReporte extends Fragment {

    private static final String IMAGENES_LLENAS = "Ya tiene el máximo de fotos : 8";
    private static final String TIPO_REPORTE = "Accidente de carro";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Conductor conductor;
    private Validaciones validacion;

    private boolean[] menuFotos;
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
            conductor = (Conductor) getArguments().getSerializable("conductor");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // conductor = ((MainActivity)getActivity()).getConductor();

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        View vista = inflater.inflate(R.layout.fragment_reporte, container, false);

        menuFotos = new boolean[]{false, false, false, false, false, false, false, false};
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

        consultarGps();

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
                String latidud = latidudView.getText().toString().trim();
                String longitud = longitudView.getText().toString().trim();
                double lat = Double.parseDouble(latidud);
                double lon = Double.parseDouble(longitud);
                Reporte reporte = new Reporte();
                reporte.setIdReporte(1);
                reporte.setPlacas("0123456789");
                reporte.setNoCelular(conductor.getNoCelular());
                reporte.setLatitud(lat);
                reporte.setLongitud(lon);
                reporte.setPlacasImplicado(placasEdit.getText().toString().trim());
                reporte.setNombreImplicado(nombreEdit.getText().toString().trim());
                reporte.setPolizaImplicado(polizaEdit.getText().toString().trim());
                reporte.setMarcaImplicado(marcaEdit.getText().toString().trim());
                reporte.setModeloImplicado(modeloEdit.getText().toString().trim());
                reporte.setColorImplicado(colorEdit.getText().toString().trim());
                reporte.setFechaReporte(fechaView.getText().toString().trim());
                reporte.setTipoReporte(TIPO_REPORTE);

                String resultado = validacion.validarReporte(reporte, menuFotos[3]);

                if ("".equals(resultado)) {
                    Toast datosInvalidosLogin = Toast.makeText(getActivity(), "Reporte Enviado", Toast.LENGTH_SHORT);
                    datosInvalidosLogin.show();
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
