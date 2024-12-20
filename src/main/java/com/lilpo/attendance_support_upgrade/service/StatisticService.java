package com.lilpo.attendance_support_upgrade.service;

import com.lilpo.attendance_support_upgrade.dto.request.StudentAbsenceSummaryResponse;
import com.lilpo.attendance_support_upgrade.dto.request.StudentRollCallSummaryResponse;
import com.lilpo.attendance_support_upgrade.repository.CheckInRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StatisticService {
    CheckInRepository checkInRepository;

    public List<StudentRollCallSummaryResponse> getStudentRollCallSummary(String userId) {
        List<Object[]> results = checkInRepository.findClassroomAbsenceSummaryByUserId(userId);
        return results.stream()
                .map(result -> new StudentRollCallSummaryResponse(
                        ((Number) result[0]).longValue(), // classroomId
                        (String) result[1],              // classroomName
                        ((Number) result[2]).longValue() // absenceCount
                ))
                .collect(Collectors.toList());
    }

    public List<LocalDateTime> getAbsenceDateTimeByClassroomAndUserId(Long classroomId, String userId) {
        return checkInRepository.findAbsenceDateTimeByClassroomAndUserId(classroomId, userId);
    }

    public List<StudentAbsenceSummaryResponse> getStudentAbsenceSummary(Long classroomId) {
        List<Object[]> results = checkInRepository.findStudentAbsenceSummaryByClassroomId(classroomId);
        return results.stream()
                .map(result -> new StudentAbsenceSummaryResponse(
                        (String) result[0],  // userId
                        (String) result[1],  // username
                        ((Number) result[2]).longValue() // absenceCount
                ))
                .collect(Collectors.toList());
    }
}
