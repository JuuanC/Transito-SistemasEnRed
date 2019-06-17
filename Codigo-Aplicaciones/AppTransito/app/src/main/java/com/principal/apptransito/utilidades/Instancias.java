package com.principal.apptransito.utilidades;

import com.principal.apptransito.objetos.Conductor;
import com.principal.apptransito.objetos.Reporte;
import com.principal.apptransito.objetos.Vehiculo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Instancias implements Serializable {

    private static final long serialVersionUID = 1L;
    private static Instancias INSTANCE = null;

    private Conductor conductor;
    private List<Vehiculo> vehiculos;
    private Reporte reporte;
    private List<Reporte> listaReporte;

    public Instancias() {
        conductor = new Conductor();
        vehiculos = new ArrayList<>();
        // Constructor vacío.
    }

    private synchronized static void crearInstancia() {
        if (INSTANCE == null) {
            INSTANCE = new Instancias();
        }
    }

    public static Instancias getInstancia() {
        if (INSTANCE == null) crearInstancia();
        return INSTANCE;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public List<Reporte> getListaReporte() {
        return listaReporte;
    }

    public void setListaReporte(List<Reporte> listaReporte) {
        this.listaReporte = listaReporte;
    }

    public Reporte getReporte() {
        return reporte;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }
}
