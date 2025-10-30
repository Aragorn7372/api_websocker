package org.example.funko2.webSocket.dto;

import java.util.UUID;

public record FunkoDtoWebSocketResponse(
        Long id,
        UUID uuid,
        String name,
        Double price,
        Integer cantidad,
        String imagen,
        String categoria
){}
