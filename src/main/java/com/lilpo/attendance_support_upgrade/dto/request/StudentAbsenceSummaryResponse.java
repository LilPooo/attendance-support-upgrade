package com.lilpo.attendance_support_upgrade.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentAbsenceSummaryResponse {

    String userId;
    String username;
    Long absenceCount;
}
