package com.lilpo.attendance_support_upgrade.dto.request;

import com.lilpo.attendance_support_upgrade.validator.RollCallTimeConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RollCallTimeConstraint(message = "INVALID_ROLL_CALL_TIME")
public class ClassroomPatchRequest {
    LocalTime startRollCallTime;
    LocalTime endRollCallTime;
}
