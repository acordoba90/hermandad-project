package com.hermandadproject.gestionhermandades.service.impl;

import com.hermandadproject.gestionhermandades.exception.TipoHermandadCaracteristicasNotFoundException;
import com.hermandadproject.gestionhermandades.mapper.TipoHermandadCaracteristicasMapper;
import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadCaracteristicasResponse;
import com.hermandadproject.gestionhermandades.model.entity.TipoHermandadCaracteristicasEntity;
import com.hermandadproject.gestionhermandades.repository.TipoHermandadCaracteristicasRepository;
import com.hermandadproject.gestionhermandades.service.TipoHermandadCaracteristicasService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TipoHermandadCaracteristicasServiceImpl implements TipoHermandadCaracteristicasService {

    private final TipoHermandadCaracteristicasRepository repository;
    private final TipoHermandadCaracteristicasMapper mapper;

    public TipoHermandadCaracteristicasServiceImpl(
            TipoHermandadCaracteristicasRepository repository,
            TipoHermandadCaracteristicasMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TipoHermandadCaracteristicasResponse buscarPorTipoHermandadUuid(UUID tipoHermandadUuid) {
        TipoHermandadCaracteristicasEntity entity = repository.findByTipoHermandadUuid(tipoHermandadUuid)
                .orElseThrow(() -> new TipoHermandadCaracteristicasNotFoundException("Caracteristicas de tipo de hermandad no encontradas"));
        return mapper.toResponse(entity);
    }

    @Override
    public TipoHermandadCaracteristicasResponse buscarPorCodigoTipoHermandad(String codigo) {
        TipoHermandadCaracteristicasEntity entity = repository.findByTipoHermandadCodigo(codigo)
                .orElseThrow(() -> new TipoHermandadCaracteristicasNotFoundException("Caracteristicas de tipo de hermandad no encontradas"));
        return mapper.toResponse(entity);
    }
}
