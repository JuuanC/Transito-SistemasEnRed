using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using TransitoWeb.Models;

namespace TransitoWeb.Controllers
{
    public class DictamenController : Controller
    {
        [HttpPost]
        public String Registro(String descripcion, int idPerito, int idReporte)
        {
            using (TransitoContext dbSS =
                new TransitoContext())
            {
                Dictamen dictamen = new Dictamen();
                dictamen.Descripcion = descripcion;
                dictamen.IdPerito = idPerito;
                dictamen.IdReporte = idReporte;
                dictamen.Fecha = DateTime.Now;
                dbSS.Dictamen.Add(dictamen);
                if (dbSS.SaveChanges() == 1)
                {
                    Reporte reporte = new Reporte();
                    reporte = dbSS.Reporte.Find(idReporte);
                    reporte.Estatus = "Dictaminado";
                    dbSS.Reporte.Update(reporte);
                    if(dbSS.SaveChanges() == 1)
                    {
                        return "{\"correcto\": \"si\"}";
                    }
                    else
                    {
                        return "{\"correcto\": \"no\"}";
                    }
                }
                else
                    return "{\"correcto\": \"no\"}";
            }
        }

        [HttpGet]
        public Dictamen ObtenerDictamen(int idReporte)
        {
            Dictamen dictamen = new Dictamen();
            using (TransitoContext dbSS = new TransitoContext())
            {
                dictamen = dbSS.Dictamen.FirstOrDefault(dic => dic.IdReporte == idReporte);
            }
            return dictamen;
        }
    }
}