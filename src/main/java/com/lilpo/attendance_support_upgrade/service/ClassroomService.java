package com.lilpo.attendance_support_upgrade.service;

import com.lilpo.attendance_support_upgrade.dto.request.ClassroomCreationRequest;
import com.lilpo.attendance_support_upgrade.dto.request.ClassroomUpdateRequest;
import com.lilpo.attendance_support_upgrade.dto.response.ClassroomResponse;
import com.lilpo.attendance_support_upgrade.entity.Classroom;
import com.lilpo.attendance_support_upgrade.exception.AppException;
import com.lilpo.attendance_support_upgrade.exception.ErrorCode;
import com.lilpo.attendance_support_upgrade.mapper.ClassroomMapper;
import com.lilpo.attendance_support_upgrade.repository.ClassroomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ClassroomService {
    ClassroomRepository classroomRepository;
    ClassroomMapper classroomMapper;

    public ClassroomResponse createClassroom(ClassroomCreationRequest request) {
        Classroom classroom = classroomMapper.toClassroom(request);
        classroomRepository.save(classroom);
        return classroomMapper.toClassroomResponse(classroom);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ClassroomResponse> getClassrooms() {
        log.info("In method get all classrooms");
        return classroomRepository.findAll().stream().map(classroomMapper::toClassroomResponse).toList();
    }

    public ClassroomResponse getClassroomById(Long id) {
        return classroomMapper.toClassroomResponse(classroomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSROOM_NOT_EXISTED)));
    }

    public ClassroomResponse updateClassroom(Long id, ClassroomUpdateRequest request) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSROOM_NOT_EXISTED));

        classroomMapper.updateClassroom(classroom, request);

        return classroomMapper.toClassroomResponse(classroomRepository.save(classroom));
    }

    public void deleteClassroom(Long id) {
        classroomRepository.deleteById(id);
    }
}
