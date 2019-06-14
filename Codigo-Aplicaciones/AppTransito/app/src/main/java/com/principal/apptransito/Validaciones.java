package com.principal.apptransito;

public class Validaciones {

    private static final String DATOS_VACIOS = "Asegure de ingresar todos los datos.";
    private static final String CELULAR_INVALIDO = "Ingrese un numero de 10 dígitos.";

    public String validarLogin(String noCelular, String password) {

        if ("".equals(noCelular) | "".equals(password)) {
            return DATOS_VACIOS;
        } else if (noCelular.length() < 10) {
            return CELULAR_INVALIDO;
        } else {
            return "";
        }

    }

    public String validarRegistro01(String noCelular, String password, String password2) {

        if ("".equals(noCelular) | "".equals(password) | "".equals(password2)) {
            return DATOS_VACIOS;
        } else if (noCelular.length() < 10) {
            return CELULAR_INVALIDO;
        } else if (password.length() < 5) {
            return "La contraseña es muy corta";
        } else if (!password.equals(password2)) {
            return "Las contraseñas no coinciden";
        } else {
            return "";
        }

    }

    public String validarRegistro02(String nombre, String apellidoPat, String apellidoMat, String fechaNacimiento, String numeroLicencia) {

        if ("".equals(nombre) | "".equals(apellidoPat) | "".equals(apellidoMat) | "".equals(fechaNacimiento) | "".equals(numeroLicencia)) {
            return DATOS_VACIOS;
        } else if (nombre.length() < 2) {
            return "El nombre es demasiado corto.";
        } else if (apellidoPat.length() < 2) {
            return "El apellido paterno es demasiado corto.";
        } else if (apellidoMat.length() < 2) {
            return "El apellido materno es demasiado corto.";
        } else if (numeroLicencia.length() < 4) {
            return "Ingrese una licencia correcta.";
        } else {
            return "";
        }

    }

    public String validarReporte(Reporte reporte, boolean fotoMinima) {
        if ("".equals(reporte.getLatitud()) | "".equals(reporte.getLongitud()) |
                "".equals(reporte.getFechaReporte()) | "".equals(reporte.getTipoReporte()) |
                "".equals(reporte.getPlacasImplicado()) | "".equals(reporte.getMarcaImplicado()) |
                "".equals(reporte.getModeloImplicado()) | "".equals(reporte.getColorImplicado())) {
            return DATOS_VACIOS;
        } else if (reporte.getPlacasImplicado().length() < 10) {
            return "Ingrese placas reales";
        } else if (!fotoMinima) {
            return "Ingrese 4 fotos como mínimo";
        } else {
            return "";
        }
    }
}