package com.hermandadproject.gestionpersonajes.model.dto;

import com.hermandadproject.gestionpersonajes.model.PerfilPersonajeRules;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PerfilPersonalizadoRequestValidationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void atributosDebenEstarEntreCeroYCien() {
        PerfilPersonalizadoRequest request = request(101);

        assertThat(validator.validate(request))
                .anySatisfy(violation -> assertThat(violation.getPropertyPath()).hasToString("liderazgo"));
    }

    @Test
    void totalMaximoEstaCentralizado() {
        assertThat(PerfilPersonajeRules.TOTAL_PUNTOS_PERFIL_PERSONALIZADO).isEqualTo(1000);
        assertThat(PerfilPersonajeRules.totalPuntos(request(50))).isEqualTo(850);
    }

    private PerfilPersonalizadoRequest request(Integer liderazgo) {
        return new PerfilPersonalizadoRequest(
                liderazgo,
                50,
                50,
                50,
                50,
                50,
                50,
                50,
                50,
                50,
                50,
                50,
                50,
                50,
                50,
                50,
                50
        );
    }
}
