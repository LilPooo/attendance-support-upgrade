package com.lilpo.attendance_support_upgrade.dto.request;

import com.lilpo.attendance_support_upgrade.enums.DayOfWeek;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassroomUpdateRequest {
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
    Set<DayOfWeek> daysOfWeek;
}
