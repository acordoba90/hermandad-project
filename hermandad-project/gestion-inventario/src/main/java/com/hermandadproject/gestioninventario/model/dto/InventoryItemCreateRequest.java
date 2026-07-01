package com.hermandadproject.gestioninventario.model.dto;

import com.hermandadproject.gestioninventario.model.enums.InventoryItemTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record InventoryItemCreateRequest(
        @NotNull UUID idHermandad,
        @NotNull InventoryItemTypeEnum tipoElemento,
        UUID idElemento,
        @NotBlank @Size(max = 100) String codigoElemento,
        @Positive Integer cantidad
) {
}
