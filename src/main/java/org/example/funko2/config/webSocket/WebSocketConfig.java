package org.example.funko2.config.webSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.example.funko2.handler.WebSocketHandler;

/**
 * Clase de configuración del WebSocket.
 * @author Aragorn7372
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {



    // Registra uno por cada tipo de notificación que quieras con su handler y su ruta (endpoint)
    // Cuidado con la ruta que no se repita
    // Para coinectar con el cliente, el cliente debe hacer una petición de conexión
    // ws://localhost:3000/ws/v1/productos
    /**
     * Registra los handlers de WebSocket.
     * @param registry Registro de handlers de WebSocket.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketFunkosHandler(), "/ws/funkos");
        registry.addHandler(webSocketCategoriasHandler(), "/ws/categorias");
    }

    /**
     * Registra el handler de Funkos.
     * @return Handler de Funkos.
     */
    @Bean
    public WebSocketHandler webSocketFunkosHandler() {
        return new WebSocketHandler("Funko");
    }
    /**
     * Registra el handler de Categorias.
     * @return Handler de Categorias.
     */
    @Bean
    public WebSocketHandler webSocketCategoriasHandler() {
        return new WebSocketHandler("Categoria");
    }

}