package com.lilpo.attendance_support_upgrade.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final Map<String, WebSocketSession> sessions;

    @PostMapping("/notifications/{user}")
    public void createNotification(@PathVariable String user,
                                   @RequestBody String notification) throws IOException {
        if (notification == null) {
            throw new IllegalArgumentException("Notification should not be null");
        }

        var session = sessions.get(user);
        if (session == null) {
            throw new IllegalStateException(user + " is not connected");
        }

        session.sendMessage(new TextMessage(notification));
    }
}
