using System;
using System.Collections.Generic;

namespace TransitoWeb.Models
{
    public partial class Reporte
    {
        public Reporte()
        {
            Dictamen = new HashSet<Dictamen>();
            FotoReporte = new HashSet<FotoReporte>();
        }

        public int IdReporte { get; set; }
        public string Placa { get; set; }
        public string Telefono { get; set; }
        public string Latitud { get; set; }
        public string Longitud { get; set; }
        public string PlacasImplicado { get; set; }
        public string NombreImplicado { get; set; }
        public string NombreAseguradoraImplicado { get; set; }
        public string NumPolizaImplicado { get; set; }
        public string MarcaImplicado { get; set; }
        public string ModeloImplicado { get; set; }
        public string ColorImplicado { get; set; }
        public DateTime FechaReporte { get; set; }
        public string TipoReporte { get; set; }
        public string Estatus { get; set; }

        public Conductor TelefonoNavigation { get; set; }
        public ICollection<Dictamen> Dictamen { get; set; }
        public ICollection<FotoReporte> FotoReporte { get; set; }
    }
}
