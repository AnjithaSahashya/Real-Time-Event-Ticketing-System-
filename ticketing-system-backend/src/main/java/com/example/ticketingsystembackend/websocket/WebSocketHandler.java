package com.example.ticketingsystembackend.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New WebSocket connection: " + session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (message.isLast()) {
            session.sendMessage(new TextMessage("Server received: " + message.getPayload()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("WebSocket connection closed: " + session.getId());
    }


    public void broadcastMessage(String event, Object payload) {
        executorService.submit(() -> {
            for (WebSocketSession session : sessions) {
                try {
                    if (session.isOpen()) {
                        // Construct JSON object
                        ObjectMapper objectMapper = new ObjectMapper();
                        Map<String, Object> message = new HashMap<>();
                        message.put("event", event);
                        message.put("payload", payload);

                        // Convert the message to a JSON string
                        String jsonMessage = objectMapper.writeValueAsString(message);

                        // Send the JSON message
                        session.sendMessage(new TextMessage(jsonMessage));
                    }
                } catch (IOException e) {
                    System.err.println("Error sending message: " + e.getMessage());
                }
            }
        });
    }


}
