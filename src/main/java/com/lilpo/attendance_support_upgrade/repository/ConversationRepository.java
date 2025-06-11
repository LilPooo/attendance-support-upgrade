package com.lilpo.attendance_support_upgrade.repository;

import com.lilpo.attendance_support_upgrade.dto.response.ConversationDetailResponse;
import com.lilpo.attendance_support_upgrade.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    @Query("SELECT c FROM Conversation c " +
            "LEFT JOIN FETCH c.participants cp " +
            "LEFT JOIN FETCH cp.user " +
            "WHERE c.isGroup = FALSE " +
            "AND EXISTS (" +
            "   SELECT cp1 FROM ConversationParticipant cp1 " +
            "   WHERE cp1.conversation.id = c.id " +
            "   AND cp1.user.id = :userAId" +
            ") " +
            "AND EXISTS (" +
            "   SELECT cp2 FROM ConversationParticipant cp2 " +
            "   WHERE cp2.conversation.id = c.id " +
            "   AND cp2.user.id = :userBId" +
            ")")
    Optional<Conversation> findOneToOneConversation(@Param("userAId") String userAId, @Param("userBId") String userBId);

    //Return list conversation + last message.
    @Query("SELECT new com.lilpo.attendance_support_upgrade.dto.response.ConversationDetailResponse(" +
            "c.id, c.isGroup, c.createdAt, " +
            "(SELECT m.content FROM Message m WHERE m.conversation.id = c.id ORDER BY m.timestamp DESC LIMIT 1), " +
            "(SELECT m.timestamp FROM Message m WHERE m.conversation.id = c.id ORDER BY m.timestamp DESC LIMIT 1), " +
            "(SELECT m.sender.id FROM Message m WHERE m.conversation.id = c.id ORDER BY m.timestamp DESC LIMIT 1)" +
            ") " +
            "FROM Conversation c " +
            "JOIN c.participants cp " +
            "WHERE cp.user.id = :userId")
    Page<ConversationDetailResponse> findConversationsByUserId(@Param("userId") String userId, Pageable pageable);


}