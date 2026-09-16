package co.edu.poli.sales;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Genera archivos planos de prueba para el proyecto de reportes de ventas.
 * Crea un archivo de productos, un archivo de vendedores y archivos individuales de ventas para cada vendedor.
 *
 * @author David Felipe Olarte Carmona
 * @author Maria Alejandra Cardenas Guzman
 * @author Laura Vanessa Romero Jimenez
 * @author Martha Liliana Salazar Betancur
 * 
 * @version 1.0
 */

public class GenerateInfoFiles {

	private static final String DATA_FOLDER = "data";
	
	private static final int PRODUCTS_COUNT = 10;
	
	private static final int SALESMEN_COUNT = 5;
	
	private static final String PRODUCTS_FILE = "products.txt";
	
	private static final String SALESMEN_INFO_FILE = "salesmen.txt";

	private static final String[] FIRST_NAMES = {
	    "Ana", "Carlos", "Laura", "Miguel", "Sofia",
	    "Daniel", "Valentina", "Andres", "Camila", "Jorge"
	};

	private static final String[] LAST_NAMES = {
	    "Gomez", "Rodriguez", "Martinez", "Lopez", "Garcia",
	    "Perez", "Hernandez", "Torres", "Ramirez", "Diaz"
	};
	private static final String[] PRODUCT_NAMES = {
	    "Cuaderno", "Lapiz", "Borrador", "Regla", "Marcador",
	    "Carpeta", "Tijeras", "Pegante", "Calculadora", "Mochila"
	};
	
	/**
	 * Punto de inicio del programa. Genera los archivos de productos, vendedores y ventas necesarios para las pruebas del proyecto.
	 *
	 * @param args argumentos de línea de comandos. No se usan en este programa.
	 */	
	public static void main(String[] args) {
		try {
			createProductsFile(PRODUCTS_COUNT);
			createSalesManInfoFile(SALESMEN_COUNT);

			for (int salesmanNumber = 1;
			        salesmanNumber <= SALESMEN_COUNT;
			        salesmanNumber++) {

			    long salesmanId = 1000000000L + salesmanNumber;

			    createSalesMenFile(20, "Vendedor " + salesmanNumber, salesmanId);
			}
			
			System.out.println("Archivos de productos, vendedores y ventas generados correctamente.");
	    } catch (IOException e) {
	        System.out.println("Ocurrió un error al generar los archivos.");
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Crea un archivo con productos de prueba.
	 *
	 * @param productsCount cantidad de productos que se desea generar.
	 * @throws IOException si ocurre un error al crear o escribir el archivo.
	 */
	public static void createProductsFile(int productsCount) throws IOException {
	    if (productsCount <= 0) {
	        throw new IllegalArgumentException(
	                "La cantidad de productos debe ser mayor que cero.");
	    }

	    Path dataPath = Paths.get(DATA_FOLDER);
	    Files.createDirectories(dataPath);

	    Path productsPath = dataPath.resolve(PRODUCTS_FILE);
	    Random random = new Random();

	    try (BufferedWriter writer = Files.newBufferedWriter(productsPath)) {
	        for (int productNumber = 1; productNumber <= productsCount; productNumber++) {
	            String productId = String.format("P%03d", productNumber);
	            String productName = PRODUCT_NAMES[
	                    random.nextInt(PRODUCT_NAMES.length)];
	            long productPrice = (random.nextInt(100) + 1) * 1000L;

	            writer.write(productId + ";" + productName + ";" + productPrice);
	            writer.newLine();
	        }
	    }
	}
	
	/**
	 * Crea un archivo con información de vendedores de prueba.
	 *
	 * @param salesmanCount cantidad de vendedores que se desea generar.
	 * @throws IOException si ocurre un error al crear o escribir el archivo.
	 */
	public static void createSalesManInfoFile(int salesmanCount) throws IOException {
	    if (salesmanCount <= 0) {
	        throw new IllegalArgumentException(
	                "La cantidad de vendedores debe ser mayor que cero.");
	    }

	    Path dataPath = Paths.get(DATA_FOLDER);
	    Files.createDirectories(dataPath);

	    Path salesmenPath = dataPath.resolve(SALESMEN_INFO_FILE);
	    Random random = new Random();

	    try (BufferedWriter writer = Files.newBufferedWriter(salesmenPath)) {
	        for (int salesmanNumber = 1;
	                salesmanNumber <= salesmanCount;
	                salesmanNumber++) {

	            String documentType = "CC";
	            long documentNumber = 1000000000L + salesmanNumber;

	            String firstName = FIRST_NAMES[
	                    random.nextInt(FIRST_NAMES.length)];
	            String lastName = LAST_NAMES[
	                    random.nextInt(LAST_NAMES.length)];

	            writer.write(documentType + ";" + documentNumber + ";"
	                    + firstName + ";" + lastName);
	            writer.newLine();
	        }
	    }
	}
	
	/**
	 * Crea un archivo de ventas de prueba para un vendedor.
	 *
	 * @param randomSalesCount cantidad de ventas que se desea generar.
	 * @param name nombre del vendedor.
	 * @param id número de documento del vendedor.
	 * @throws IOException si ocurre un error al crear o escribir el archivo.
	 */
	public static void createSalesMenFile(int randomSalesCount,
	        String name, long id) throws IOException {

	    if (randomSalesCount <= 0) {
	        throw new IllegalArgumentException(
	                "La cantidad de ventas debe ser mayor que cero.");
	    }

	    if (name == null || name.trim().isEmpty()) {
	        throw new IllegalArgumentException(
	                "El nombre del vendedor no puede estar vacío.");
	    }

	    if (id <= 0) {
	        throw new IllegalArgumentException(
	                "El número de documento debe ser mayor que cero.");
	    }

	    Path dataPath = Paths.get(DATA_FOLDER);
	    Files.createDirectories(dataPath);

	    String safeName = name.trim().replace(" ", "_");
	    String salesFileName = "sales_" + safeName + "_" + id + ".txt";
	    Path salesPath = dataPath.resolve(salesFileName);

	    Random random = new Random();

	    try (BufferedWriter writer = Files.newBufferedWriter(salesPath)) {
	        writer.write("CC;" + id);
	        writer.newLine();

	        for (int saleNumber = 1;
	                saleNumber <= randomSalesCount;
	                saleNumber++) {

	            int productNumber = random.nextInt(PRODUCTS_COUNT) + 1;
	            String productId = String.format("P%03d", productNumber);
	            int quantity = random.nextInt(10) + 1;

	            writer.write(productId + ";" + quantity + ";");
	            writer.newLine();
	        }
	    }
	}
}

