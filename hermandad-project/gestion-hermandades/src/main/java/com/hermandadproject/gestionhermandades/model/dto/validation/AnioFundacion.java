package com.hermandadproject.gestionhermandades.model.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AnioFundacionValidator.class)
public @interface AnioFundacion {
    String message() default "El aÃ±o de fundaciÃ³n debe estar entre 1200 y el aÃ±o actual";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
