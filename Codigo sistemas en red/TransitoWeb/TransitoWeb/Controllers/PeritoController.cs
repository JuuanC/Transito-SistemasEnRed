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

        [HttpPost]
        public IActionResult Validar(String email, String password)
        {
            Perito perito = ValidarUsuario(email, password);
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

        public IActionResult Principal()
        {

            return View("Principal");
        }



        public Perito ValidarUsuario(String login, String password)
        {
            using (TransitoContext dbSS = new TransitoContext())
            {
                var perito = dbSS.Perito.FirstOrDefault(al =>
                                al.Usuario.Equals(login) &&
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