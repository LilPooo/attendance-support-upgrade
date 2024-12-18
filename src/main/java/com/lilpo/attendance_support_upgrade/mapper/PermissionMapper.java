package com.lilpo.attendance_support_upgrade.mapper;

import com.lilpo.attendance_support_upgrade.dto.request.PermissionRequest;
import com.lilpo.attendance_support_upgrade.dto.response.PermissionResponse;
import com.lilpo.attendance_support_upgrade.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

//    void updatePermission(@MappingTarget Permission permission);
}
