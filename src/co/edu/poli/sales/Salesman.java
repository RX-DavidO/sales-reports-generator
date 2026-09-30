package co.edu.poli.sales;

/**
 * Representa un vendedor y su información personal.
 *
 * @author David Felipe Olarte Carmona
 * @author Maria Alejandra Cardenas Guzman
 * @author Laura Vanessa Romero Jimenez
 * @author Martha Liliana Salazar Betancur
 * @version 1.0
 */
public class Salesman {

    private final String documentType;
    private final long documentNumber;
    private final String firstName;
    private final String lastName;

    /**
     * Crea un vendedor con su información básica.
     *
     * @param documentType tipo de documento del vendedor.
     * @param documentNumber número de documento del vendedor.
     * @param firstName nombres del vendedor.
     * @param lastName apellidos del vendedor.
     */
    public Salesman(String documentType, long documentNumber,
            String firstName, String lastName) {

        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Obtiene el tipo de documento.
     *
     * @return tipo de documento.
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * Obtiene el número de documento.
     *
     * @return número de documento.
     */
    public long getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Obtiene los nombres del vendedor.
     *
     * @return nombres del vendedor.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Obtiene los apellidos del vendedor.
     *
     * @return apellidos del vendedor.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Obtiene el nombre completo del vendedor.
     *
     * @return nombres y apellidos unidos.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}