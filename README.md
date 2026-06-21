TRABAJO PRACTICO INTEGRADO "FOOD STORE - SISTEMA DE GESTION DE PEDIDOS CON COMIDAS

Aplicación desarrollada en Java con Maven, basada en arquitectura en capas (DAO, Service, Entities),
que implementa operaciones CRUD con validaciones de negocio y persistencia de datos. 
El es un sistema de pedidos con:
°Categorias
°Usuarios
°Productos
°Pedidos

En donde a traves de la consola vamos a ir creando y generando pedidos. Estos pedidos
van a ir alamacenandose en una base de datos la cual tambien va ir guardando todo lo que creemos
como por ejemplo las catergorias  y/O usuarios. Con este proyecto no solo vamos a crear pedidos, 
tambien podemos ver una lista de lo que tenemos creado, editar por si cometimos un error y eliminar
pedidos,categorias,usuarios o productos que ya no sean necesarios


==== Tecnologias empleadas ====
- Java
- Maven
- MySQL 
- Git / GitHub

==== Instalación y ejecución ====
Pasos:
1) creacion de base de datos
   copiar el codigo de sql que se encuentra en el archivo "schemas.sql"
   pegar ese codigo en el editor SQL
   ejecutar el codigo. eso ya habra creado la tabla para ejecutar correctamente el codigo

2) configuracion de codigos
   ir a la carpeta "config" del codigo
   dentro del archivo "DatabaseConnectionPool.java" aproximadamente en la linea 18 donde dice "config.setPassword("1234567");" colocar la contraseña que tengas en mysql workbench dentro del parentesis dentro de las comillas
   eso para que java pueda acceder a la base de datos

3) Ejecutar el codigo
   En el archivo "Main.java" aproximadamente en la linea 8 donde dice "public static void main(String[] args)..." Darle click al biton de la derecha de color verde
   saldra el menu en la terminal
   dentro del menu moverse con los numeros correspondiente al trabajo que quieras hacer, por ejemplo 1 para categorias
   al seleccionarlo saldra un nuevo menu en donde podras elegir de la misma manera (con numeros) el trabajo a realizar, por ejemplo 2 en donde podras crear una categoria
   


   
Clonar el repositorio
```bash
git clone [https://github.com/tuusuario/tu-repo.git](https://github.com/ElIsuss/TRABAJO-PRACTICO-INTEGRADOR---UTN---1PRO4---MENDOZA).git

