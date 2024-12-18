package com.lilpo.attendance_support_upgrade.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String firstName;
    String lastName;
    String phone;
    LocalDate dob;
    int gender;
    String major;
    String deviceId;
    Set<RoleResponse> roles;
}
