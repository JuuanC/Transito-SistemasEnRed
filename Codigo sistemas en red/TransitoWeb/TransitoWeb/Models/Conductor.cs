using System;
using System.Collections.Generic;

namespace TransitoWeb.Models
{
    public partial class Conductor
    {
        public Conductor()
        {
            BitacoraConductor = new HashSet<BitacoraConductor>();
            Reporte = new HashSet<Reporte>();
            Vehiculo = new HashSet<Vehiculo>();
        }

        public string Telefono { get; set; }
        public string Nombre { get; set; }
        public string FechaNacimiento { get; set; }
        public string NumLicencia { get; set; }
        public string Contrasenia { get; set; }

        public ICollection<BitacoraConductor> BitacoraConductor { get; set; }
        public ICollection<Reporte> Reporte { get; set; }
        public ICollection<Vehiculo> Vehiculo { get; set; }
    }
}
