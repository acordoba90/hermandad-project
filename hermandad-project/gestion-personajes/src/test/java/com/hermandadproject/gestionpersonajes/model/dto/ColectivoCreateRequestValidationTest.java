package com.hermandadproject.gestionpersonajes.model.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ColectivoCreateRequestValidationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void codigoEsObligatorio() {
        ColectivoCreateRequest request = new ColectivoCreateRequest(null, "Nombre", "Descripcion");

        assertThat(validator.validate(request))
                .anySatisfy(violation -> assertThat(violation.getPropertyPath()).hasToString("codigo"));
    }

    @Test
    void nombreEsObligatorio() {
        ColectivoCreateRequest request = new ColectivoCreateRequest("CODIGO_VALIDO", "", "Descripcion");

        assertThat(validator.validate(request))
                .anySatisfy(violation -> assertThat(violation.getPropertyPath()).hasToString("nombre"));
    }

    @Test
    void codigoDebeTenerFormatoTecnico() {
        ColectivoCreateRequest request = new ColectivoCreateRequest("codigo invalido", "Nombre", "Descripcion");

        assertThat(validator.validate(request))
                .anySatisfy(violation -> assertThat(violation.getPropertyPath()).hasToString("codigo"));
    }
}
