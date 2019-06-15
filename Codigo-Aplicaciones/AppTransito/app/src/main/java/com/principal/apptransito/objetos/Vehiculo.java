package com.principal.apptransito.objetos;

public class Vehiculo {

    private String celular;
    private String placas;
    private String marca;
    private String modelo;
    private String anio;
    private String color;
    private String numeroAseguradora;
    private String numeroPoliza;

    public Vehiculo () {
        // Constructir vacío de Vehículo.
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getPlacas() {
        return placas;
    }

    public void setPlacas(String placas) {
        this.placas = placas;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNumeroAseguradora() {
        return numeroAseguradora;
    }

    public void setNumeroAseguradora(String numeroAseguradora) {
        this.numeroAseguradora = numeroAseguradora;
    }

    public String getNumeroPoliza() {
        return numeroPoliza;
    }

    public void setNumeroPoliza(String numeroPoliza) {
        this.numeroPoliza = numeroPoliza;
    }
}
