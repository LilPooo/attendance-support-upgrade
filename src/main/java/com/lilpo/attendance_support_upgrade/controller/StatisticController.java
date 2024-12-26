package com.lilpo.attendance_support_upgrade.controller;

import com.lilpo.attendance_support_upgrade.dto.ApiResponse;
import com.lilpo.attendance_support_upgrade.dto.request.StudentAbsenceSummaryResponse;
import com.lilpo.attendance_support_upgrade.dto.request.StudentRollCallSummaryResponse;
import com.lilpo.attendance_support_upgrade.service.StatisticService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StatisticController {
    StatisticService statisticService;

    @GetMapping("/student/absences/{userId}")
    public ApiResponse<List<StudentRollCallSummaryResponse>> getStudentAbsences(@PathVariable("userId") String userId) {
        return ApiResponse.<List<StudentRollCallSummaryResponse>>builder()
                .result(statisticService.getStudentRollCallSummary(userId))
                .build();
    }

    @GetMapping("/student/absences/dates/{classroomId}/{userId}")
    public ApiResponse<List<LocalDateTime>> getStudentAbsencesDates(
            @PathVariable("classroomId") Long classroomId,
            @PathVariable("userId") String userId) {
        return ApiResponse.<List<LocalDateTime>>builder()
                .result(statisticService.getAbsenceDateTimeByClassroomAndUserId(classroomId, userId))
                .build();
    }

    //Count for a class
    @GetMapping("/teacher/absences/classroom/{classroomId}")
    public ApiResponse<List<StudentAbsenceSummaryResponse>> getStudentAbsencesClassroom(@PathVariable("classroomId") Long classroomId) {
        return ApiResponse.<List<StudentAbsenceSummaryResponse>>builder()
                .result(statisticService.getStudentAbsenceSummary(classroomId))
                .build();
    }
}
