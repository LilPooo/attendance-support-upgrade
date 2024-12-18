package com.lilpo.attendance_support_upgrade.dto.request;

import com.lilpo.attendance_support_upgrade.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @Size(min = 2, message = "INVALID_PASSWORD")
    String password;


    String firstName;
    String lastName;
    String phone;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    int gender;
    String major;
    String deviceId;

}
