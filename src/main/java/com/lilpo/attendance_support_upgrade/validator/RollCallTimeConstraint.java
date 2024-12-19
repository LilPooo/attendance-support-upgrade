package com.lilpo.attendance_support_upgrade.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RollCallTimeValidator.class)
public @interface RollCallTimeConstraint {
    String message() default "Invalid roll call time: start time must be before end time and cannot be null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
