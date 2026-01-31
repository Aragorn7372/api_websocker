package org.example.funko2.webSocket.model;

/**
 * Clase que representa una notificación de WebSocket.
 * @param <T> Tipo de datos en la notificación.
 * @author Aragorn7372
 */
public record Notification<T>(
        /**
         * Entidad a la que se refiere la notificación.
         */
        String entity,
        /**
         * Tipo de operación (CREATE, UPDATE, DELETE).
         */
        Tipo type,
        /**
         * Datos de la notificación.
         */
        T data,
        /**
         * Fecha de creación de la notificación.
         */
        String createdAt
) { public enum Tipo {CREATE, UPDATE, DELETE}

}
