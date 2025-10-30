package org.example.funko2.exceptions.categoria;

public abstract class  CategoriaException extends RuntimeException{
    protected CategoriaException(String message)
    {
        super(message);
    }
}
