using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using TransitoWeb.Models;

namespace TransitoWeb.Controllers
{
    public class ReporteController : Controller
    {
        [HttpPost]
        public String Registro(String placa, String latitud, String longitud, String placasImplicado,
            String nombreImplicado, String nombreAseguradoraImplicado, String numPolizaImplicado, String marcaImplicado,
            String modeloImplicado, String colorImplicado, String tipoReporte, String telefono, String estatus,
            String descripcion, String imagen1, String imagen2, String imagen3, String imagen4, String imagen5, String imagen6,
            String imagen7, String imagen8)
        {
            using (TransitoContext dbSS =
                new TransitoContext())
            {
                Reporte reporte = new Reporte();
                reporte.Placa = placa;
                reporte.Latitud = latitud;
                reporte.Longitud = longitud;
                reporte.PlacasImplicado = placasImplicado;
                reporte.NombreImplicado = nombreImplicado;
                reporte.NombreAseguradoraImplicado = nombreAseguradoraImplicado;
                reporte.NumPolizaImplicado = numPolizaImplicado;
                reporte.MarcaImplicado = marcaImplicado;
                reporte.ModeloImplicado = modeloImplicado;
                reporte.ColorImplicado = colorImplicado;
                reporte.TipoReporte = tipoReporte;
                reporte.Telefono = telefono;
                reporte.Estatus = estatus;
                reporte.Descripcion = descripcion;
                reporte.FechaReporte = DateTime.Now;
                dbSS.Reporte.Add(reporte);
                if (dbSS.SaveChanges() == 1)
                {
                    Reporte reporteAux = new Reporte();
                    reporteAux = dbSS.Reporte.Last();
                    if (imagen1 != "")
                    {
                        FotoReporteController frc = new FotoReporteController();
                        frc.Registro(imagen1, reporteAux.IdReporte);
                    }
                    if (imagen2 != "")
                    {
                        FotoReporteController frc = new FotoReporteController();
                        frc.Registro(imagen1, reporteAux.IdReporte);
                    }
                    if (imagen3 != "")
                    {
                        FotoReporteController frc = new FotoReporteController();
                        frc.Registro(imagen1, reporteAux.IdReporte);
                    }
                    if (imagen4 != "")
                    {
                        FotoReporteController frc = new FotoReporteController();
                        frc.Registro(imagen1, reporteAux.IdReporte);
                    }
                    if (imagen5 != "")
                    {
                        FotoReporteController frc = new FotoReporteController();
                        frc.Registro(imagen1, reporteAux.IdReporte);
                    }
                    if (imagen6 != "")
                    {
                        FotoReporteController frc = new FotoReporteController();
                        frc.Registro(imagen1, reporteAux.IdReporte);
                    }
                    if (imagen7 != "")
                    {
                        FotoReporteController frc = new FotoReporteController();
                        frc.Registro(imagen1, reporteAux.IdReporte);
                    }
                    if (imagen8 != "")
                    {
                        FotoReporteController frc = new FotoReporteController();
                        frc.Registro(imagen1, reporteAux.IdReporte);
                    }
                    return "{\"correcto\": \"si\"}";
                }
                else
                    return "{\"correcto\": \"no\"}";
            }
        }

        [HttpPut]
        public String Actualizar(int idReporte, String placa, String latitud, String longitud, String placasImplicado,
            String nombreImplicado, String nombreAseguradoraImplicado, String numPoliza, String marcaImplicado,
            String modeloImplicado, String colorImplicado, String tipoReporte, String telefono, String estatus)
        {
            if (ValidarExistencia(idReporte) == 1)
            {
                using (TransitoContext dbSS =
                new TransitoContext())
                {
                    Reporte reporte = new Reporte();
                    reporte.Placa = placa;
                    reporte.Latitud = latitud;
                    reporte.Longitud = longitud;
                    reporte.PlacasImplicado = placasImplicado;
                    reporte.NombreImplicado = nombreImplicado;
                    reporte.NombreAseguradoraImplicado = nombreAseguradoraImplicado;
                    reporte.ColorImplicado = colorImplicado;
                    reporte.TipoReporte = tipoReporte;
                    reporte.Telefono = telefono;
                    reporte.Estatus = estatus;
                    reporte.FechaReporte = DateTime.Now;
                    dbSS.Reporte.Update(reporte);
                    dbSS.SaveChanges();
                    return "{\"correcto\": \"si\"}";
                }
            }
            else
                return "{\"correcto\": \"no\"}";
        }

        [HttpDelete]
        public String Eliminar(int idReporte)
        {
            using (TransitoContext dbSS =
                    new TransitoContext())
            {
                Reporte reporte = new Reporte();
                reporte.IdReporte = idReporte;
                dbSS.Reporte.Remove(reporte);
                if (dbSS.SaveChanges() == 1)
                {
                    return "{\"correcto\": \"si\"}";
                }
                else
                    return "{\"correcto\": \"no\"}";

            }
        }

        [HttpGet]
        public List<Reporte> ListaReportes()
        {
            List<Reporte> listaReportes = null;
            using (TransitoContext dbSS = new TransitoContext())
            {
                listaReportes = dbSS.Reporte.OrderBy(a => a.FechaReporte).ToList();
            }
            return listaReportes;
        }

        [HttpGet]
        public List<Reporte> ListaReportesConductor(String telefono)
        {
            List<Reporte> listaReportes = null;
            using (TransitoContext dbSS = new TransitoContext())
            {
                listaReportes = dbSS.Reporte.Where(a1 => a1.Telefono.Equals(telefono)).ToList();
            }
            return listaReportes;
        }

        public int ValidarExistencia(int idReporte)
        {
            using (TransitoContext dbSS = new TransitoContext())
            {
                var existe = dbSS.Reporte.Count(al =>
                                al.IdReporte.Equals(idReporte)
                                 );
                return existe;
            }
        }
    }
}