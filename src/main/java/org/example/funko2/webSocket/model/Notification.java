package org.example.funko2.webSocket.model;

public record Notification<T>(
        String entity,
        Tipo type,
        T data,
        String createdAt
) { public enum Tipo {CREATE, UPDATE, DELETE}

}
