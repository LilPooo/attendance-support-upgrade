package com.lilpo.attendance_support_upgrade.entity;

import com.lilpo.attendance_support_upgrade.enums.MessageStatus;
import com.lilpo.attendance_support_upgrade.enums.MessageType;
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
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    LocalDateTime timestamp = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    MessageStatus status; // ENUM: SENT, DELIVERED, SEEN, FAILED

    @Enumerated(EnumType.STRING)
    MessageType type;  //ENUM: TEXT, IMAGE, FILE

    String content;

    LocalDateTime readAt;

    //refactor later.
    @ManyToOne
    @JoinColumn(name = "sender_id")
    User sender;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    Conversation conversation;
}
