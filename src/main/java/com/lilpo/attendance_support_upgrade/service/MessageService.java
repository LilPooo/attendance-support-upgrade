package com.lilpo.attendance_support_upgrade.service;

import com.lilpo.attendance_support_upgrade.dto.PageResponse;
import com.lilpo.attendance_support_upgrade.dto.request.MessageRequest;
import com.lilpo.attendance_support_upgrade.dto.response.MessageResponse;
import com.lilpo.attendance_support_upgrade.entity.Conversation;
import com.lilpo.attendance_support_upgrade.entity.Message;
import com.lilpo.attendance_support_upgrade.enums.MessageStatus;
import com.lilpo.attendance_support_upgrade.enums.MessageType;
import com.lilpo.attendance_support_upgrade.exception.AppException;
import com.lilpo.attendance_support_upgrade.exception.ErrorCode;
import com.lilpo.attendance_support_upgrade.mapper.MessageMapper;
import com.lilpo.attendance_support_upgrade.repository.ConversationRepository;
import com.lilpo.attendance_support_upgrade.repository.MessageRepository;
import com.lilpo.attendance_support_upgrade.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MessageService {
    ConversationRepository conversationRepository;
    MessageRepository messageRepository;
    UserRepository userRepository;
    MessageMapper messageMapper;

    public Message saveMessage(MessageRequest request) {
        Conversation conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        Message message = Message.builder()
                .content(request.getContent())
                .conversation(conversation)
                .sender(userRepository.findById(request.getSenderId())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)))
                .timestamp(LocalDateTime.now())
                .status(MessageStatus.SENT)
                .type(MessageType.TEXT)
                .build();
        return messageRepository.save(message);
    }

    public PageResponse<MessageResponse> getChatHistory(String senderId, String receiverId, int page, int size) {
        Conversation conversation = conversationRepository.findOneToOneConversation(senderId, receiverId)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        Sort sort = Sort.by("timestamp").ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<Message> pageData = messageRepository.findByConversationIdOrderByTimestampAsc(conversation.getId(), pageable);

        List<MessageResponse> messageResponses = pageData.getContent().stream()
                .map(messageMapper::toMessageResponse).toList();

        return PageResponse.<MessageResponse>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(messageResponses)
                .build();
    }


}
