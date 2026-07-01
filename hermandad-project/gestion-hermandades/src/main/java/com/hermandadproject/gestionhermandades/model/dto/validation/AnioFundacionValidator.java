package com.hermandadproject.gestionhermandades.model.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class AnioFundacionValidator implements ConstraintValidator<AnioFundacion, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        int currentYear = Year.now().getValue();
        return value >= 1200 && value <= currentYear;
    }
}
