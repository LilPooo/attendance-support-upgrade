package com.lilpo.attendance_support_upgrade.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final JwtDecoder jwtDecoder; // Inject JwtDecoder
    private final JwtAuthenticationConverter jwtAuthenticationConverter; // Inject JwtAuthenticationConverter

    @Bean("sessions")
    public Map<String, WebSocketSession> getSessions() {
        return sessions;
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new CustomWebSocketHandler(jwtDecoder, jwtAuthenticationConverter, sessions), "/ws/notifications")
                .setAllowedOrigins("*");
    }
}
