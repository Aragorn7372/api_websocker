package org.example.funko2.exceptions.storage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
/**
 * Clase que representa una excepción de almacenamiento interna.
 * @author Aragorn7372
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class StorageInternal extends StorageException {
    // Por si debemos serializar
    @Serial
    private static final long serialVersionUID = 43876691117560211L;

    public StorageInternal(String mensaje) {
        super(mensaje);
    }
}
