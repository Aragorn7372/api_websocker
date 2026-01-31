package org.example.funko2.exceptions.funko;

/**
 * Excepción lanzada cuando no se encuentra un Funko.
 * @author Aragorn7372
 */
public final  class FunkoNotFoundException extends FunkoException {
    /**
     * Constructor que recibe un mensaje de error.
     * @param message Mensaje de error.
     */
    public FunkoNotFoundException(String message)
    {
        super(message);
    }
}
