using System;
using System.Collections.Generic;

namespace TransitoWeb.Models
{
    public partial class FotoReporte
    {
        public int Idfoto { get; set; }
        public byte[] Imagen { get; set; }
        public int IdReporte { get; set; }

        public Reporte IdReporteNavigation { get; set; }
    }
}
