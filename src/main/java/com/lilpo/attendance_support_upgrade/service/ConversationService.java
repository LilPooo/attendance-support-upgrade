package com.lilpo.attendance_support_upgrade.service;

import com.lilpo.attendance_support_upgrade.dto.PageResponse;
import com.lilpo.attendance_support_upgrade.dto.response.ConversationDetailResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    public ConversationResponse findOneToOneConversationUsername(String userAId, String usernameB) {
        User userB = userRepository.findByUsername(usernameB)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return findOneToOneConversation(userAId, userB.getId());

    }

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
                    .participants(new HashSet<>())
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
            conversation.getParticipants().add(participantA);
            conversation.getParticipants().add(participantB);
            conversation = conversationRepository.save(conversation); // Save again to persist participants

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

    public PageResponse<ConversationDetailResponse> getUserConversations(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ConversationDetailResponse> pageData = conversationRepository.findConversationsByUserId(userId, pageable);
        List<ConversationDetailResponse> conversationDetails = pageData.getContent();
        return PageResponse.<ConversationDetailResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(conversationDetails)
                .build();
    }
}
