package com.hermandadproject.gestionusuarios.logging;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

class CorrelationIdFilterTest {

    private final CorrelationIdFilter filter = new CorrelationIdFilter();

    @Test
    void doFilterGeneratesCorrelationIdWhenHeaderIsMissing() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/users");
        MockHttpServletResponse response = new MockHttpServletResponse();
        AtomicReference<String> mdcValueInsideFilter = new AtomicReference<>();

        filter.doFilter(request, response, (servletRequest, servletResponse) ->
                mdcValueInsideFilter.set(MDC.get(CorrelationIdFilter.CORRELATION_ID_MDC_KEY)));

        String responseCorrelationId = response.getHeader(CorrelationIdFilter.CORRELATION_ID_HEADER);
        assertThat(responseCorrelationId).isNotBlank();
        assertThat(UUID.fromString(responseCorrelationId)).isNotNull();
        assertThat(mdcValueInsideFilter.get()).isEqualTo(responseCorrelationId);
        assertThat(MDC.get(CorrelationIdFilter.CORRELATION_ID_MDC_KEY)).isNull();
    }

    @Test
    void doFilterReusesReceivedCorrelationIdAndCleansMdc() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/users");
        MockHttpServletResponse response = new MockHttpServletResponse();
        String receivedCorrelationId = "external-correlation-id";
        request.addHeader(CorrelationIdFilter.CORRELATION_ID_HEADER, receivedCorrelationId);
        AtomicReference<String> mdcValueInsideFilter = new AtomicReference<>();

        filter.doFilter(request, response, (servletRequest, servletResponse) ->
                mdcValueInsideFilter.set(MDC.get(CorrelationIdFilter.CORRELATION_ID_MDC_KEY)));

        assertThat(response.getHeader(CorrelationIdFilter.CORRELATION_ID_HEADER)).isEqualTo(receivedCorrelationId);
        assertThat(mdcValueInsideFilter.get()).isEqualTo(receivedCorrelationId);
        assertThat(MDC.get(CorrelationIdFilter.CORRELATION_ID_MDC_KEY)).isNull();
    }
}
