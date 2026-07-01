package com.hermandadproject.gestionhermandades.service.impl;

import com.hermandadproject.gestionhermandades.exception.CarismaHermandadAlreadyExistsException;
import com.hermandadproject.gestionhermandades.exception.CarismaHermandadNotFoundException;
import com.hermandadproject.gestionhermandades.mapper.CarismaHermandadMapper;
import com.hermandadproject.gestionhermandades.model.dto.CarismaHermandadCreateDto;
import com.hermandadproject.gestionhermandades.model.dto.CarismaHermandadDto;
import com.hermandadproject.gestionhermandades.model.dto.CarismaHermandadUpdateDto;
import com.hermandadproject.gestionhermandades.model.entity.CarismaHermandadEntity;
import com.hermandadproject.gestionhermandades.repository.CarismaHermandadRepository;
import com.hermandadproject.gestionhermandades.service.CarismaHermandadService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class CarismaHermandadServiceImpl implements CarismaHermandadService {

    private final CarismaHermandadRepository carismaHermandadRepository;
    private final CarismaHermandadMapper carismaHermandadMapper;

    public CarismaHermandadServiceImpl(CarismaHermandadRepository carismaHermandadRepository, CarismaHermandadMapper carismaHermandadMapper) {
        this.carismaHermandadRepository = carismaHermandadRepository;
        this.carismaHermandadMapper = carismaHermandadMapper;
    }

    @Override
    public List<CarismaHermandadDto> findAll() {
        return carismaHermandadRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(CarismaHermandadEntity::getOrden))
                .map(carismaHermandadMapper::toDto)
                .toList();
    }

    @Override
    public List<CarismaHermandadDto> findActivos() {
        return carismaHermandadRepository.findByActivoTrueOrderByOrdenAsc()
                .stream()
                .map(carismaHermandadMapper::toDto)
                .toList();
    }

    @Override
    public CarismaHermandadDto findByUuid(UUID uuid) {
        CarismaHermandadEntity entity = carismaHermandadRepository.findById(uuid)
                .orElseThrow(() -> new CarismaHermandadNotFoundException("Carisma de hermandad no encontrado"));
        return carismaHermandadMapper.toDto(entity);
    }

    @Override
    public CarismaHermandadDto create(CarismaHermandadCreateDto dto) {
        String normalizedCode = normalizeCodigo(dto.codigo());
        if (carismaHermandadRepository.existsByCodigo(normalizedCode)) {
            throw new CarismaHermandadAlreadyExistsException("Ya existe un carisma con ese codigo");
        }

        CarismaHermandadEntity entity = carismaHermandadMapper.toEntity(dto);
        entity.setCodigo(normalizedCode);
        CarismaHermandadEntity saved = carismaHermandadRepository.save(entity);
        return carismaHermandadMapper.toDto(saved);
    }

    @Override
    public CarismaHermandadDto update(UUID uuid, CarismaHermandadUpdateDto dto) {
        CarismaHermandadEntity entity = carismaHermandadRepository.findById(uuid)
                .orElseThrow(() -> new CarismaHermandadNotFoundException("Carisma de hermandad no encontrado"));

        String normalizedCode = normalizeCodigo(dto.codigo());
        if (!entity.getCodigo().equals(normalizedCode) && carismaHermandadRepository.existsByCodigo(normalizedCode)) {
            throw new CarismaHermandadAlreadyExistsException("Ya existe un carisma con ese codigo");
        }

        carismaHermandadMapper.updateEntity(entity, dto);
        entity.setCodigo(normalizedCode);
        CarismaHermandadEntity saved = carismaHermandadRepository.save(entity);
        return carismaHermandadMapper.toDto(saved);
    }

    @Override
    public void delete(UUID uuid) {
        CarismaHermandadEntity entity = carismaHermandadRepository.findById(uuid)
                .orElseThrow(() -> new CarismaHermandadNotFoundException("Carisma de hermandad no encontrado"));
        entity.setActivo(false);
        carismaHermandadRepository.save(entity);
    }

    private static String normalizeCodigo(String codigo) {
        if (codigo == null) {
            return null;
        }
        return codigo.replaceAll("\\s+", "").toUpperCase(Locale.ROOT);
    }
}

