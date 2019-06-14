using System;
using System.Collections.Generic;

namespace TransitoWeb.Models
{
    public partial class BitacoraPerito
    {
        public int IdBitacora { get; set; }
        public DateTime Fecha { get; set; }
        public string Token { get; set; }
        public bool Activa { get; set; }
        public int IdPerito { get; set; }

        public Perito IdPeritoNavigation { get; set; }
    }
}
