package org.example.funko2.webSocket.mapper;

import org.example.funko2.model.Categoria;
import org.example.funko2.model.Funko;
import org.example.funko2.webSocket.dto.CategoriaDtoWebSocketResponse;
import org.example.funko2.webSocket.dto.FunkoDtoWebSocketResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entidades a DTOs de respuesta WebSocket.
 * @author Aragorn7372
 */
@Component
public class NotificationMapper {
    /**
     * Convierte un Funko a FunkoDtoWebSocketResponse.
     * @param funko Entidad Funko.
     * @return FunkoDtoWebSocketResponse.
     */
    public static FunkoDtoWebSocketResponse toFunkoDtoWebsocketResponse(Funko funko) {
        return new FunkoDtoWebSocketResponse(
                funko.getId(),
                funko.getUuid(),
                funko.getName(),
                funko.getPrice(),
                funko.getCantidad(),
                funko.getImagen(),
                funko.getCategoria().getName()
        );
    }
    /**
     * Convierte una Categoria a CategoriaDtoWebSocketResponse.
     * @param categoria Entidad Categoria.
     * @return CategoriaDtoWebSocketResponse.
     */
    public static CategoriaDtoWebSocketResponse toCategoriaDtoWebSocketResponse(Categoria categoria) {
        return new CategoriaDtoWebSocketResponse(
                categoria.getId(),
                categoria.getName()
        );
    }
}
