package org.example.funko2.exceptions.categoria;
/**
 * Clase abstracta que representa una excepción de categoría.
 * @author Aragorn7372
 */
public abstract class  CategoriaException extends RuntimeException{
    /**
     * Constructor que recibe un mensaje de error.
     * @param message Mensaje de error.
     */
    protected CategoriaException(String message)
    {
        super(message);
    }
}
