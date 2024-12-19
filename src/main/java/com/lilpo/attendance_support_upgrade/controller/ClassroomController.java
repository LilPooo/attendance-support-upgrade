package com.lilpo.attendance_support_upgrade.controller;

import com.lilpo.attendance_support_upgrade.dto.ApiResponse;
import com.lilpo.attendance_support_upgrade.dto.request.ClassroomCreationRequest;
import com.lilpo.attendance_support_upgrade.dto.request.ClassroomUpdateRequest;
import com.lilpo.attendance_support_upgrade.dto.response.ClassroomResponse;
import com.lilpo.attendance_support_upgrade.service.ClassroomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/classrooms")
@RequiredArgsConstructor
public class ClassroomController {
    ClassroomService classroomService;

    @PostMapping
    ApiResponse<ClassroomResponse> createClassroom(@RequestBody ClassroomCreationRequest request) {
        return ApiResponse.<ClassroomResponse>builder()
                .result(classroomService.createClassroom(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<ClassroomResponse>> getClassrooms() {
        return ApiResponse.<List<ClassroomResponse>>builder()
                .result(classroomService.getClassrooms())
                .build();
    }

    @GetMapping("{classroomId}")
    ApiResponse<ClassroomResponse> getClassroom(@PathVariable("classroomId") Long classroomId) {
        return ApiResponse.<ClassroomResponse>builder()
                .result(classroomService.getClassroomById(classroomId))
                .build();
    }

    @PutMapping("{classroomId}")
    ApiResponse<ClassroomResponse> updateClassroom(@PathVariable("classroomId") Long classroomId, @RequestBody ClassroomUpdateRequest request) {
        return ApiResponse.<ClassroomResponse>builder()
                .result(classroomService.updateClassroom(classroomId, request))
                .build();
    }

    @DeleteMapping("{classroomId}")
    ApiResponse<String> deleteClassroom(@PathVariable("classroomId") Long classroomId) {
        classroomService.deleteClassroom(classroomId);
        return ApiResponse.<String>builder()
                .result("Classroom has been deleted")
                .build();
    }


}
