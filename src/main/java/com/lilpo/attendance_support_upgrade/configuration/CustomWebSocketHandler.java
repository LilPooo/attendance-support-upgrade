package com.lilpo.attendance_support_upgrade.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Map;


@Slf4j
@RequiredArgsConstructor
public class CustomWebSocketHandler extends AbstractWebSocketHandler {
    private final JwtDecoder jwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;
    private final Map<String, WebSocketSession> sessions;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = session.getHandshakeHeaders().getFirst("Authorization");
        log.info("Authorization token received: {}", token);  // Log token để kiểm tra

        if (token == null || !token.startsWith("Bearer ")) {
            log.error("Invalid token format: {}", token);
            session.close(CloseStatus.BAD_DATA.withReason("Missing or Invalid Token"));
            return;
        }

        try {
            token = token.substring(7);  // Remove "Bearer " prefix
            log.info("Extracted token: {}", token);  // Log token after extraction

            var jwt = jwtDecoder.decode(token);
            log.info("Decoded JWT: {}", jwt);  // Log the decoded JWT for debugging

            Authentication authentication = jwtAuthenticationConverter.convert(jwt);
            log.info("Authentication details: {}", authentication);  // Log the authentication details

            session.getAttributes().put("user", authentication); // Store the authentication info in the session
            sessions.put(authentication.getName(), session); // Save the session
            log.info("Session stored for user: {}", authentication.getName()); // Log session storage
        } catch (Exception e) {
            log.error("Token validation failed", e);
            session.close(CloseStatus.BAD_DATA.withReason("Invalid Token"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = (String) session.getAttributes().get("user");
        if (username != null) {
            sessions.remove(username); // Remove the session when the connection is closed
            log.info("Session removed for user: {}", username);  // Log session removal
        }
    }
}
