package com.badkultech.trip_management.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EndDateAfterStartDateValidator.class)
@Documented
public @interface EndDateAfterStartDate {
    String message() default "endDate must be the same or after startDate";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
