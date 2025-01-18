package com.lilpo.attendance_support_upgrade.controller;

import com.lilpo.attendance_support_upgrade.dto.ApiResponse;
import com.lilpo.attendance_support_upgrade.dto.PageResponse;
import com.lilpo.attendance_support_upgrade.dto.request.MessageRequest;
import com.lilpo.attendance_support_upgrade.dto.response.MessageResponse;
import com.lilpo.attendance_support_upgrade.service.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

//@Configuration
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class MessageController {
    MessageService messageService;
    Map<String, WebSocketSession> sessions;

    @PostMapping("/send")
    public ApiResponse<String> sendMessage(@RequestBody MessageRequest request) throws IOException {

        //save message to db
        messageService.saveMessage(request);
        WebSocketSession receiverSession = sessions.get(request.getReceiverId());
        if (receiverSession != null) {
            try {
                receiverSession.sendMessage(new TextMessage(request.getContent()));
                log.info("Message sent to: {}", request.getReceiverId());
                return ApiResponse.<String>builder()
                        .result("Message sent successfully!")
                        .build();
            } catch (Exception e) {
                log.error("Error sending message: ", e);
                return ApiResponse.<String>builder()
                        .result("Error sending message: ")
                        .build();
            }
        } else {
            log.warn("User {} is not connected", request.getReceiverId());
            return ApiResponse.<String>builder().result("Receiver not connected").build();
        }
    }

    @GetMapping("/history")
    public ApiResponse<PageResponse<MessageResponse>> getChatHistory(
            @RequestParam("senderId") String senderId,
            @RequestParam("receiverId") String receiverId,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size
    ) {
        PageResponse<MessageResponse> messageHistory = messageService.getChatHistory(senderId, receiverId, page, size);
        return ApiResponse.<PageResponse<MessageResponse>>builder()
                .result(messageHistory)
                .build();
    }

}
