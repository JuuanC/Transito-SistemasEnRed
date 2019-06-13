using System;
using System.Collections.Generic;

namespace TransitoWeb.Models
{
    public partial class EstadoReporte
    {
        public int IdEstado { get; set; }
        public DateTime Fecha { get; set; }
        public string Nombre { get; set; }
        public int IdReporte { get; set; }

        public Reporte IdReporteNavigation { get; set; }
    }
}
