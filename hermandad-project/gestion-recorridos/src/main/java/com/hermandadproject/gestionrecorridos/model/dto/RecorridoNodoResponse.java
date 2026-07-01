package com.hermandadproject.gestionrecorridos.model.dto;

import java.util.UUID;

public record RecorridoNodoResponse(
        UUID id,
        UUID idNodoCiudad,
        String codigoNodo,
        String nombreNodo,
        Integer orden,
        Integer minutosDesdeAnterior,
        Integer distanciaDesdeAnteriorMetros,
        Integer dificultadTramo
) {
}
