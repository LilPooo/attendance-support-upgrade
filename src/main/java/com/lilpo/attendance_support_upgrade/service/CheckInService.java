package com.lilpo.attendance_support_upgrade.service;

import com.lilpo.attendance_support_upgrade.dto.request.ClassroomPatchRequest;
import com.lilpo.attendance_support_upgrade.entity.CheckIn;
import com.lilpo.attendance_support_upgrade.entity.Classroom;
import com.lilpo.attendance_support_upgrade.entity.User;
import com.lilpo.attendance_support_upgrade.entity.UserClassroom;
import com.lilpo.attendance_support_upgrade.exception.AppException;
import com.lilpo.attendance_support_upgrade.exception.ErrorCode;
import com.lilpo.attendance_support_upgrade.repository.CheckInRepository;
import com.lilpo.attendance_support_upgrade.repository.ClassroomRepository;
import com.lilpo.attendance_support_upgrade.repository.UserClassroomRepository;
import com.lilpo.attendance_support_upgrade.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CheckInService {
    ClassroomRepository classroomRepository;
    UserClassroomRepository userClassroomRepository;
    UserRepository userRepository;
    private final CheckInRepository checkInRepository;


    //    @Value("${allowed.distance}")
    public double allowedDistance = 100;

    public void updateRollCallTime(Long classroomId, ClassroomPatchRequest request) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSROOM_NOT_EXISTED));

        classroom.setStartRollCallTime(request.getStartRollCallTime());
        classroom.setEndRollCallTime(request.getEndRollCallTime());
        classroomRepository.save(classroom);
    }

    public void createRollCallSession(Long classroomId, LocalTime startRollCallTime, LocalTime endRollCallTime) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSROOM_NOT_EXISTED));

        classroom.setStartRollCallTime(startRollCallTime);
        classroom.setEndRollCallTime(endRollCallTime);
        classroomRepository.save(classroom);


        List<UserClassroom> userClassrooms = userClassroomRepository.findStudentUserClassrooms(classroom);

        for (UserClassroom userClassroom : userClassrooms) {
            User user = userClassroom.getUser();


//            if (user.getRoles().stream().noneMatch(role -> role.getName().equals("STUDENT"))) {
//                continue;
//            }

            CheckIn checkIn = CheckIn.builder()
                    .user(user)
                    .classroom(classroom)
                    .time(LocalDateTime.now())
                    .status(false)
                    .build();
            checkInRepository.save(checkIn);
        }
    }

    public void studentCheckIn(String username, Long classroomId, String deviceId, double longitude, double latitude) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSROOM_NOT_EXISTED));

        //device condition
//        if (!user.getDeviceId().equals(deviceId)) {
//            throw new AppException(ErrorCode.INVALID_DEVICE_ID);
//        }

        //Time now
        LocalTime now = LocalTime.now();
        if (now.isBefore(classroom.getStartRollCallTime()) || now.isAfter(classroom.getEndRollCallTime())) {
            throw new AppException(ErrorCode.INVALID_ROLL_CALL_CHECK_IN);
        }

        //distance condition
        double distance = calculateDistance(classroom.getLatitude(), classroom.getLongitude(), latitude, longitude);

        if (distance > allowedDistance) {
            throw new AppException(ErrorCode.USER_TOO_FAR_FROM_CLASSROOM);
        }

        LocalDate today = LocalDate.now();
        List<CheckIn> checkIns = checkInRepository.findByUserAndClassroomAndDate(user, classroom, today);

        if (checkIns == null || checkIns.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        for (CheckIn checkIn : checkIns) {
            checkIn.setStatus(true);
            checkInRepository.save(checkIn);
        }

    }

    public void teacherCheckIn(String username, Long classroomId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new AppException(ErrorCode.CLASSROOM_NOT_EXISTED));

        LocalDate today = LocalDate.now();
        List<CheckIn> checkIns = checkInRepository.findByUserAndClassroomAndDate(user, classroom, today);

        if (checkIns == null || checkIns.isEmpty()) {
            throw new AppException(ErrorCode.CHECK_IN_RECORD_NOT_FOUND);
        }

        for (CheckIn checkIn : checkIns) {
            checkIn.setStatus(true);
        }
        checkInRepository.saveAll(checkIns);

    }

    //calculate distance
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in kilometers

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        // Convert to meters
        return R * c * 1000;
    }


}
