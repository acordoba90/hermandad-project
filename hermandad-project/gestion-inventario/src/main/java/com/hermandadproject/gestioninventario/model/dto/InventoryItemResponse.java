package com.hermandadproject.gestioninventario.model.dto;

import com.hermandadproject.gestioninventario.model.enums.InventoryItemTypeEnum;

import java.time.Instant;
import java.util.UUID;

public record InventoryItemResponse(
        UUID id,
        UUID idHermandad,
        InventoryItemTypeEnum tipoElemento,
        UUID idElemento,
        String codigoElemento,
        Integer cantidad,
        Boolean activo,
        Instant fechaAdquisicion,
        Instant fechaCreacion,
        Instant fechaActualizacion
) {
}
