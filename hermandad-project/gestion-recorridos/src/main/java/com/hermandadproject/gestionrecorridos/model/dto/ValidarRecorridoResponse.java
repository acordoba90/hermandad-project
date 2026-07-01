package com.hermandadproject.gestionrecorridos.model.dto;

import java.util.List;

public record ValidarRecorridoResponse(
        Boolean valido,
        List<String> mensajes,
        Integer distanciaTotalMetros,
        Integer minutosEstimados,
        Integer dificultadTotal,
        Boolean pasaCarreraOficial
) {
}
