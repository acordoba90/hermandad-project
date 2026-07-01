package com.hermandadproject.gestionhermandades.service.impl;

import com.hermandadproject.gestionhermandades.exception.TipoHermandadAlreadyExistsException;
import com.hermandadproject.gestionhermandades.exception.TipoHermandadNotFoundException;
import com.hermandadproject.gestionhermandades.mapper.TipoHermandadMapper;
import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadCreateDto;
import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadDto;
import com.hermandadproject.gestionhermandades.model.dto.TipoHermandadUpdateDto;
import com.hermandadproject.gestionhermandades.model.entity.TipoHermandadEntity;
import com.hermandadproject.gestionhermandades.repository.TipoHermandadRepository;
import com.hermandadproject.gestionhermandades.service.TipoHermandadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@Transactional
public class TipoHermandadServiceImpl implements TipoHermandadService {

    private final TipoHermandadRepository tipoHermandadRepository;
    private final TipoHermandadMapper tipoHermandadMapper;

    public TipoHermandadServiceImpl(TipoHermandadRepository tipoHermandadRepository, TipoHermandadMapper tipoHermandadMapper) {
        this.tipoHermandadRepository = tipoHermandadRepository;
        this.tipoHermandadMapper = tipoHermandadMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoHermandadDto> findAll() {
        return tipoHermandadRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(TipoHermandadEntity::getOrden))
                .map(tipoHermandadMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoHermandadDto> findActivos() {
        return tipoHermandadRepository.findByActivoTrueOrderByOrdenAsc()
                .stream()
                .map(tipoHermandadMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TipoHermandadDto findByUuid(UUID uuid) {
        TipoHermandadEntity entity = tipoHermandadRepository.findById(uuid)
                .orElseThrow(() -> new TipoHermandadNotFoundException("Tipo de hermandad no encontrado"));
        return tipoHermandadMapper.toDto(entity);
    }

    @Override
    public TipoHermandadDto create(TipoHermandadCreateDto dto) {
        String normalizedCode = normalizeCodigo(dto.codigo());
        if (tipoHermandadRepository.existsByCodigo(normalizedCode)) {
            throw new TipoHermandadAlreadyExistsException("Ya existe un tipo de hermandad con ese codigo");
        }

        TipoHermandadEntity entity = tipoHermandadMapper.toEntity(dto);
        entity.setCodigo(normalizedCode);
        TipoHermandadEntity saved = tipoHermandadRepository.save(entity);
        return tipoHermandadMapper.toDto(saved);
    }

    @Override
    public TipoHermandadDto update(UUID uuid, TipoHermandadUpdateDto dto) {
        TipoHermandadEntity entity = tipoHermandadRepository.findById(uuid)
                .orElseThrow(() -> new TipoHermandadNotFoundException("Tipo de hermandad no encontrado"));

        String normalizedCode = normalizeCodigo(dto.codigo());
        if (!entity.getCodigo().equals(normalizedCode) && tipoHermandadRepository.existsByCodigo(normalizedCode)) {
            throw new TipoHermandadAlreadyExistsException("Ya existe un tipo de hermandad con ese codigo");
        }

        tipoHermandadMapper.updateEntity(entity, dto);
        entity.setCodigo(normalizedCode);
        TipoHermandadEntity saved = tipoHermandadRepository.save(entity);
        return tipoHermandadMapper.toDto(saved);
    }

    @Override
    public void delete(UUID uuid) {
        TipoHermandadEntity entity = tipoHermandadRepository.findById(uuid)
                .orElseThrow(() -> new TipoHermandadNotFoundException("Tipo de hermandad no encontrado"));
        entity.setActivo(false);
        tipoHermandadRepository.save(entity);
    }

    private static String normalizeCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        return codigo.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);
    }
}
