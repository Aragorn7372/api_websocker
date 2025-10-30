package org.example.funko2.exceptions.funko;

public abstract class FunkoException extends RuntimeException {
    protected FunkoException(String message)
    {
        super(message);
    }

}

