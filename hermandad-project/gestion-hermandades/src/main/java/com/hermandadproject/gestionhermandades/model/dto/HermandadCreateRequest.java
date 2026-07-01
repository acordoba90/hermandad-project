package com.hermandadproject.gestionhermandades.model.dto;

import com.hermandadproject.gestionhermandades.model.dto.validation.AnioFundacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;
import java.util.Set;

public record HermandadCreateRequest(
        @NotNull UUID idUsuario,
        @NotBlank @Size(max = 150) String nombre,
        @NotBlank @Size(max = 100) String ciudad,
        @AnioFundacion Integer anioFundacion,
        @NotNull UUID uuidTipoHermandad,
        UUID uuidCarismaPrincipal,
        Set<UUID> uuidsCarismasSecundarios
) {
}
