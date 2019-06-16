using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using TransitoWeb.Models;

namespace TransitoWeb.Controllers
{
    public class VehiculoController : Controller
    {
        [HttpPost]
        public String Registro(String placa,
            String marca, String modelo, String anio, String color, String nombreAseguradora
            , String numPoliza, String telefono)
        {
            if (ValidarExistencia(placa) == 0)
            {
                using (TransitoContext dbSS =
                    new TransitoContext())
                {
                    Vehiculo vehiculo = new Vehiculo();
                    vehiculo.Placa = placa;
                    vehiculo.Marca = marca;
                    vehiculo.Modelo = modelo;
                    vehiculo.Anio = anio;
                    vehiculo.Color = color;
                    vehiculo.NombreAseguradora = nombreAseguradora;
                    vehiculo.NumPoliza = numPoliza;
                    vehiculo.Telefono = telefono;
                    dbSS.Vehiculo.Add(vehiculo);
                    dbSS.SaveChanges();
                    return "{\"correcto\": \"si\"}";
                }
            }
            else
                return "{\"correcto\": \"no\"}";
        }

        [HttpPut]
        public String Actualizar(String placaVieja, String placa,
            String marca, String modelo, String anio, String color, String nombreAseguradora
            , String numPoliza, String telefono)
        {
            if (ValidarExistencia(placa) == 1)
            {
                using (TransitoContext dbSS =
                    new TransitoContext())
                {
                    Vehiculo vehiculo = new Vehiculo();
                    vehiculo.Placa = placa;
                    vehiculo.Marca = marca;
                    vehiculo.Modelo = modelo;
                    vehiculo.Anio = anio;
                    vehiculo.Color = color;
                    vehiculo.NombreAseguradora = nombreAseguradora;
                    vehiculo.NumPoliza = numPoliza;
                    vehiculo.Telefono = telefono;
                    dbSS.Vehiculo.Update(vehiculo);
                    dbSS.SaveChanges();
                    return "{\"correcto\": \"si\"}";
                }
            }
            else
                return "{\"correcto\": \"no\"}";
        }

        [HttpDelete]
        public String Eliminar(String placa)
        {
            using (TransitoContext dbSS =
                    new TransitoContext())
            {
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.Placa  = placa;
                dbSS.Vehiculo.Remove(vehiculo);
                if (dbSS.SaveChanges() == 1)
                {
                    return "{\"correcto\": \"si\"}";
                }
                else
                    return "{\"correcto\": \"no\"}";

            }
        }

        [HttpGet]
        public List<Vehiculo> ListaVehiculos(String telefono)
        {
            List<Vehiculo> listaVehiculos = null;
            using (TransitoContext dbSS = new TransitoContext())
            {
                listaVehiculos = dbSS.Vehiculo.Where(a1 => a1.Telefono.Equals(telefono)).ToList();
            }
            return listaVehiculos;
        }

        public int ValidarExistencia(String placa)
        {
            using (TransitoContext dbSS = new TransitoContext())
            {
                var existe = dbSS.Vehiculo.Count(al =>
                                al.Placa.Equals(placa)
                                 );
                return existe;
            }
        }
    }
}