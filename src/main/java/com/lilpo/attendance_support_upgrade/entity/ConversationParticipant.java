package com.lilpo.attendance_support_upgrade.entity;

import com.lilpo.attendance_support_upgrade.enums.ConversationRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)        //MEMBER or ADMIn
    ConversationRole role;

    @Column(nullable = false)
    LocalDateTime joinedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
