package com.lilpo.attendance_support_upgrade.repository;

import com.lilpo.attendance_support_upgrade.entity.ConversationParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationParticipantRepository extends JpaRepository<ConversationParticipant, Long> {
}
