using System;
using System.Collections.Generic;

namespace TransitoWeb.Models
{
    public partial class BitacoraConductor
    {
        public int IdBitacora { get; set; }
        public DateTime Fecha { get; set; }
        public string Token { get; set; }
        public bool Activa { get; set; }
        public int IdConductor { get; set; }

        public Conductor IdConductorNavigation { get; set; }
    }
}
