using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using TransitoWeb.Models;

namespace TransitoWeb.Controllers
{
    public class ConductorController : Controller
    {
        
        [HttpPost]
        public String Validar(String telefono, String password)
        {
            Conductor conductor = ValidarUsuario(telefono, password);
            bool valido = conductor != null;
            if (valido)
            {
                using (TransitoContext dbSS = new TransitoContext())
                {
                    String token = "";
                    do
                    {
                        token = GenerarToken();
                    } while (dbSS.BitacoraConductor.Any(a => a.Token.Equals(token)));

                    BitacoraConductor registroAcceso = new BitacoraConductor();
                    registroAcceso.Fecha = DateTime.Now;
                    registroAcceso.Telefono = conductor.Telefono;
                    registroAcceso.Token = token;
                    registroAcceso.Activa = true;
                    dbSS.BitacoraConductor.Add(registroAcceso);
                    dbSS.SaveChanges();

                    byte[] arr = Encoding.ASCII.GetBytes(registroAcceso.Telefono);
                    HttpContext.Session.Set("SesionConductor", arr);
                    HttpContext.Session.Set("Conductor",Encoding.ASCII
                     .GetBytes($"{conductor.Nombre}"));
                    return "{\"correcto\": \"si\"}";
                }
            }
            else
                return "{\"correcto\": \"no\"}";
        }

        [HttpPost]
        public String Registro(String nombre, String fechaNacimiento, 
            String numLicencia, String telefono, String contrasenia)
        {
            if (ValidarExistencia(telefono) == 0)
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
                return "{\"correcto\": \"no\"}";
        }

        [HttpPut]
        public String Actualizar(String nombre, String fechaNacimiento,
            String numLicencia, String telefono, String contrasenia)
        {
            if (ValidarExistencia(telefono) == 1)
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
                    dbSS.Conductor.Update(conductor);
                    dbSS.SaveChanges();
                    return "{\"correcto\": \"si\"}";
                }
            }
            else
                return "{\"correcto\": \"no\"}";
        }

        public Conductor ValidarUsuario(String telefono, String password)
        {
            using (TransitoContext dbSS = new TransitoContext())
            {
                var conductor = dbSS.Conductor.FirstOrDefault(al =>
                                al.Telefono.Equals(telefono) &&
                                      al.Contrasenia.Equals(password)
                                 );
                return conductor;
            }

        }

        private String GenerarToken()
        {
            Random random = new Random();
            const string chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
            return new string(Enumerable.Repeat(chars, 45)
                    .Select(s => s[random.Next(s.Length)]).ToArray());

        }

        public int ValidarExistencia(String telefono)
        {
            using (TransitoContext dbSS = new TransitoContext())
            {
                var existe = dbSS.Conductor.Count(al =>
                                al.Telefono.Equals(telefono)
                                 );
                return existe;
            }
        }
    }
}