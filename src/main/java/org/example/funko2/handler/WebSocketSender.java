package org.example.funko2.handler;



import java.io.IOException;

public interface WebSocketSender {
    void sendMessage(String message) throws IOException;
    void sendPeriodicMessages() throws IOException;
}

