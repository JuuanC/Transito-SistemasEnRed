package com.principal.apptransito.utilidades;

import com.principal.apptransito.objetos.Conductor;
import com.principal.apptransito.objetos.Reporte;
import com.principal.apptransito.objetos.Vehiculo;

public class Validaciones {

    private static final String DATOS_VACIOS = "Asegure de ingresar todos los datos.";
    private static final String CELULAR_INVALIDO = "Ingrese un numero de 10 dígitos.";
    private static final String PLACAS_INVALIDAS = "Ingrese unas placas reales";
    private static final String MODELO_INVALIDO = "Ingrese un modelo válido";
    private static final String MARCA_INVALIDA = "Ingrese una marca válida";
    private static final String ANIO_INVALIDO = "Ingrese un año válido";

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

    public String validarActualizarDatos(Conductor conductor, String password2) {
        if ("".equals(conductor.getNoCelular()) | "".equals(conductor.getPassword()) | "".equals(password2) |
                "".equals(conductor.getNombre()) | "".equals(conductor.getNumeroLicencia())) {
            return DATOS_VACIOS;
        } else if (conductor.getNoCelular().length() < 10) {
            return CELULAR_INVALIDO;
        } else if (conductor.getPassword().length() < 5) {
            return "La contraseña es muy corta";
        } else if (!password2.equals(conductor.getPassword())) {
            return "Las contraseñas no coinciden";
        } else if (conductor.getNombre().length() < 7) {
            return "El nombre es demasiado corto.";
        } else if (conductor.getNumeroLicencia().length() < 4) {
            return "Ingrese una licencia correcta.";
        } else {
            return "";
        }
    }

    public String validarVehiculo(Vehiculo vehiculo) {
        if ("".equals(vehiculo.getPlacas()) | "".equals(vehiculo.getMarca()) | "".equals(vehiculo.getModelo())
                | "".equals(vehiculo.getAnio()) | "".equals(vehiculo.getColor()) ) {
            return DATOS_VACIOS;
        } if (vehiculo.getPlacas().length() < 10) {
            return PLACAS_INVALIDAS;
        } if (vehiculo.getMarca().length() < 3) {
            return MARCA_INVALIDA;
        } if (vehiculo.getModelo().length() < 3) {
            return MODELO_INVALIDO;
        } if (vehiculo.getAnio().length() < 4) {
            return ANIO_INVALIDO;
        } else {
            return "";
        }
    }
}