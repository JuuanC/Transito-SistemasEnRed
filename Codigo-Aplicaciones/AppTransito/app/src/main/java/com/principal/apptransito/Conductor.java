package com.principal.apptransito;

import java.io.Serializable;

public class Conductor implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idConductor;
    private String noCelular;
    private String password;
    private String nombre;
    private String fechaNacimiento;
    private String numeroLicencia;

    public Conductor () {
        // Constructor de la clase Conductor.
    }

    public int getIdConductor() { return idConductor; }

    public void setIdConductor(int idConductor) { this.idConductor = idConductor; }

    public String getNoCelular() {
        return noCelular;
    }

    public void setNoCelular(String noCelular) {
        this.noCelular = noCelular;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }
}
