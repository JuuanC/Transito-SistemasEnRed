using System;
using System.Collections.Generic;

namespace TransitoWeb.Models
{
    public partial class Dictamen
    {
        public int IdFolio { get; set; }
        public string Descripcion { get; set; }
        public DateTime Fecha { get; set; }
        public int IdPerito { get; set; }
        public int IdReporte { get; set; }

        public Perito IdPeritoNavigation { get; set; }
        public Reporte IdReporteNavigation { get; set; }
    }
}
