package com.lilpo.attendance_support_upgrade.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationDetailResponse {
    Long id;
    boolean isGroup;
    LocalDateTime createdAt;
    String lastMessageContent;
    LocalDateTime lastMessageTimestamp;
    String lastMessageSenderId;
}
