package com.lilpo.attendance_support_upgrade.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassroomResponse {

    Long id;
    String courseId;
    String address;

    LocalTime startTimeLearn;
    LocalTime endTimeLearn;

    LocalTime startRollCallTime;
    LocalTime endRollCallTime;

    double latitude;
    double longitude;

    int startWeek;
    int endWeek;

    String name;
}
