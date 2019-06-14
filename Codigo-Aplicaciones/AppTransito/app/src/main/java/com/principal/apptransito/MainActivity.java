package com.principal.apptransito;

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

public class MainActivity
        extends
            AppCompatActivity
        implements
            NavigationView.OnNavigationItemSelectedListener,
            FragmentReporte.OnFragmentInteractionListener,
            FragmentVehiculo.OnFragmentInteractionListener,
            FragmentDatos.OnFragmentInteractionListener {

    private Conductor conductor;
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
            conductor = (Conductor) bundle.getSerializable("conductor");
        }

        nombreUsuario.setText("Hola " + conductor.getNombre().toString() +"!");
        celularUsuario.setText("Numero : " + conductor.getNoCelular().toString());

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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragmentoActual = null;
        boolean fragmentoSeleccionado = false;

        if (id == R.id.nav_accidente) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("conductor", conductor);
            fragmentoActual = new FragmentReporte();
            fragmentoActual.setArguments(bundle);
            fragmentoSeleccionado = true;
        } else if (id == R.id.nav_baches) {

        } else if (id == R.id.nav_semaforo) {

        } else if (id == R.id.nav_dictamen) {
            // Handle the camera action
        } else if (id == R.id.nav_historial) {

        } else if (id == R.id.nav_vehiculo) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("conductor", conductor);
            fragmentoActual = new FragmentVehiculo();
            fragmentoActual.setArguments(bundle);
            fragmentoSeleccionado = true;
        } else if (id == R.id.nav_misDatos) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("conductor", conductor);
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

    public Conductor getConductor() { return conductor; }

}
