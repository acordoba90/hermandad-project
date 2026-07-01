package com.hermandadproject.gestionrecorridos.model.dto;

import com.hermandadproject.gestionrecorridos.model.enums.EstadoRecorridoEnum;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record RecorridoResponse(
        UUID id,
        UUID idHermandad,
        UUID idCiudad,
        UUID idMapaCiudad,
        UUID idIglesiaSede,
        UUID idNodoInicio,
        UUID idNodoFin,
        String nombre,
        String descripcion,
        EstadoRecorridoEnum estado,
        Integer distanciaTotalMetros,
        Integer minutosEstimados,
        Integer dificultadTotal,
        Boolean pasaCarreraOficial,
        Boolean activo,
        List<RecorridoNodoResponse> nodos,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {
}
