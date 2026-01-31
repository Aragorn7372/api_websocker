package org.example.funko2.webSocket.dto;

import java.util.UUID;
/**
 * Clase que representa un FunkoDtoWebSocketResponse.
 * @author Aragorn7372
 */
public record FunkoDtoWebSocketResponse(
        /**
         * Identificador único del Funko.
         */
        Long id,
        /**
         * Identificador único del Funko.
         */
        UUID uuid,
        /**
         * Nombre del Funko.
         */
        String name,
        /**
         * Precio del Funko.
         */
        Double price,
        /**
         * Cantidad del Funko.
         */
        Integer cantidad,
        /**
         * Imagen del Funko.
         */
        String imagen,
        /**
         * Categoria del Funko.
         */
        String categoria
){}
