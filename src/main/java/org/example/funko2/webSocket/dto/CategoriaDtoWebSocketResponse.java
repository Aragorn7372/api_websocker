package org.example.funko2.webSocket.dto;

import java.util.UUID;

public record CategoriaDtoWebSocketResponse(
        UUID id,
        String name
) {
}
