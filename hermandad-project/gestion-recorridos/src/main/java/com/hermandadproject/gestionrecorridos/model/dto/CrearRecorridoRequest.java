package com.hermandadproject.gestionrecorridos.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record CrearRecorridoRequest(
        @NotNull UUID idHermandad,
        @NotNull UUID idCiudad,
        @NotNull UUID idMapaCiudad,
        @NotNull UUID idIglesiaSede,
        @NotNull UUID idNodoInicio,
        @NotBlank @Size(max = 150) String nombre,
        @Size(max = 500) String descripcion,
        @NotEmpty @Size(min = 3) List<@NotNull UUID> idsNodos
) {
}
