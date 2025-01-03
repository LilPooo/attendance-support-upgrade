package com.lilpo.attendance_support_upgrade.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String firstName;
    String lastName;
    String phone;
    LocalDate dob;
    int gender;
    String major;
    String deviceId;

    List<String> roles;
}
