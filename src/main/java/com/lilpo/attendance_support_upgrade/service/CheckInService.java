package com.lilpo.attendance_support_upgrade.service;

import com.lilpo.attendance_support_upgrade.dto.request.ClassroomPatchRequest;
import com.lilpo.attendance_support_upgrade.entity.Classroom;
import com.lilpo.attendance_support_upgrade.exception.AppException;
import com.lilpo.attendance_support_upgrade.exception.ErrorCode;
import com.lilpo.attendance_support_upgrade.repository.ClassroomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CheckInService {
    ClassroomRepository classroomRepository;

    public void updateRollCallTime(Long classroomId, ClassroomPatchRequest request) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSROOM_NOT_EXISTED));

        classroom.setStartRollCallTime(request.getStartRollCallTime());
        classroom.setEndRollCallTime(request.getEndRollCallTime());
        classroomRepository.save(classroom);
    }


}
