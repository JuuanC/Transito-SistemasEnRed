using System;
using System.Collections.Generic;

namespace TransitoWeb.Models
{
    public partial class Perito
    {
        public Perito()
        {
            BitacoraPerito = new HashSet<BitacoraPerito>();
            Dictamen = new HashSet<Dictamen>();
        }

        public int IdPerito { get; set; }
        public string Usuario { get; set; }
        public string Nombre { get; set; }
        public string Contrasenia { get; set; }
        public string Cargo { get; set; }

        public ICollection<BitacoraPerito> BitacoraPerito { get; set; }
        public ICollection<Dictamen> Dictamen { get; set; }
    }
}
