package com.hermandadproject.gestioninventario.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InventoryItemQuantityUpdateRequest(
        @NotNull @Positive Integer cantidad
) {
}
