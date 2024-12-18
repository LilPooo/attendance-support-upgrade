package com.lilpo.attendance_support_upgrade.mapper;

import com.lilpo.attendance_support_upgrade.dto.request.RoleRequest;
import com.lilpo.attendance_support_upgrade.dto.response.RoleResponse;
import com.lilpo.attendance_support_upgrade.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface RoleMapper {

    //cuz we map Set of String Permission to Set of Entity Permission to--> ignore, and build by ourselves.
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
