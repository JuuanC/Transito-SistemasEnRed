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
        /*
        [HttpPost]
        public IActionResult Validar(String telefono, String password)
        {
            Perito perito = ValidarUsuario(usuario, password);
            bool valido = perito != null;
            if (valido)
            {
                using (TransitoContext dbSS = new TransitoContext())
                {
                    String token = "";
                    do
                    {
                        token = GenerarToken();
                    } while (dbSS.BitacoraPerito.Any(a => a.Token.Equals(token)));

                    BitacoraPerito registroAcceso = new BitacoraPerito();
                    registroAcceso.Fecha = DateTime.Now;
                    registroAcceso.IdPerito = perito.IdPerito;
                    registroAcceso.Token = token;
                    registroAcceso.Activa = true;
                    dbSS.BitacoraPerito.Add(registroAcceso);
                    dbSS.SaveChanges();

                    byte[] arr = BitConverter.GetBytes(registroAcceso.IdPerito);
                    HttpContext.Session.Set("SesionPerito", arr);
                    HttpContext.Session.Set("Perito",
                     Encoding.ASCII
                     .GetBytes($"{perito.Nombre}"));
                    return new RedirectResult("/Perito/Principal");
                }
            }
            else
                return new RedirectResult("/");
        }*/

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
        public String Actualizar(int idPerito, String usuario,
            String nombre, String contrasenia, String cargo)
        {
            if (ValidarExistencia(usuario) == 1)
            {
                using (TransitoContext dbSS =
                    new TransitoContext())
                {
                    Perito perito = new Perito();
                    perito.IdPerito = idPerito;
                    perito.Usuario = usuario;
                    perito.Nombre = nombre;
                    perito.Contrasenia = contrasenia;
                    perito.Cargo = cargo;
                    dbSS.Perito.Update(perito);
                    dbSS.SaveChanges();
                    return "{\"correcto\": \"si\"}";
                }
            }
            else
                return "{\"correcto\": \"no\"}";
        }

        public Perito ValidarUsuario(String usuario, String password)
        {
            using (TransitoContext dbSS = new TransitoContext())
            {
                var perito = dbSS.Perito.FirstOrDefault(al =>
                                al.Usuario.Equals(usuario) &&
                                      al.Contrasenia.Equals(password)
                                 );
                return perito;
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