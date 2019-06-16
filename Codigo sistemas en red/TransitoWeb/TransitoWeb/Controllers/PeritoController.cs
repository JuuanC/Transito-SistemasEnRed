using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using TransitoWeb.Models;

namespace TransitoWeb.Controllers
{
    public class PeritoController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
        
        [HttpGet]
        public IActionResult Validar(String usuario, String password)
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
        }

        [HttpPost]
        public String Registro(String usuario,
            String nombre, String contrasenia, String cargo)
        {
            if (ValidarExistencia(usuario) == 0)
            {
                using (TransitoContext dbSS =
                    new TransitoContext())
                {
                    Perito perito = new Perito();
                    perito.Usuario = usuario;
                    perito.Nombre = nombre;
                    perito.Contrasenia = contrasenia;
                    perito.Cargo = cargo;
                    dbSS.Perito.Add(perito);
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

        [HttpDelete]
        public String Eliminar(int idPerito)
        {
            using (TransitoContext dbSS =
                    new TransitoContext())
            {
                Perito perito = new Perito();
                perito.IdPerito = idPerito;
                dbSS.Perito.Remove(perito);
                if(dbSS.SaveChanges() == 1)
                {
                    return "{\"correcto\": \"si\"}";
                }
                else
                    return "{\"correcto\": \"no\"}";

            }
        }

        [HttpGet]
        public List<Perito> ListaPeritos()
        {
            byte[] arr = new byte[100];
            List<Perito> listaPeritos = null;
            if (HttpContext.Session.TryGetValue("SesionPerito", out arr))
            {
                using (TransitoContext dbSS = new TransitoContext())
                {
                    listaPeritos = dbSS.Perito
                             .OrderBy(a => a.IdPerito)
                             .ToList();
                }
                
            }
            return listaPeritos;
        }
        public IActionResult Principal()
        {
            byte[] arr = new byte[100];
            if (HttpContext.Session.TryGetValue("SesionPerito", out arr))
            {
                int idSesion = BitConverter.ToInt32(arr, 0);
                HttpContext.Session.TryGetValue("Perito", out arr);
                String nombre = Encoding.ASCII.GetString(arr);

                using (TransitoContext dbSS = new TransitoContext())
                {
                    BitacoraPerito registro =
                        dbSS.BitacoraPerito
                        .FirstOrDefault(b => b.IdBitacora == idSesion);
                    if (registro != null && registro.Activa == true)
                    {
                        Perito perito = dbSS.Perito
                            .FirstOrDefault(a => a.IdPerito == registro.IdPerito);
                        ViewBag.idSesion = idSesion;
                        ViewBag.Perito = perito;
                        return View("Principal");
                    }
                    else
                        return new RedirectResult("/");

                }
            }
            else
                return new RedirectResult("/");
        }

        public IActionResult AdministrarPeritos(int idSesion)
        {
            byte[] arr = new byte[100];
            if (HttpContext.Session.TryGetValue("SesionPerito", out arr))
            {
                using (TransitoContext dbSS = new TransitoContext())
                {
                    BitacoraPerito registro =
                        dbSS.BitacoraPerito
                        .FirstOrDefault(b => b.IdBitacora == idSesion);
                    if (registro != null && registro.Activa == true)
                    {
                        List<Perito> peritos = dbSS.Perito.ToList();
                        ViewBag.idSesion = idSesion;
                        ViewBag.Peritos = peritos;
                        return View("AdministrarPeritos");
                    }
                    else
                        return new RedirectResult("/");

                }
            }
            else
                return new RedirectResult("/");
        }

        public IActionResult VisualizarReportes(int idSesion)
        {
            byte[] arr = new byte[100];
            if (HttpContext.Session.TryGetValue("SesionPerito", out arr))
            {
                using (TransitoContext dbSS = new TransitoContext())
                {
                    BitacoraPerito registro =
                        dbSS.BitacoraPerito
                        .FirstOrDefault(b => b.IdBitacora == idSesion);
                    if (registro != null && registro.Activa == true)
                    {
                        Perito perito = dbSS.Perito
                            .FirstOrDefault(a => a.IdPerito == registro.IdPerito);
                        ViewBag.idSesion = idSesion;
                        ViewBag.Perito = perito;
                        return View("VisualizarReportes");
                    }
                    else
                        return new RedirectResult("/");

                }
            }
            else
                return new RedirectResult("/");
        }

        public IActionResult VerDetalle(int idSesion)
        {
            byte[] arr = new byte[100];
            if (HttpContext.Session.TryGetValue("SesionPerito", out arr))
            {
                using (TransitoContext dbSS = new TransitoContext())
                {
                    BitacoraPerito registro =
                        dbSS.BitacoraPerito
                        .FirstOrDefault(b => b.IdBitacora == idSesion);
                    if (registro != null && registro.Activa == true)
                    {
                        Perito perito = dbSS.Perito
                            .FirstOrDefault(a => a.IdPerito == registro.IdPerito);
                        ViewBag.idSesion = idSesion;
                        ViewBag.Perito = perito;
                        return View("VerDetalle");
                    }
                    else
                        return new RedirectResult("/");

                }
            }
            else
                return new RedirectResult("/");
        }

        public int ValidarExistencia(String usuario)
        {
            using (TransitoContext dbSS = new TransitoContext())
            {
                var existe = dbSS.Perito.Count(al =>
                                al.Usuario.Equals(usuario) 
                                 );
                return existe;
            }
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
    }
}