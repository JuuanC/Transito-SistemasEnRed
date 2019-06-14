package com.principal.apptransito;

public class Reporte {

    private int idReporte;
    private String placas;
    private int idConductor;
    private double latitud;
    private double longitud;
    private String placasImplicado;
    private String nombreImplicado;
    private String polizaImplicado;
    private String marcaImplicado;
    private String modeloImplicado;
    private String colorImplicado;
    private String fechaReporte;
    private String tipoReporte;

    public Reporte() {
        // Constructor vacío de Reporte
    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public String getPlacas() {
        return placas;
    }

    public void setPlacas(String placas) {
        this.placas = placas;
    }

    public int getIdConductor() {
        return idConductor;
    }

    public void setIdConductor(int idConductor) {
        this.idConductor = idConductor;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getPlacasImplicado() {
        return placasImplicado;
    }

    public void setPlacasImplicado(String placasImplicado) {
        this.placasImplicado = placasImplicado;
    }

    public String getNombreImplicado() {
        return nombreImplicado;
    }

    public void setNombreImplicado(String nombreImplicado) {
        this.nombreImplicado = nombreImplicado;
    }

    public String getPolizaImplicado() {
        return polizaImplicado;
    }

    public void setPolizaImplicado(String polizaImplicado) {
        this.polizaImplicado = polizaImplicado;
    }

    public String getMarcaImplicado() {
        return marcaImplicado;
    }

    public void setMarcaImplicado(String marcaImplicado) {
        this.marcaImplicado = marcaImplicado;
    }

    public String getModeloImplicado() {
        return modeloImplicado;
    }

    public void setModeloImplicado(String modeloImplicado) {
        this.modeloImplicado = modeloImplicado;
    }

    public String getColorImplicado() {
        return colorImplicado;
    }

    public void setColorImplicado(String colorImplicado) {
        this.colorImplicado = colorImplicado;
    }

    public String getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(String fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }
}
