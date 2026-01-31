package org.example.funko2.exceptions.storage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

/**
 * Clase que representa una excepción de almacenamiento no encontrado.
 * @author Aragorn7372
 */ 
@ResponseStatus(HttpStatus.NOT_FOUND)
public class StorageNotFound extends StorageException {
    // Por si debemos serializar
    @Serial
    private static final long serialVersionUID = 43876691117560211L;
    /**
     * Constructor que recibe un mensaje de error.
     * @param mensaje Mensaje de error.
     */
    public StorageNotFound(String mensaje) {
        super(mensaje);
    }
}
