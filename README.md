# Sistema de reportes de ventas

Proyecto final del módulo de reportes de ventas. El programa genera archivos de prueba, procesa la información de productos, vendedores y ventas, y crea reportes ordenados.

## Clases ejecutables

El proyecto tiene exactamente dos clases con método `main`:

- `GenerateInfoFiles`: genera archivos planos pseudoaleatorios de productos, vendedores y ventas.
- `main`: lee los archivos generados y crea los reportes de ventas.

Las demás clases, como `Product` y `Salesman`, son clases auxiliares y no tienen método `main`.

## Ejecución

Los programas deben ejecutarse en este orden:

1. Abrir el proyecto en Eclipse.
2. Ejecutar `GenerateInfoFiles` con `Run As` → `Java Application`.
3. El programa generará los archivos de entrada dentro de la carpeta `data`.
4. Ejecutar `main` con `Run As` → `Java Application`.
5. El programa creará los reportes solicitados.

Ninguno de los programas solicita información al usuario.

## Archivos generados

Al ejecutar `GenerateInfoFiles` se crean:

- `products.txt`: información de productos.
- `salesmen.txt`: información de vendedores.
- `sales_Vendedor_...txt`: archivos de ventas, uno por vendedor.

Al ejecutar `main` se crean:

- `salesmen_report.csv`: vendedores ordenados de mayor a menor según el dinero recaudado.
- `products_report.csv`: productos ordenados de mayor a menor según la cantidad vendida.

## Documentación

El código incluye documentación JavaDoc en sus clases y métodos principales.

El archivo `conslusion.txt` contiene un resumen de los aprendizajes, aplicaciones profesionales y dificultades presentadas durante el desarrollo del proyecto.
