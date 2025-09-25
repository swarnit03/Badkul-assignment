package com.badkultech.trip_management.validator;

import com.badkultech.trip_management.dto.TripRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class EndDateAfterStartDateValidator implements ConstraintValidator<EndDateAfterStartDate, TripRequestDto> {

    @Override
    public boolean isValid(TripRequestDto dto, ConstraintValidatorContext context) {
        if (dto == null) return true;
        LocalDate s = dto.getStartDate();
        LocalDate e = dto.getEndDate();
        if (s == null || e == null) return true; 
        return !e.isBefore(s);
    }
}
