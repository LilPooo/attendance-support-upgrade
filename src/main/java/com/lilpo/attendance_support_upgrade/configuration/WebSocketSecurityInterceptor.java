package com.lilpo.attendance_support_upgrade.configuration;

import com.lilpo.attendance_support_upgrade.exception.AppException;
import com.lilpo.attendance_support_upgrade.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebSocketSecurityInterceptor implements ChannelInterceptor {
    JwtDecoder jwtDecoder;
    JwtAuthenticationConverter jwtAuthenticationConverter;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        log.info("Headers: {}", accessor);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");
//            if (token == null || !token.startsWith("Bearer ")) {
//                throw new AppException(ErrorCode.UNAUTHORIZED);
//            }
            try {
                String jwtToken = token.substring(7);
                var jwt = jwtDecoder.decode(jwtToken);

                Authentication authentication = jwtAuthenticationConverter.convert(jwt);
                accessor.setUser(authentication);
            } catch (Exception e) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
        }
        return message;
    }
}