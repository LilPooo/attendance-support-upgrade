package com.lilpo.attendance_support_upgrade.mapper;

import com.lilpo.attendance_support_upgrade.dto.response.MessageResponse;
import com.lilpo.attendance_support_upgrade.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface MessageMapper {
    @Mapping(source = "sender.id", target = "senderId")
    MessageResponse toMessageResponse(Message message);
}
