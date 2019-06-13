﻿using System;
using System.Collections.Generic;

namespace TransitoWeb.Models
{
    public partial class Reporte
    {
        public Reporte()
        {
            Dictamen = new HashSet<Dictamen>();
            EstadoReporte = new HashSet<EstadoReporte>();
            FotoSiniestro = new HashSet<FotoSiniestro>();
        }

        public int IdReporte { get; set; }
        public string Placa { get; set; }
        public int IdConductor { get; set; }
        public string Latitud { get; set; }
        public string Longitud { get; set; }
        public string PlaclasImplicado { get; set; }
        public string NombreImplicado { get; set; }
        public string PolizaImplicado { get; set; }
        public string MarcaImplicado { get; set; }
        public string ModeloImplicado { get; set; }
        public string ColorImplicado { get; set; }
        public DateTime FechaReporte { get; set; }
        public string TipoReporte { get; set; }

        public Conductor IdConductorNavigation { get; set; }
        public Vehiculo PlacaNavigation { get; set; }
        public ICollection<Dictamen> Dictamen { get; set; }
        public ICollection<EstadoReporte> EstadoReporte { get; set; }
        public ICollection<FotoSiniestro> FotoSiniestro { get; set; }
    }
}
