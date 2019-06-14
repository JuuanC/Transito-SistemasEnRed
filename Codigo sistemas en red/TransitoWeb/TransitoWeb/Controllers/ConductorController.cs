using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using TransitoWeb.Models;

namespace TransitoWeb.Controllers
{
    public class ConductorController : Controller
    {
        
        [HttpPost]
        public String Registro(String nombre, String fechaNacimiento, 
            String numLicencia, String telefono, String contrasenia)
        {
            if (true)
            {
                using (TransitoContext dbSS =
                    new TransitoContext())
                {
                    Conductor conductor = new Conductor();
                    conductor.Nombre = nombre;
                    conductor.FechaNacimiento = fechaNacimiento;
                    conductor.NumLicencia = numLicencia;
                    conductor.Telefono = telefono;
                    conductor.Contrasenia = contrasenia;
                    dbSS.Conductor.Add(conductor);
                    dbSS.SaveChanges();
                    return "{\"correcto\": \"si\"}";
                }
            }
            else
                return "{\"correcto\": \"no\", \"motivo\":1}";
        }
    }
}