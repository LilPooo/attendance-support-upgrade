package com.lilpo.attendance_support_upgrade.mapper;

import com.lilpo.attendance_support_upgrade.dto.request.UserCreationRequest;
import com.lilpo.attendance_support_upgrade.dto.request.UserUpdateRequest;
import com.lilpo.attendance_support_upgrade.dto.response.UserResponse;
import com.lilpo.attendance_support_upgrade.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
