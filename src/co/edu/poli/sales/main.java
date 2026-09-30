package co.edu.poli.sales;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.DirectoryStream;
import java.io.BufferedWriter;
import java.util.ArrayList;

/**
 * Procesa los archivos de entrada y genera los reportes de ventas.
 *
 * @author David Felipe Olarte Carmona
 * @author Maria Alejandra Cardenas Guzman
 * @author Laura Vanessa Romero Jimenez
 * @author Martha Liliana Salazar Betancur
 * @version 1.0
 */
public class main {

    private static final String DATA_FOLDER = "data";
    private static final String PRODUCTS_FILE = "products.txt";
    private static final String SALESMEN_FILE = "salesmen.txt";
    private static final String SALESMEN_REPORT_FILE = "salesmen_report.csv";
    private static final String PRODUCTS_REPORT_FILE = "products_report.csv";

    /**
     * Punto de inicio del programa de generación de reportes.
     *
     * @param args argumentos de línea de comandos. No se usan.
     */
    public static void main(String[] args) {
        try {
            Map<String, Product> products = readProductsFile();
            Map<Long, Salesman> salesmen = readSalesmenFile();
            Map<Long, Long> salesmenRevenue = calculateSalesmenRevenue(
            		products, salesmen);
            
            createSalesmenReport(salesmen, salesmenRevenue);
            
            Map<String, Integer> productQuantities = calculateProductsQuantity(products);

            createProductsReport(products, productQuantities);

            System.out.println("Reporte de productos creado correctamente.");
            System.out.println("Productos leídos correctamente: "+ products.size());
            System.out.println("Vendedores leídos correctamente: "+ salesmen.size());
            System.out.println("Ventas procesadas correctamente.");
            System.out.println("Reporte de vendedores creado correctamente.");
        } catch (IOException e) {
        	System.out.println("Ocurrió un error al procesar los archivos.");
            e.printStackTrace();
        }
    }
    
    /**
     * Lee el archivo de productos y los guarda en un mapa.
     *
     * @return mapa que relaciona cada ID de producto con su información.
     * @throws IOException si ocurre un error al leer el archivo.
     */
    public static Map<String, Product> readProductsFile() throws IOException {
        Path productsPath = Paths.get(DATA_FOLDER, PRODUCTS_FILE);
        List<String> lines = Files.readAllLines(productsPath);

        Map<String, Product> products = new HashMap<>();
        
        for (String line : lines) {
            String[] parts = line.split(";");

            if (parts.length != 3) {
                throw new IllegalArgumentException(
                        "El archivo de productos tiene una línea con formato inválido.");
            }

            String productId = parts[0].trim();
            String productName = parts[1].trim();
            long productPrice = Long.parseLong(parts[2].trim());

            Product product = new Product(productId, productName, productPrice);
            products.put(productId, product);
        }

        return products;
    }
    /**
     * Lee el archivo de vendedores y los guarda en un mapa.
     *
     * @return mapa que relaciona cada documento con su vendedor.
     * @throws IOException si ocurre un error al leer el archivo.
     */
    public static Map<Long, Salesman> readSalesmenFile() throws IOException {
        Path salesmenPath = Paths.get(DATA_FOLDER, SALESMEN_FILE);
        List<String> lines = Files.readAllLines(salesmenPath);

        Map<Long, Salesman> salesmen = new HashMap<>();

        for (String line : lines) {
            String[] parts = line.split(";");

            if (parts.length != 4) {
                throw new IllegalArgumentException(
                        "El archivo de vendedores tiene una línea con formato inválido.");
            }

            String documentType = parts[0].trim();
            long documentNumber = Long.parseLong(parts[1].trim());
            String firstName = parts[2].trim();
            String lastName = parts[3].trim();

            Salesman salesman = new Salesman(documentType, documentNumber,
                    firstName, lastName);

            salesmen.put(documentNumber, salesman);
        }

        return salesmen;
    }
    /**
     * Lee los archivos de ventas y calcula el dinero recaudado por vendedor.
     *
     * @param products productos disponibles, organizados por ID.
     * @param salesmen vendedores disponibles, organizados por documento.
     * @return mapa que relaciona cada vendedor con su dinero recaudado.
     * @throws IOException si ocurre un error al leer los archivos de ventas.
     */
    public static Map<Long, Long> calculateSalesmenRevenue(
            Map<String, Product> products, Map<Long, Salesman> salesmen)
            throws IOException {

        Map<Long, Long> salesmenRevenue = new HashMap<>();

        for (Long documentNumber : salesmen.keySet()) {
            salesmenRevenue.put(documentNumber, 0L);
        }

        Path dataPath = Paths.get(DATA_FOLDER);

        try (DirectoryStream<Path> salesFiles = Files.newDirectoryStream(
                dataPath, "sales_*.txt")) {

            for (Path salesFile : salesFiles) {
                List<String> lines = Files.readAllLines(salesFile);

                if (lines.isEmpty()) {
                    throw new IllegalArgumentException(
                            "Uno de los archivos de ventas está vacío.");
                }

                String[] headerParts = lines.get(0).split(";");

                if (headerParts.length != 2) {
                    throw new IllegalArgumentException(
                            "El encabezado de un archivo de ventas es inválido.");
                }

                long salesmanId = Long.parseLong(headerParts[1].trim());

                if (!salesmen.containsKey(salesmanId)) {
                    throw new IllegalArgumentException(
                            "Existe una venta de un vendedor no registrado.");
                }

                for (int lineIndex = 1; lineIndex < lines.size(); lineIndex++) {
                    String[] saleParts = lines.get(lineIndex).split(";");

                    if (saleParts.length < 2) {
                        throw new IllegalArgumentException(
                                "Una venta tiene un formato inválido.");
                    }

                    String productId = saleParts[0].trim();
                    int quantity = Integer.parseInt(saleParts[1].trim());

                    Product product = products.get(productId);

                    if (product == null) {
                        throw new IllegalArgumentException(
                                "Existe una venta de un producto no registrado.");
                    }

                    long currentRevenue = salesmenRevenue.get(salesmanId);
                    long saleValue = product.getUnitPrice() * quantity;

                    salesmenRevenue.put(salesmanId,
                            currentRevenue + saleValue);
                }
            }
        }

        return salesmenRevenue;
    }
    /**
     * Crea un reporte de vendedores ordenado por dinero recaudado.
     *
     * @param salesmen vendedores organizados por documento.
     * @param salesmenRevenue dinero recaudado por cada vendedor.
     * @throws IOException si ocurre un error al crear el reporte.
     */
    public static void createSalesmenReport(Map<Long, Salesman> salesmen,
            Map<Long, Long> salesmenRevenue) throws IOException {

        List<Map.Entry<Long, Long>> orderedSalesmen =
                new ArrayList<>(salesmenRevenue.entrySet());

        orderedSalesmen.sort((first, second) ->
                Long.compare(second.getValue(), first.getValue()));

        Path reportPath = Paths.get(DATA_FOLDER, SALESMEN_REPORT_FILE);

        try (BufferedWriter writer = Files.newBufferedWriter(reportPath)) {
            for (Map.Entry<Long, Long> entry : orderedSalesmen) {
                Salesman salesman = salesmen.get(entry.getKey());

                writer.write(salesman.getFullName() + ";"
                        + entry.getValue());
                writer.newLine();
            }
        }
    }
    /**
     * Calcula la cantidad total vendida de cada producto.
     *
     * @param products productos disponibles, organizados por ID.
     * @return mapa que relaciona cada producto con su cantidad vendida.
     * @throws IOException si ocurre un error al leer los archivos de ventas.
     */
    public static Map<String, Integer> calculateProductsQuantity(
            Map<String, Product> products) throws IOException {

        Map<String, Integer> productQuantities = new HashMap<>();

        for (String productId : products.keySet()) {
            productQuantities.put(productId, 0);
        }

        Path dataPath = Paths.get(DATA_FOLDER);

        try (DirectoryStream<Path> salesFiles = Files.newDirectoryStream(
                dataPath, "sales_*.txt")) {

            for (Path salesFile : salesFiles) {
                List<String> lines = Files.readAllLines(salesFile);

                for (int lineIndex = 1; lineIndex < lines.size(); lineIndex++) {
                    String[] saleParts = lines.get(lineIndex).split(";");

                    if (saleParts.length < 2) {
                        throw new IllegalArgumentException(
                                "Una venta tiene un formato inválido.");
                    }

                    String productId = saleParts[0].trim();
                    int quantity = Integer.parseInt(saleParts[1].trim());

                    if (!products.containsKey(productId)) {
                        throw new IllegalArgumentException(
                                "Existe una venta de un producto no registrado.");
                    }

                    int currentQuantity = productQuantities.get(productId);

                    productQuantities.put(productId,
                            currentQuantity + quantity);
                }
            }
        }

        return productQuantities;
    }
    /**
     * Crea un reporte de productos ordenado por cantidad vendida.
     *
     * @param products productos disponibles, organizados por ID.
     * @param productQuantities cantidad total vendida por producto.
     * @throws IOException si ocurre un error al crear el reporte.
     */
    public static void createProductsReport(Map<String, Product> products,
            Map<String, Integer> productQuantities) throws IOException {

        List<Map.Entry<String, Integer>> orderedProducts =
                new ArrayList<>(productQuantities.entrySet());

        orderedProducts.sort((first, second) ->
                Integer.compare(second.getValue(), first.getValue()));

        Path reportPath = Paths.get(DATA_FOLDER, PRODUCTS_REPORT_FILE);

        try (BufferedWriter writer = Files.newBufferedWriter(reportPath)) {
            for (Map.Entry<String, Integer> entry : orderedProducts) {
                Product product = products.get(entry.getKey());

                writer.write(product.getName() + ";"
                        + product.getUnitPrice());
                writer.newLine();
            }
        }
    }
}
