package com.lilpo.attendance_support_upgrade.controller;

import com.lilpo.attendance_support_upgrade.dto.ApiResponse;
import com.lilpo.attendance_support_upgrade.dto.request.CheckInStudentRequest;
import com.lilpo.attendance_support_upgrade.dto.request.ClassroomPatchRequest;
import com.lilpo.attendance_support_upgrade.service.CheckInService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/check-in")
public class CheckInController {
    CheckInService checkInService;

    @PatchMapping("/update-roll-call-time/{classroomId}")
    public ApiResponse<String> updateRollCallTime(
            @PathVariable("classroomId") Long classroomId, @RequestBody @Valid ClassroomPatchRequest request) {
        checkInService.updateRollCallTime(classroomId, request);
        return ApiResponse.<String>builder()
                .result("Roll call time updated successfully")
                .build();
    }

    @PostMapping("/create-roll-call-session/{classroomId}")
    public ApiResponse<String> createRollCallSession(
            @PathVariable("classroomId") Long classroomId, @RequestBody @Valid ClassroomPatchRequest request) {
        checkInService.createRollCallSession(classroomId, request.getStartRollCallTime(), request.getEndRollCallTime());
        return ApiResponse.<String>builder()
                .result("Roll call session created successfully")
                .build();
    }

    @PostMapping("/student/{username}/{classroomId}")
    public ApiResponse<String> studentCheckIn(
            @PathVariable("username") String username,
            @PathVariable("classroomId") Long classroomId,
            @RequestBody CheckInStudentRequest request
    ) {
        checkInService.studentCheckIn(
                username,
                classroomId,
                request.getDeviceId(),
                request.getLongitude(),
                request.getLatitude());

        return ApiResponse.<String>builder()
                .result("Student check in successfully")
                .build();
    }

}
