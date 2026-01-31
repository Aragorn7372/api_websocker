package org.example.funko2.webSocket.dto;

import java.util.UUID;

/**
 * Clase que representa un CategoriaDtoWebSocketResponse.
 * @author Aragorn7372
 */
public record CategoriaDtoWebSocketResponse(
        /**
         * Identificador único de la categoría.
         */
        UUID id,
        /**
         * Nombre de la categoría.
         */
        String name
) {
}
