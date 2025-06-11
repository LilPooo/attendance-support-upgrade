package com.lilpo.attendance_support_upgrade.entity;

import com.lilpo.attendance_support_upgrade.enums.DayOfWeek;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity

public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<UserClassroom> userClassrooms;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<CheckIn> checkIns;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    Set<DayOfWeek> daysOfWeek;
}
