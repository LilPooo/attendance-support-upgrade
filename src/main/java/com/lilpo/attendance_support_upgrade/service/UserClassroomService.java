package com.lilpo.attendance_support_upgrade.service;

import com.lilpo.attendance_support_upgrade.dto.response.ClassroomResponse;
import com.lilpo.attendance_support_upgrade.entity.Classroom;
import com.lilpo.attendance_support_upgrade.entity.User;
import com.lilpo.attendance_support_upgrade.entity.UserClassroom;
import com.lilpo.attendance_support_upgrade.exception.AppException;
import com.lilpo.attendance_support_upgrade.exception.ErrorCode;
import com.lilpo.attendance_support_upgrade.mapper.ClassroomMapper;
import com.lilpo.attendance_support_upgrade.repository.ClassroomRepository;
import com.lilpo.attendance_support_upgrade.repository.UserClassroomRepository;
import com.lilpo.attendance_support_upgrade.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserClassroomService {
    UserClassroomRepository userClassroomRepository;
    UserRepository userRepository;
    ClassroomRepository classroomRepository;
    ClassroomMapper classroomMapper;


    public void addUserToClassroomById(String userId, Long classroomId) {
        User user = getUserById(userId);
        Classroom classroom = getClassroomById(classroomId);

        if (userClassroomRepository.existsByUserAndClassroom(user, classroom)) {
            throw new AppException(ErrorCode.USER_ALREADY_IN_CLASSROOM);
        }

        UserClassroom userClassroom = UserClassroom.builder()
                .user(user)
                .classroom(classroom)
                .active(true)
                .build();

        userClassroomRepository.save(userClassroom);
    }

    public void addUserToClassroomByUsername(String username, Long classroomId) {
        User user = getUserByUsername(username);

        Classroom classroom = getClassroomById(classroomId);

        if (userClassroomRepository.existsByUserAndClassroom(user, classroom)) {
            throw new AppException(ErrorCode.USER_ALREADY_IN_CLASSROOM);
        }

        UserClassroom userClassroom = UserClassroom.builder()
                .user(user)
                .classroom(classroom)
                .active(true)
                .build();

        userClassroomRepository.save(userClassroom);
    }

    public void removeUserFromClassroomById(String userId, Long classroomId) {
        UserClassroom userClassroom = userClassroomRepository.findByUserIdAndClassroomId(userId, classroomId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_IN_CLASSROOM));

        userClassroomRepository.delete(userClassroom);
        log.info("User {} has been removed from classroom {}", userId, classroomId);
    }

    public void removeUserFromClassroomByUsername(String username, Long classroomId) {
        User user = getUserByUsername(username);
        Classroom classroom = getClassroomById(classroomId);

        UserClassroom userClassroom = userClassroomRepository.findByUserAndClassroom(user, classroom)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_IN_CLASSROOM));
        userClassroomRepository.delete(userClassroom);
    }

    private Classroom getClassroomById(Long classroomId) {
        return classroomRepository.findById(classroomId)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSROOM_NOT_EXISTED));
    }

    private User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public List<ClassroomResponse> getClassScheduleByUsername(String username) {
        List<Classroom> classrooms = userClassroomRepository.findClassroomsByUsername(username);

        if (classrooms.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_IN_CLASSROOM);
        }
        return classrooms.stream()
                .map(classroomMapper::toClassroomResponse)
                .toList();
    }

    public List<ClassroomResponse> getClassScheduleByUserId(String userId) {
        List<Classroom> classrooms = userClassroomRepository.findClassroomByUserId(userId);
        if (classrooms.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_IN_CLASSROOM);
        }

        return classrooms.stream()
                .map(classroomMapper::toClassroomResponse)
                .toList();
    }
}
