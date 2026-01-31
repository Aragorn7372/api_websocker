package org.example.funko2.exceptions.storage;

import java.io.Serial;

/**
 * Excepción base para errores en el servicio de almacenamiento.
 * @author Aragorn7372
 */
public abstract class StorageException extends RuntimeException {
    // Por si debemos serializar
    @Serial
    private static final long serialVersionUID = 43876691117560211L;

    public StorageException(String mensaje) {
        super(mensaje);
    }
}
