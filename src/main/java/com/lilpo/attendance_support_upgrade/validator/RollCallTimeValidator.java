package com.lilpo.attendance_support_upgrade.validator;

import com.lilpo.attendance_support_upgrade.dto.request.ClassroomPatchRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class RollCallTimeValidator implements ConstraintValidator<RollCallTimeConstraint, ClassroomPatchRequest> {
    @Override
    public boolean isValid(ClassroomPatchRequest value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        LocalTime start = value.getStartRollCallTime();
        LocalTime end = value.getEndRollCallTime();

        if (start == null || end == null) {
            return false;
        }

        return start.isBefore(end);
    }

    @Override
    public void initialize(RollCallTimeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
