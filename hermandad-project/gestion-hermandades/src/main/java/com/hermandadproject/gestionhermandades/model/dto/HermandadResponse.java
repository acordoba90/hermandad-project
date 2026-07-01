package com.hermandadproject.gestionhermandades.model.dto;

import com.hermandadproject.gestionhermandades.model.enums.EstadoHermandad;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record HermandadResponse(
        UUID id,
        UUID idUsuario,
        String nombre,
        String ciudad,
        Integer anioFundacion,
        EstadoHermandad estado,
        Integer prestigio,
        Integer popularidad,
        Integer devocion,
        Integer solemnidad,
        TipoHermandadResumenDto tipoHermandad,
        CarismaHermandadResumenDto carismaPrincipal,
        Set<CarismaHermandadResumenDto> carismasSecundarios,
        EconomiaHermandadResumenDto economia,
        BigDecimal dinero,
        Integer prestigioGlobal,
        Integer devocionGlobal,
        Integer satisfaccionInterna,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion
) {
}
