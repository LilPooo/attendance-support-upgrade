package com.lilpo.attendance_support_upgrade.controller;

import com.lilpo.attendance_support_upgrade.dto.ApiResponse;
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

    @PatchMapping("/update-roll-call-time/{classroomId}/")
    public ApiResponse<String> updateRollCallTime(@PathVariable("classroomId") Long classroomId,
                                                  @RequestBody @Valid ClassroomPatchRequest request) {
        checkInService.updateRollCallTime(classroomId, request);
        return ApiResponse.<String>builder()
                .result("Roll call time updated successfully")
                .build();
    }

}
