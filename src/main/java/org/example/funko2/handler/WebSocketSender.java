package org.example.funko2.handler;



import java.io.IOException;

/**
 * Interfaz que define los métodos para enviar mensajes a través de WebSocket.
 * @author Aragorn7372
 */
public interface WebSocketSender {
    /**
     * Envía un mensaje a todos los clientes conectados.
     * @param message Mensaje a enviar.
     * @throws IOException Error al enviar el mensaje.
     */
    void sendMessage(String message) throws IOException;
    /**
     * Envía mensajes periódicos a los clientes conectados.
     * @throws IOException Error al enviar el mensaje.
     */
    void sendPeriodicMessages() throws IOException;
}

