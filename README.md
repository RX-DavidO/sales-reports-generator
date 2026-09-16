# Generador de archivos de prueba

Proyecto correspondiente a la primera entrega del módulo de reportes de ventas.

## Descripción

La clase `GenerateInfoFiles` genera archivos planos de prueba para el programa principal de reportes de ventas.

Al ejecutarla, se crean los siguientes archivos dentro de la carpeta `data`:

- `products.txt`: información de productos.
- `salesmen.txt`: información de vendedores.
- `sales_Vendedor_...txt`: archivos de ventas, uno por cada vendedor.

## Ejecución

1. Abrir el proyecto con Eclipse.
2. Ubicar la clase `GenerateInfoFiles`.
3. Hacer clic derecho sobre la clase.
4. Seleccionar `Run As` → `Java Application`.

El programa mostrará un mensaje de éxito y generará los archivos de prueba en la carpeta `data`.

## Métodos principales

- `createProductsFile(int productsCount)`
- `createSalesManInfoFile(int salesmanCount)`
- `createSalesMenFile(int randomSalesCount, String name, long id)`
