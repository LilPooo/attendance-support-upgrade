package com.lilpo.attendance_support_upgrade.service;

import com.lilpo.attendance_support_upgrade.dto.response.ConversationResponse;
import com.lilpo.attendance_support_upgrade.entity.Conversation;
import com.lilpo.attendance_support_upgrade.entity.ConversationParticipant;
import com.lilpo.attendance_support_upgrade.entity.User;
import com.lilpo.attendance_support_upgrade.enums.ConversationRole;
import com.lilpo.attendance_support_upgrade.exception.AppException;
import com.lilpo.attendance_support_upgrade.exception.ErrorCode;
import com.lilpo.attendance_support_upgrade.mapper.ConversationMapper;
import com.lilpo.attendance_support_upgrade.repository.ConversationParticipantRepository;
import com.lilpo.attendance_support_upgrade.repository.ConversationRepository;
import com.lilpo.attendance_support_upgrade.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ConversationService {
    ConversationRepository conversationRepository;
    ConversationParticipantRepository conversationParticipantRepository;
    UserRepository userRepository;
    ConversationMapper conversationMapper;

    public ConversationResponse findOneToOneConversation(String userAId, String userBId) {
        Optional<Conversation> existingConversation = conversationRepository.findOneToOneConversation(userAId, userBId);
        Conversation conversation;
        if (existingConversation.isPresent()) {
            conversation = existingConversation.get();
//            throw new AppException(ErrorCode.USER_ALREADY_HAVE_CONVERSATION);
        } else {
            User userA = userRepository.findById(userAId)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            User userB = userRepository.findById(userBId)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            conversation = Conversation.builder()
                    .name(null)
                    .isGroup(false)
                    .createdAt(LocalDateTime.now())
                    .build();
            conversation = conversationRepository.save(conversation);

            ConversationParticipant participantA = ConversationParticipant.builder()
                    .user(userA)
                    .joinedAt(LocalDateTime.now())
                    .role(ConversationRole.MEMBER)
                    .conversation(conversation)
                    .build();

            ConversationParticipant participantB = ConversationParticipant.builder()
                    .user(userB)
                    .joinedAt(LocalDateTime.now())
                    .role(ConversationRole.MEMBER)
                    .conversation(conversation)
                    .build();

            conversationParticipantRepository.saveAll(List.of(participantA, participantB));
            log.info("Participant A: {}", participantA);
            log.info("Participant B: {}", participantB);
        }
        ConversationResponse response = conversationMapper.toConversationResponse(conversation);

        List<String> participantIds = conversation.getParticipants().stream()
                .map(participant -> participant.getUser().getId())
                .toList();
        response.setParticipantIds(participantIds);

        return response;
    }
}
