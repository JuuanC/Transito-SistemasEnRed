using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using TransitoWeb.Models;

namespace TransitoWeb.Controllers
{
    public class FotoReporteController : Controller
    {
        [HttpGet]
        public String Registro(String imagen, int idReporte)
        {
            using (TransitoContext dbSS = new TransitoContext())
            {
                byte[] archivo = System.Convert.FromBase64String(imagen);
                FotoReporte foto = new FotoReporte();
                foto.IdReporte = idReporte;
                foto.Imagen = archivo;
                dbSS.FotoReporte.Add(foto);
                if (dbSS.SaveChanges() == 1)
                {
                    return "{\"si\"}";
                }
                else
                    return "{\"no\"}";
            }
        }

        [HttpPost]
        public List<FotoReporte> listaFotos(int idReporte)
        {
            List<FotoReporte> fotos = null;
            using (TransitoContext dbSS = new TransitoContext())
            {
                fotos = dbSS.FotoReporte
                         .Where(a => a.IdReporte == idReporte)
                         .ToList();
            }
            return fotos;
        }

    }
}