package com.lilpo.attendance_support_upgrade.mapper;

import com.lilpo.attendance_support_upgrade.dto.response.ConversationResponse;
import com.lilpo.attendance_support_upgrade.entity.Conversation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConversationMapper {

    @Mapping(target = "participantIds", ignore = true)
    ConversationResponse toConversationResponse(Conversation conversation);


}
