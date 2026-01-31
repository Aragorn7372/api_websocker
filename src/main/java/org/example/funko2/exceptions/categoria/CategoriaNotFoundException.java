package org.example.funko2.exceptions.categoria;

/**
 * Excepción lanzada cuando no se encuentra una categoría.
 * @author Aragorn7372
 */
public class CategoriaNotFoundException extends CategoriaException {
    /**
     * Constructor que recibe un mensaje de error.
     * @param message Mensaje de error.
     */
    public  CategoriaNotFoundException(String message) { super(message); }
}
