package com.hermandadproject.gestionhermandades.model.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TipoHermandadCaracteristicasResponse(
        UUID uuid,
        UUID tipoHermandadUuid,
        String codigoTipoHermandad,
        String resumenJugable,
        BigDecimal costeMantenimientoBase,
        BigDecimal ingresosBase,
        Integer dificultadBase,
        Integer devocionBase,
        Integer influenciaEclesiasticaBase,
        Integer influenciaSocialBase,
        Integer capacidadCrecimiento,
        Boolean permiteCarreraOficial,
        Boolean permitePatrimonioAvanzado,
        Boolean permiteBandaMusica,
        Boolean permiteCuerpoNazarenos,
        Boolean permiteCuadrillaCostaleros,
        String tipoPrevioRequerido,
        String requisitosEvolucion,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {
}
