package com.lilpo.attendance_support_upgrade.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //Optional, if 1-1--> null
    String name;

    LocalDateTime createdAt;

    boolean isGroup;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Message> messages;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<ConversationParticipant> participants;
}
