package com.lilpo.attendance_support_upgrade.controller;

import com.lilpo.attendance_support_upgrade.dto.ApiResponse;
import com.lilpo.attendance_support_upgrade.dto.response.ConversationResponse;
import com.lilpo.attendance_support_upgrade.service.ConversationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/conversation")
@RequiredArgsConstructor
public class ConversationController {
    ConversationService conversationService;

    @PostMapping("/create-one-to-one")
    public ApiResponse<ConversationResponse> createOneToOneConversation(
            @RequestParam String userAId,
            @RequestParam String userBId
    ) {
        return ApiResponse.<ConversationResponse>builder()
                .result(conversationService.findOneToOneConversation(userAId, userBId))
                .build();
    }

    @PostMapping("/create-one-to-one-username")
    public ApiResponse<ConversationResponse> createOneToOneConversationUsername(
            @RequestParam String userAId,
            @RequestParam String usernameB
    ) {
        return ApiResponse.<ConversationResponse>builder()
                .result(conversationService.findOneToOneConversationUsername(userAId, usernameB))
                .build();
    }
}
