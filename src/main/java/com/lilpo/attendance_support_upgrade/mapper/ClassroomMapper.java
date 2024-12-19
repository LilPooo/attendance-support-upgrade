package com.lilpo.attendance_support_upgrade.mapper;

import com.lilpo.attendance_support_upgrade.dto.request.ClassroomCreationRequest;
import com.lilpo.attendance_support_upgrade.dto.request.ClassroomUpdateRequest;
import com.lilpo.attendance_support_upgrade.dto.response.ClassroomResponse;
import com.lilpo.attendance_support_upgrade.entity.Classroom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface ClassroomMapper {
    Classroom toClassroom(ClassroomCreationRequest request);

    @Mapping(target = "daysOfWeek", source = "daysOfWeek")
    ClassroomResponse toClassroomResponse(Classroom classroom);

    void updateClassroom(@MappingTarget Classroom classroom, ClassroomUpdateRequest request);
}
