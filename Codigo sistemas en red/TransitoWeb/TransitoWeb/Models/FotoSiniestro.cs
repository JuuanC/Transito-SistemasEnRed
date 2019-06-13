using System;
using System.Collections.Generic;

namespace TransitoWeb.Models
{
    public partial class FotoSiniestro
    {
        public int IdFoto { get; set; }
        public DateTime Fecha { get; set; }
        public int NumFoto { get; set; }
        public int IdReporte { get; set; }

        public Reporte IdReporteNavigation { get; set; }
    }
}
