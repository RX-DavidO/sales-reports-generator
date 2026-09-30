package co.edu.poli.sales;

/**
 * Representa un producto disponible para la venta.
 *
 * @author David Felipe Olarte Carmona
 * @author Maria Alejandra Cardenas Guzman
 * @author Laura Vanessa Romero Jimenez
 * @author Martha Liliana Salazar Betancur
 * @version 1.0
 */
public class Product {

    private final String id;
    private final String name;
    private final long unitPrice;

    /**
     * Crea un producto con su información básica.
     *
     * @param id identificador único del producto.
     * @param name nombre del producto.
     * @param unitPrice precio de una unidad del producto.
     */
    public Product(String id, String name, long unitPrice) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    /**
     * Obtiene el identificador del producto.
     *
     * @return ID del producto.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return nombre del producto.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el precio por unidad del producto.
     *
     * @return precio unitario del producto.
     */
    public long getUnitPrice() {
        return unitPrice;
    }
}