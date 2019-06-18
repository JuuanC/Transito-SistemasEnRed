# Transito-SistemasEnRed
Seguimiento de accidentes vehiculares para Transito.

# Estructura del repositorio:
Dentro de este repositorio se encontrarán 3 carpetas generales del proyecto.

1. Código Sistemas en red.
      Contiene todo el proyecto en la parte web creado en el IDE VisualStudio Enterprise.
2. Código de aplicación.
      Contiene todo el proyecto de la parte móvil creado en el IDE AndroidStudio.
3. Documentación.
       Contiene todo el diseño del proyecto, prototipos, casos de uso, descripciones de casos de uso, diagramas de robustez, diagramas de secuencia, diagrama de clases, modelo de datos, diagrama de despliegue, arquitectura del sistema y un documento "DOCUMENTO.dox" en el cual se detallen todos los diagramasm, plan de pruebas, justificación de frameworks, tecnologías, estándares de codificación y aseguramiento de la calidad, también puede ser visualizado en el documento con formato pdf.
       En la carpeta de Modelo relacional tambien se cuentran los script para la base de datos, con todos los elementos de prueba que se utilizaron, para probar los servicios web.
       

# Alcance de la aplicación móvil

La aplicación móvil cumple con la meta establecida en el planteamiento del proyecto consumiento una web api que también forma parte del proyecto. Con ayuda del framework Volley se crean peticiones al servidor de una forma sencilla y eficaz.

Metas principales conseguidas:

  01. Registro de conductores desde la aplicación móvil mediante petición POST.
  02. Inicio de sesión exitosa si el usuario está registrado en el sistema introduciendo teléfono y contraseña mediante petición GET.
  03. Registro nuevos vehículos con placas por cada conductor, introduciendo placas, marca, modelo, año, color mediante petición POST.
  04. Actualización de vehículos mediante petición PUT.
  05. Eliminación de vehículos mediante petición DELETE.
  06. Modificación de datos del conductor mediante petición PUT.
  07. Consulta de todos los vehículos por conductor mediante petición GET.
  08. Registro de reportes introduciendo placas, marca, modelo, color, nombre del conductor implicado mediante petición POST.
  09. Consulta de reportes generados por el conductor mediante petición GET.
  10. Consulta de dictamenes realzadas por un perito mediante petición GET.
  11. Obtención de latitud y longitud en tiempo real mediante el GPS del dispositivo móvil.
  12. Uso de la cámara del dispositivo para tomar fotos del accidente 4 fotos mínimo 8 máximos. (Validado)

Metas segundarias conseguidas :

  1. Reporte diferente para registrar algún error en la vía pública como baches que afecten el curso de los vehículos.
  2. Levantar reporte a causa de semáforos descompuestos que presenten un peligro o atraso a los vehículos o peatones.
  3. Validaciones de entrada para campos nulos, máximo de caracteres en los campos.
    
Metas no conseguidas :
  1. Envío de fotos al servidor. Se trató de enviar las fotos como cadena de código en base 64, sin embargo nunca se pudo realizar.
  2. Bugs de recargas en interfaz gráfica. Si el conductor realiza un registro, en varias partes no se actualiza la información, tendrá que salir y entrar nuevamente a la app para que haga una consulta al servidor y vea todos sus registros correctamente.
  3. Excepciones generales en conexión al servidor. Sea cual sea el tipo de error cuando se realiza la petición, ya sea por parte del cliente, por tiempo de respuesta, error en bd, error en el servidor, se manejan como un tipo de error general y solo se despliega el mensaje "Hubo un error de conexión".
    
# Alcance del sistema web

El sistema web, contiene todo los servicios web suficientes para que la aplicacion móvil y el sistema web pueden operar de manera eficiente. Sin embargo la aplicacion web no se pudieron implementar todos los servicios que este ofrece, hizo falta poder actualizar y eliminar un perito, y también hizo falta el poder dictaminar desde el sistema web, auqnue lo servicios fueron probados mediante la herramienta de Postman y funcionan correctamente, no se alconzo a consumir ese servicio desde el sistema web, esto principalmente no se logro por falta de conocimientos sobre el manejo de ventanas modales en HTML y como sincronizarla con los servicios web.

# Alcance de la base de dats

Se implemento la base de datos de manera correcta y completa, no hizo falta ninguna tabla o algun atributo a alguna tabla, fue implementada en SQL Server y se pudo hacer el mapeo desde Visual Studio 2017 de manera exitosa.

# Servicios web

Todo los servicios web realizados son los suficientes para que el sistema web como el móvil funcionen correctamente, estos servicios fueron probados de manera local y remota con la herramienta Postman y posteriormente fueron utilizados por el sistema móvil de manera remota.
Se utilizaron de manera remota ya que el sistema web con sus servicios fueron publicados en el IIS de la maniquina servidor y todo funciono con exito.
    

    
    
    
    
    
