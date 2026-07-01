package com.hermandadproject.gestionhermandades.mapper;

import com.hermandadproject.gestionhermandades.model.dto.CarismaHermandadResumenDto;
import com.hermandadproject.gestionhermandades.model.dto.HermandadCreateRequest;
import com.hermandadproject.gestionhermandades.model.dto.HermandadResponse;
import com.hermandadproject.gestionhermandades.model.dto.HermandadResumenDto;
import com.hermandadproject.gestionhermandades.model.dto.HermandadUpdateRequest;
import com.hermandadproject.gestionhermandades.model.entity.HermandadEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HermandadMapper {

    private final TipoHermandadMapper tipoHermandadMapper;
    private final CarismaHermandadMapper carismaHermandadMapper;
    private final EconomiaHermandadMapper economiaHermandadMapper;

    public HermandadMapper(
            TipoHermandadMapper tipoHermandadMapper,
            CarismaHermandadMapper carismaHermandadMapper,
            EconomiaHermandadMapper economiaHermandadMapper
    ) {
        this.tipoHermandadMapper = tipoHermandadMapper;
        this.carismaHermandadMapper = carismaHermandadMapper;
        this.economiaHermandadMapper = economiaHermandadMapper;
    }

    public HermandadEntity toEntity(HermandadCreateRequest request) {
        HermandadEntity entity = new HermandadEntity();
        entity.setIdUsuario(request.idUsuario());
        entity.setNombre(request.nombre());
        entity.setCiudad(request.ciudad());
        entity.setAnioFundacion(request.anioFundacion());
        return entity;
    }

    public HermandadResponse toResponse(HermandadEntity entity) {
        Set<CarismaHermandadResumenDto> carismasSecundarios = entity.getCarismasSecundarios()
                .stream()
                .map(carismaHermandadMapper::toResumenDto)
                .collect(Collectors.toSet());

        return new HermandadResponse(
                entity.getId(),
                entity.getIdUsuario(),
                entity.getNombre(),
                entity.getCiudad(),
                entity.getAnioFundacion(),
                entity.getEstado(),
                entity.getPrestigio(),
                entity.getPopularidad(),
                entity.getDevocion(),
                entity.getSolemnidad(),
                tipoHermandadMapper.toResumenDto(entity.getTipoHermandad()),
                carismaHermandadMapper.toResumenDto(entity.getCarismaPrincipal()),
                carismasSecundarios,
                economiaHermandadMapper.toResumenDto(entity.getEconomia()),
                entity.getDinero(),
                entity.getPrestigioGlobal(),
                entity.getDevocionGlobal(),
                entity.getSatisfaccionInterna(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion()
        );
    }

    public HermandadResumenDto toResumenDto(HermandadEntity entity) {
        return new HermandadResumenDto(
                entity.getId(),
                entity.getIdUsuario(),
                entity.getNombre(),
                entity.getCiudad(),
                entity.getEstado(),
                entity.getPrestigio(),
                entity.getPopularidad(),
                entity.getDevocion(),
                entity.getSolemnidad(),
                tipoHermandadMapper.toResumenDto(entity.getTipoHermandad()),
                carismaHermandadMapper.toResumenDto(entity.getCarismaPrincipal())
        );
    }

    public void updateEntity(HermandadEntity entity, HermandadUpdateRequest request) {
        entity.setNombre(request.nombre());
        entity.setCiudad(request.ciudad());
        entity.setAnioFundacion(request.anioFundacion());
    }
}

