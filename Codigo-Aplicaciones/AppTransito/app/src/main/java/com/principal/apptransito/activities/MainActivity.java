package com.principal.apptransito.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.principal.apptransito.R;
import com.principal.apptransito.fragmentos.FragmentBache;
import com.principal.apptransito.fragmentos.FragmentDatos;
import com.principal.apptransito.fragmentos.FragmentHistorial;
import com.principal.apptransito.fragmentos.FragmentListaVehiculos;
import com.principal.apptransito.fragmentos.FragmentReporte;
import com.principal.apptransito.fragmentos.FragmentSemaforo;
import com.principal.apptransito.fragmentos.FragmentVehiculo;
import com.principal.apptransito.objetos.Reporte;
import com.principal.apptransito.objetos.Vehiculo;
import com.principal.apptransito.utilidades.Instancias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity
        extends
            AppCompatActivity
        implements
            NavigationView.OnNavigationItemSelectedListener,
            FragmentReporte.OnFragmentInteractionListener,
            FragmentVehiculo.OnFragmentInteractionListener,
            FragmentDatos.OnFragmentInteractionListener {

    private boolean bandera;
    private Instancias misInstancias;
    private RequestQueue queue;
    private List<Reporte> reportes;
    private List<Vehiculo> vehiculos;

    private TextView nombreUsuario;
    private TextView celularUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        queue = Volley.newRequestQueue(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        nombreUsuario = (TextView) hView.findViewById(R.id.nombreUsuarioView);
        celularUsuario = (TextView) hView.findViewById(R.id.celularUsuarioView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            misInstancias = (Instancias) bundle.getSerializable("conductor");
        }

        conexionConsultarVehiculos(misInstancias.getConductor().getTelefono());
        conexionConsultarReportes(misInstancias.getConductor().getTelefono());
        nombreUsuario.setText("Hola " + misInstancias.getConductor().getNombre().toString() +"!");
        celularUsuario.setText("Numero : " + misInstancias.getConductor().getTelefono().toString());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragmentoActual = null;
        boolean fragmentoSeleccionado = false;
        Bundle bundle;

        if (id == R.id.nav_accidente) {
            bundle = new Bundle();
            bundle.putSerializable("conductor", misInstancias);
            fragmentoActual = new FragmentReporte();
            fragmentoActual.setArguments(bundle);
            fragmentoSeleccionado = true;
        } else if (id == R.id.nav_baches) {
            bundle = new Bundle();
            bundle.putSerializable("conductor", misInstancias);
            fragmentoActual = new FragmentBache();
            fragmentoActual.setArguments(bundle);
            fragmentoSeleccionado = true;
        } else if (id == R.id.nav_semaforo) {
            bundle = new Bundle();
            bundle.putSerializable("conductor", misInstancias);
            fragmentoActual = new FragmentSemaforo();
            fragmentoActual.setArguments(bundle);
            fragmentoSeleccionado = true;
        } else if (id == R.id.nav_historial) {
            if (bandera) {
                bundle = new Bundle();
                bundle.putSerializable("conductor", misInstancias);
                fragmentoActual = new FragmentHistorial();
                fragmentoActual.setArguments(bundle);
                fragmentoSeleccionado = true;
            } else {
                Toast datosInvalidosLogin = Toast.makeText(getApplicationContext(), "Hubo un error en la conexión, inténtelo más tarde.", Toast.LENGTH_SHORT);
                datosInvalidosLogin.show();
            }
            System.out.println("BANDERA : " + bandera);
        } else if (id == R.id.nav_registrarVehiculo) {
            bundle = new Bundle();
            bundle.putSerializable("conductor", misInstancias);
            fragmentoActual = new FragmentVehiculo();
            fragmentoActual.setArguments(bundle);
            fragmentoSeleccionado = true;
        } else if (id == R.id.nav_actualizarVehiculo) {
            bundle = new Bundle();
            bundle.putSerializable("conductor", misInstancias);
            fragmentoActual = new FragmentListaVehiculos();
            fragmentoActual.setArguments(bundle);
            fragmentoSeleccionado = true;
        } else if (id == R.id.nav_misDatos) {
            bundle = new Bundle();
            bundle.putSerializable("conductor", misInstancias);
            fragmentoActual = new FragmentDatos();
            fragmentoActual.setArguments(bundle);
            fragmentoSeleccionado = true;
        }

        if (fragmentoSeleccionado) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragmentoActual).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void conexionConsultarVehiculos(String telefono) {
        bandera = false;
        String url = "http://192.168.43.238:80/Vehiculo/ListaVehiculos/?telefono=" + telefono;
        vehiculos = new ArrayList<>();

        JsonArrayRequest getArray = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;
                    for (int a = 0; a < jsonArray.length(); a++) {

                        JSONObject obj = jsonArray.getJSONObject(a);

                        Vehiculo vehiculo = new Vehiculo();

                        vehiculo.setCelular(obj.getString("telefono"));
                        vehiculo.setPlacas(obj.getString("placa"));
                        vehiculo.setMarca(obj.getString("marca"));
                        vehiculo.setModelo(obj.getString("modelo"));
                        vehiculo.setAnio(obj.getString("anio"));
                        vehiculo.setColor(obj.getString("color"));
                        vehiculo.setNumeroAseguradora(obj.getString("nombreAseguradora"));
                        vehiculo.setNumeroPoliza(obj.getString("numPoliza"));

                        vehiculos.add(vehiculo);

                    }

                    misInstancias.setVehiculos(vehiculos);

                    bandera = true;
                } catch (JSONException e) {
                        e.printStackTrace();
                        bandera = false;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(getArray);

    }

    private void conexionConsultarReportes(String telefono) {
        bandera = false;

        String url = "http://192.168.43.238:80/Reporte/ListaReportesConductor/?telefono=" + telefono;
        reportes = new ArrayList<>();

        JsonArrayRequest getArray = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;

                    for (int a = 0; a < jsonArray.length(); a++) {

                        JSONObject obj = jsonArray.getJSONObject(a);

                        Reporte nuevoReporte = new Reporte();

                        nuevoReporte.setIdReporte(obj.getInt("idReporte"));
                        nuevoReporte.setPlacas(obj.getString("placa"));
                        nuevoReporte.setLatitud(obj.getString("latitud"));
                        nuevoReporte.setLongitud(obj.getString("longitud"));
                        nuevoReporte.setNoCelular(obj.getString("telefono"));
                        nuevoReporte.setPlacasImplicado(obj.getString("placasImplicado"));
                        nuevoReporte.setMarcaImplicado(obj.getString("marcaImplicado"));
                        nuevoReporte.setModeloImplicado(obj.getString("modeloImplicado"));
                        nuevoReporte.setColorImplicado(obj.getString("colorImplicado"));
                        nuevoReporte.setNombreImplicado(obj.getString("nombreImplicado"));
                        nuevoReporte.setAseguradoraImplicado(obj.getString("nombreAseguradoraImplicado"));
                        nuevoReporte.setPolizaImplicado(obj.getString("numPolizaImplicado"));
                        nuevoReporte.setTipoReporte(obj.getString("tipoReporte"));
                        nuevoReporte.setDescripcion(obj.getString("descripcion"));
                        nuevoReporte.setFechaReporte(obj.getString("fechaReporte"));
                        nuevoReporte.setEstatus(obj.getString("estatus"));

                        reportes.add(nuevoReporte);

                    }

                    misInstancias.setListaReporte(reportes);
                    System.out.println("Tamaño de la lista : " + misInstancias.getListaReporte().size());

                    bandera = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                bandera = false;
            }
        });
        queue.add(getArray);
    }

}
