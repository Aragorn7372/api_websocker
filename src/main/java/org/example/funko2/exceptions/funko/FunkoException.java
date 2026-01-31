package org.example.funko2.exceptions.funko;
/**
 * Clase abstracta que representa una excepción de Funko.
 * @author Aragorn7372
 */
public abstract class FunkoException extends RuntimeException {
    /**
     * Constructor que recibe un mensaje de error.
     * @param message Mensaje de error.
     */
    protected FunkoException(String message)
    {
        super(message);
    }

}

