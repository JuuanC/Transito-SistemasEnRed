package com.principal.apptransito.activities;

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

import com.principal.apptransito.R;
import com.principal.apptransito.fragmentos.FragmentDatos;
import com.principal.apptransito.fragmentos.FragmentListaVehiculos;
import com.principal.apptransito.fragmentos.FragmentReporte;
import com.principal.apptransito.fragmentos.FragmentVehiculo;
import com.principal.apptransito.utilidades.Instancias;

public class MainActivity
        extends
            AppCompatActivity
        implements
            NavigationView.OnNavigationItemSelectedListener,
            FragmentReporte.OnFragmentInteractionListener,
            FragmentVehiculo.OnFragmentInteractionListener,
            FragmentDatos.OnFragmentInteractionListener {

    private Instancias misInstancias;
    private TextView nombreUsuario;
    private TextView celularUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        } else if (id == R.id.nav_semaforo) {

        } else if (id == R.id.nav_dictamen) {
            // Handle the camera action
        } else if (id == R.id.nav_historial) {

        } else if (id == R.id.nav_registrarVehiculo) {
            bundle = new Bundle();
            bundle.putSerializable("conductor", misInstancias);
            fragmentoActual = new FragmentVehiculo();
            fragmentoActual.setArguments(bundle);
            fragmentoSeleccionado = true;
        } else if (id == R.id.nav_actualizarVehiculo) {
            // TODO (4) Si no se pueden obtener los vehículos de la BD no se puede abrir este fragmento.
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

    // public Conductor getConductor() { return conductor; }

}
