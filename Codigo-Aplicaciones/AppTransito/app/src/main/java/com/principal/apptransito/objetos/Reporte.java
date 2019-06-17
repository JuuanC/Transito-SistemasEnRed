package com.principal.apptransito.objetos;

import java.io.Serializable;

public class Reporte implements Serializable {

    private int idReporte;
    private String placas;
    private String noCelular;
    private String latitud;
    private String longitud;
    private String placasImplicado;
    private String nombreImplicado;
    private String polizaImplicado;
    private String aseguradoraImplicado;
    private String marcaImplicado;
    private String modeloImplicado;
    private String colorImplicado;
    private String fechaReporte;
    private String tipoReporte;
    private String status;
    private String descripcion;
    private String estatus;
    private Imagen[] imagenes;

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

    public String getNoCelular() {
        return noCelular;
    }

    public void setNoCelular(String noCelular) {
        this.noCelular = noCelular;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Imagen[] getImagenes() {
        return imagenes;
    }

    public void setImagenes(Imagen[] imagenes) {
        this.imagenes = imagenes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getAseguradoraImplicado() {
        return aseguradoraImplicado;
    }

    public void setAseguradoraImplicado(String aseguradoraImplicado) {
        this.aseguradoraImplicado = aseguradoraImplicado;
    }
}
