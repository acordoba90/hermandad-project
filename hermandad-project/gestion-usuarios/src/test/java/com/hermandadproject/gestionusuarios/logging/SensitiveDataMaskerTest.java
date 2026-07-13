package com.hermandadproject.gestionusuarios.logging;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SensitiveDataMaskerTest {

    @Test
    void maskEmailMasksValidEmailWithoutHidingDomain() {
        String maskedEmail = SensitiveDataMasker.maskEmail("antonio.cordoba@gmail.com");

        assertThat(maskedEmail).isEqualTo("a*************a@gmail.com");
    }

    @Test
    void maskEmailHandlesNullValue() {
        assertThat(SensitiveDataMasker.maskEmail(null)).isEqualTo("null");
    }

    @Test
    void maskEmailHandlesInvalidEmailWithoutExposingOriginalValue() {
        assertThat(SensitiveDataMasker.maskEmail("not-an-email")).isEqualTo("********");
    }
}
