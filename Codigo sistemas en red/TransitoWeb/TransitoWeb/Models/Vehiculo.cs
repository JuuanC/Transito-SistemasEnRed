using System;
using System.Collections.Generic;

namespace TransitoWeb.Models
{
    public partial class Vehiculo
    {
        public string Placa { get; set; }
        public string Telefono { get; set; }
        public string Marca { get; set; }
        public string Modelo { get; set; }
        public string Anio { get; set; }
        public string Color { get; set; }
        public string NombreAseguradora { get; set; }
        public string NumPoliza { get; set; }

        public Conductor TelefonoNavigation { get; set; }
    }
}
