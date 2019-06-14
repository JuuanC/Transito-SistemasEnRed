using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace TransitoWeb.Models
{
    public partial class TransitoContext : DbContext
    {
        public TransitoContext()
        {
        }

        public TransitoContext(DbContextOptions<TransitoContext> options)
            : base(options)
        {
        }

        public virtual DbSet<BitacoraConductor> BitacoraConductor { get; set; }
        public virtual DbSet<BitacoraPerito> BitacoraPerito { get; set; }
        public virtual DbSet<Conductor> Conductor { get; set; }
        public virtual DbSet<Dictamen> Dictamen { get; set; }
        public virtual DbSet<FotoReporte> FotoReporte { get; set; }
        public virtual DbSet<Perito> Perito { get; set; }
        public virtual DbSet<Reporte> Reporte { get; set; }
        public virtual DbSet<Vehiculo> Vehiculo { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. See http://go.microsoft.com/fwlink/?LinkId=723263 for guidance on storing connection strings.
                optionsBuilder.UseSqlServer("Server=localhost;Database=Transito;Uid=transito;Pwd=12345;");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<BitacoraConductor>(entity =>
            {
                entity.HasKey(e => e.IdBitacora);

                entity.ToTable("Bitacora_Conductor");

                entity.Property(e => e.IdBitacora).HasColumnName("idBitacora");

                entity.Property(e => e.Activa).HasColumnName("activa");

                entity.Property(e => e.Fecha)
                    .HasColumnName("fecha")
                    .HasColumnType("datetime");

                entity.Property(e => e.IdConductor).HasColumnName("idConductor");

                entity.Property(e => e.Token)
                    .IsRequired()
                    .HasColumnName("token")
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.HasOne(d => d.IdConductorNavigation)
                    .WithMany(p => p.BitacoraConductor)
                    .HasForeignKey(d => d.IdConductor)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Bitacora_Conductor2_Conductor");
            });

            modelBuilder.Entity<BitacoraPerito>(entity =>
            {
                entity.HasKey(e => e.IdBitacora);

                entity.ToTable("Bitacora_Perito");

                entity.Property(e => e.IdBitacora).HasColumnName("idBitacora");

                entity.Property(e => e.Activa).HasColumnName("activa");

                entity.Property(e => e.Fecha)
                    .HasColumnName("fecha")
                    .HasColumnType("datetime");

                entity.Property(e => e.IdPerito).HasColumnName("idPerito");

                entity.Property(e => e.Token)
                    .IsRequired()
                    .HasColumnName("token")
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.HasOne(d => d.IdPeritoNavigation)
                    .WithMany(p => p.BitacoraPerito)
                    .HasForeignKey(d => d.IdPerito)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Bitacora_Perito_Perito1");
            });

            modelBuilder.Entity<Conductor>(entity =>
            {
                entity.HasKey(e => e.IdConductor);

                entity.Property(e => e.IdConductor).HasColumnName("idConductor");

                entity.Property(e => e.Contrasenia)
                    .IsRequired()
                    .HasColumnName("contrasenia")
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.FechaNacimiento)
                    .IsRequired()
                    .HasColumnName("fechaNacimiento")
                    .HasMaxLength(10)
                    .IsUnicode(false);

                entity.Property(e => e.Nombre)
                    .IsRequired()
                    .HasColumnName("nombre")
                    .HasMaxLength(60)
                    .IsUnicode(false);

                entity.Property(e => e.NumLicencia)
                    .IsRequired()
                    .HasColumnName("numLicencia")
                    .HasMaxLength(40)
                    .IsUnicode(false);

                entity.Property(e => e.Telefono)
                    .IsRequired()
                    .HasColumnName("telefono")
                    .HasMaxLength(10)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<Dictamen>(entity =>
            {
                entity.HasKey(e => e.IdFolio);

                entity.Property(e => e.IdFolio).HasColumnName("idFolio");

                entity.Property(e => e.Descripcion)
                    .IsRequired()
                    .HasColumnName("descripcion")
                    .HasMaxLength(250)
                    .IsUnicode(false);

                entity.Property(e => e.Fecha)
                    .HasColumnName("fecha")
                    .HasColumnType("datetime");

                entity.Property(e => e.IdPerito).HasColumnName("idPerito");

                entity.Property(e => e.IdReporte).HasColumnName("idReporte");

                entity.HasOne(d => d.IdPeritoNavigation)
                    .WithMany(p => p.Dictamen)
                    .HasForeignKey(d => d.IdPerito)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Dictamen_Perito");

                entity.HasOne(d => d.IdReporteNavigation)
                    .WithMany(p => p.Dictamen)
                    .HasForeignKey(d => d.IdReporte)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Dictamen_Reporte");
            });

            modelBuilder.Entity<FotoReporte>(entity =>
            {
                entity.HasKey(e => e.Idfoto);

                entity.ToTable("Foto_Reporte");

                entity.Property(e => e.Idfoto).HasColumnName("idfoto");

                entity.Property(e => e.IdReporte).HasColumnName("idReporte");

                entity.Property(e => e.Imagen)
                    .IsRequired()
                    .HasColumnName("imagen")
                    .HasColumnType("image");

                entity.HasOne(d => d.IdReporteNavigation)
                    .WithMany(p => p.FotoReporte)
                    .HasForeignKey(d => d.IdReporte)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Foto_Reporte_Reporte");
            });

            modelBuilder.Entity<Perito>(entity =>
            {
                entity.HasKey(e => e.IdPerito);

                entity.Property(e => e.IdPerito).HasColumnName("idPerito");

                entity.Property(e => e.Cargo)
                    .IsRequired()
                    .HasColumnName("cargo")
                    .HasMaxLength(60)
                    .IsUnicode(false);

                entity.Property(e => e.Contrasenia)
                    .IsRequired()
                    .HasColumnName("contrasenia")
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.Property(e => e.Nombre)
                    .IsRequired()
                    .HasColumnName("nombre")
                    .HasMaxLength(60)
                    .IsUnicode(false);

                entity.Property(e => e.Usuario)
                    .IsRequired()
                    .HasColumnName("usuario")
                    .HasMaxLength(20)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<Reporte>(entity =>
            {
                entity.HasKey(e => e.IdReporte);

                entity.Property(e => e.IdReporte).HasColumnName("idReporte");

                entity.Property(e => e.ColorImplicado)
                    .HasColumnName("colorImplicado")
                    .HasMaxLength(25)
                    .IsUnicode(false);

                entity.Property(e => e.Estatus)
                    .IsRequired()
                    .HasColumnName("estatus")
                    .HasMaxLength(20)
                    .IsUnicode(false);

                entity.Property(e => e.FechaReporte)
                    .HasColumnName("fechaReporte")
                    .HasColumnType("datetime");

                entity.Property(e => e.IdConductor).HasColumnName("idConductor");

                entity.Property(e => e.Latitud)
                    .IsRequired()
                    .HasColumnName("latitud")
                    .HasMaxLength(500);

                entity.Property(e => e.Longitud)
                    .IsRequired()
                    .HasColumnName("longitud")
                    .HasMaxLength(500);

                entity.Property(e => e.MarcaImplicado)
                    .HasColumnName("marcaImplicado")
                    .HasMaxLength(20)
                    .IsUnicode(false);

                entity.Property(e => e.ModeloImplicado)
                    .HasColumnName("modeloImplicado")
                    .HasMaxLength(20)
                    .IsUnicode(false);

                entity.Property(e => e.NombreImplicado)
                    .HasColumnName("nombreImplicado")
                    .HasMaxLength(60)
                    .IsUnicode(false);

                entity.Property(e => e.Placa)
                    .IsRequired()
                    .HasColumnName("placa")
                    .HasMaxLength(10)
                    .IsUnicode(false);

                entity.Property(e => e.PlacasImplicado)
                    .HasColumnName("placasImplicado")
                    .HasMaxLength(10)
                    .IsUnicode(false);

                entity.Property(e => e.PolizaImplicado)
                    .HasColumnName("polizaImplicado")
                    .HasMaxLength(20)
                    .IsUnicode(false);

                entity.Property(e => e.TipoReporte)
                    .IsRequired()
                    .HasColumnName("tipoReporte")
                    .HasMaxLength(45)
                    .IsUnicode(false);

                entity.HasOne(d => d.IdConductorNavigation)
                    .WithMany(p => p.Reporte)
                    .HasForeignKey(d => d.IdConductor)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Reporte_Conductor");
            });

            modelBuilder.Entity<Vehiculo>(entity =>
            {
                entity.HasKey(e => e.Placa);

                entity.Property(e => e.Placa)
                    .HasColumnName("placa")
                    .HasMaxLength(10)
                    .IsUnicode(false)
                    .ValueGeneratedNever();

                entity.Property(e => e.Anio)
                    .IsRequired()
                    .HasColumnName("anio")
                    .HasMaxLength(4)
                    .IsUnicode(false);

                entity.Property(e => e.Color)
                    .IsRequired()
                    .HasColumnName("color")
                    .HasMaxLength(25)
                    .IsUnicode(false);

                entity.Property(e => e.IdConductor).HasColumnName("idConductor");

                entity.Property(e => e.Marca)
                    .IsRequired()
                    .HasColumnName("marca")
                    .HasMaxLength(20)
                    .IsUnicode(false);

                entity.Property(e => e.Modelo)
                    .IsRequired()
                    .HasColumnName("modelo")
                    .HasMaxLength(20)
                    .IsUnicode(false);

                entity.Property(e => e.NombreAseguradora)
                    .HasColumnName("nombreAseguradora")
                    .HasMaxLength(60)
                    .IsUnicode(false);

                entity.Property(e => e.NumPoliza)
                    .HasColumnName("numPoliza")
                    .HasMaxLength(20)
                    .IsUnicode(false);

                entity.HasOne(d => d.IdConductorNavigation)
                    .WithMany(p => p.Vehiculo)
                    .HasForeignKey(d => d.IdConductor)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("FK_Vehiculo_Conductor");
            });
        }
    }
}
