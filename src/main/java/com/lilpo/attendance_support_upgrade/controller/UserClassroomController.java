package com.lilpo.attendance_support_upgrade.controller;

import com.lilpo.attendance_support_upgrade.dto.ApiResponse;
import com.lilpo.attendance_support_upgrade.dto.response.ClassroomResponse;
import com.lilpo.attendance_support_upgrade.service.UserClassroomService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-classrooms")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserClassroomController {
    UserClassroomService userClassroomService;

    //Add user to a class via userId (UUID)
    @PostMapping("{userId}/{classroomId}")
    public ApiResponse<String> addUserToClassroomById(@PathVariable("userId") String userId, @PathVariable("classroomId") Long classroomId) {
        userClassroomService.addUserToClassroomById(userId, classroomId);
        return ApiResponse.<String>builder()
                .result("User has been added to the classroom successfully")
                .build();
    }

    //Add user to a class via username (String)
    @PostMapping("/add-by-username/{username}/{classroomId}")
    public ApiResponse<String> addUserToClassroomByUsername(
            @PathVariable("username") String username,
            @PathVariable("classroomId") Long classroomId) {
        userClassroomService.addUserToClassroomByUsername(username, classroomId);
        return ApiResponse.<String>builder()
                .result("User has been added to the classroom successfully.")
                .build();
    }


    //Remove user from class via userId (UUID)
    @DeleteMapping("/{userId}/{classroomId}")
    public ApiResponse<String> removeUserFromClassroomById(@PathVariable("userId") String userId, @PathVariable("classroomId") Long classroomId) {
        userClassroomService.removeUserFromClassroomById(userId, classroomId);
        return ApiResponse.<String>builder()
                .result("User has been removed from the classroom successfully")
                .build();
    }

    @DeleteMapping("/add-by-username/{username}/{classroomId}")
    public ApiResponse<String> removeUserFromClassroomByUsername(
            @PathVariable("username") String username,
            @PathVariable("classroomId") Long classroomId) {
        userClassroomService.removeUserFromClassroomByUsername(username, classroomId);
        return ApiResponse.<String>builder()
                .result("User has been removed from the classroom successfully.")
                .build();
    }

    @GetMapping("/schedule-by-username/{username}")
    public ApiResponse<List<ClassroomResponse>> getClassScheduleByUsername(@PathVariable("username") String username) {
        return ApiResponse.<List<ClassroomResponse>>builder()
                .result(userClassroomService.getClassScheduleByUsername(username))
                .build();
    }

    @GetMapping("/schedule-by-userId/{userId}")
    public ApiResponse<List<ClassroomResponse>> getClassScheduleByUserId(@PathVariable("userId") String userId) {
        return ApiResponse.<List<ClassroomResponse>>builder()
                .result(userClassroomService.getClassScheduleByUserId(userId))
                .build();
    }
}
