package com.hermandadproject.gestionhermandades.service.impl;

import com.hermandadproject.gestionhermandades.exception.CarismaHermandadAssignmentException;
import com.hermandadproject.gestionhermandades.exception.CarismaHermandadNotFoundException;
import com.hermandadproject.gestionhermandades.exception.HermandadAlreadyExistsException;
import com.hermandadproject.gestionhermandades.exception.HermandadNotFoundException;
import com.hermandadproject.gestionhermandades.exception.TipoHermandadNotFoundException;
import com.hermandadproject.gestionhermandades.mapper.HermandadMapper;
import com.hermandadproject.gestionhermandades.model.dto.HermandadCreateRequest;
import com.hermandadproject.gestionhermandades.model.dto.HermandadResponse;
import com.hermandadproject.gestionhermandades.model.dto.HermandadResumenDto;
import com.hermandadproject.gestionhermandades.model.dto.HermandadUpdateRequest;
import com.hermandadproject.gestionhermandades.model.entity.CarismaHermandadEntity;
import com.hermandadproject.gestionhermandades.model.entity.EconomiaHermandadEntity;
import com.hermandadproject.gestionhermandades.model.entity.HermandadEntity;
import com.hermandadproject.gestionhermandades.model.entity.TipoHermandadEntity;
import com.hermandadproject.gestionhermandades.repository.CarismaHermandadRepository;
import com.hermandadproject.gestionhermandades.repository.HermandadRepository;
import com.hermandadproject.gestionhermandades.repository.TipoHermandadRepository;
import com.hermandadproject.gestionhermandades.service.EconomiaHermandadService;
import com.hermandadproject.gestionhermandades.service.HermandadService;
import com.hermandadproject.gestionhermandades.service.IndicadoresHermandadCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class HermandadServiceImpl implements HermandadService {

    private static final BigDecimal DINERO_POR_DEFECTO = new BigDecimal("10000.00");
    private static final int PRESTIGIO_POR_DEFECTO = 10;
    private static final int DEVOCION_POR_DEFECTO = 10;
    private static final int SATISFACCION_INTERNA_POR_DEFECTO = 50;

    private final HermandadRepository hermandadRepository;
    private final HermandadMapper hermandadMapper;
    private final TipoHermandadRepository tipoHermandadRepository;
    private final CarismaHermandadRepository carismaHermandadRepository;
    private final EconomiaHermandadService economiaHermandadService;
    private final IndicadoresHermandadCalculator indicadoresHermandadCalculator;

    public HermandadServiceImpl(
            HermandadRepository hermandadRepository,
            HermandadMapper hermandadMapper,
            TipoHermandadRepository tipoHermandadRepository,
            CarismaHermandadRepository carismaHermandadRepository,
            EconomiaHermandadService economiaHermandadService,
            IndicadoresHermandadCalculator indicadoresHermandadCalculator
    ) {
        this.hermandadRepository = hermandadRepository;
        this.hermandadMapper = hermandadMapper;
        this.tipoHermandadRepository = tipoHermandadRepository;
        this.carismaHermandadRepository = carismaHermandadRepository;
        this.economiaHermandadService = economiaHermandadService;
        this.indicadoresHermandadCalculator = indicadoresHermandadCalculator;
    }

    @Override
    @Transactional
    public HermandadResponse create(HermandadCreateRequest request) {
        if (hermandadRepository.existsByIdUsuarioAndNombre(request.idUsuario(), request.nombre())) {
            throw new HermandadAlreadyExistsException("Ya existe una hermandad con ese nombre para este usuario");
        }

        HermandadEntity entity = hermandadMapper.toEntity(request);

        TipoHermandadEntity tipoHermandad = tipoHermandadRepository.findById(request.uuidTipoHermandad())
                .orElseThrow(() -> new TipoHermandadNotFoundException("Tipo de hermandad no encontrado"));
        entity.setTipoHermandad(tipoHermandad);

        resolveAndAssignCarismas(entity, request.uuidCarismaPrincipal(), request.uuidsCarismasSecundarios());
        indicadoresHermandadCalculator.recalcularIndicadores(entity);

        EconomiaHermandadEntity economia = economiaHermandadService.crearEconomiaInicial(entity);
        entity.setEconomia(economia);
        economia.setHermandad(entity);

        entity.setDinero(DINERO_POR_DEFECTO);
        entity.setPrestigioGlobal(PRESTIGIO_POR_DEFECTO);
        entity.setDevocionGlobal(DEVOCION_POR_DEFECTO);
        entity.setSatisfaccionInterna(SATISFACCION_INTERNA_POR_DEFECTO);

        HermandadEntity saved = hermandadRepository.save(entity);
        return hermandadMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public HermandadResponse findById(UUID id) {
        HermandadEntity entity = hermandadRepository.findById(id)
                .orElseThrow(() -> new HermandadNotFoundException("Hermandad no encontrada"));
        return hermandadMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HermandadResponse> findByIdUsuario(UUID idUsuario) {
        return hermandadRepository.findByIdUsuario(idUsuario)
                .stream()
                .map(hermandadMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public HermandadResponse update(UUID id, HermandadUpdateRequest request) {
        HermandadEntity entity = hermandadRepository.findById(id)
                .orElseThrow(() -> new HermandadNotFoundException("Hermandad no encontrada"));

        if (!entity.getNombre().equals(request.nombre())
                && hermandadRepository.existsByIdUsuarioAndNombre(entity.getIdUsuario(), request.nombre())) {
            throw new HermandadAlreadyExistsException("Ya existe una hermandad con ese nombre para este usuario");
        }

        hermandadMapper.updateEntity(entity, request);

        if (entity.getTipoHermandad() == null || !entity.getTipoHermandad().getUuid().equals(request.uuidTipoHermandad())) {
            TipoHermandadEntity tipoHermandad = tipoHermandadRepository.findById(request.uuidTipoHermandad())
                    .orElseThrow(() -> new TipoHermandadNotFoundException("Tipo de hermandad no encontrado"));
            entity.setTipoHermandad(tipoHermandad);
        }

        resolveAndAssignCarismas(entity, request.uuidCarismaPrincipal(), request.uuidsCarismasSecundarios());
        indicadoresHermandadCalculator.recalcularIndicadores(entity);

        if (entity.getEconomia() == null) {
            EconomiaHermandadEntity economia = economiaHermandadService.crearEconomiaInicial(entity);
            entity.setEconomia(economia);
            economia.setHermandad(entity);
        }

        HermandadEntity saved = hermandadRepository.save(entity);
        return hermandadMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!hermandadRepository.existsById(id)) {
            throw new HermandadNotFoundException("Hermandad no encontrada");
        }
        hermandadRepository.deleteById(id);
    }

    @Override
    @Transactional
    public HermandadResponse recalcularIndicadores(UUID id) {
        HermandadEntity entity = hermandadRepository.findById(id)
                .orElseThrow(() -> new HermandadNotFoundException("Hermandad no encontrada"));
        indicadoresHermandadCalculator.recalcularIndicadores(entity);
        HermandadEntity saved = hermandadRepository.save(entity);
        return hermandadMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HermandadResumenDto> rankingPrestigio() {
        return hermandadRepository.findAllByOrderByPrestigioDesc()
                .stream()
                .map(hermandadMapper::toResumenDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HermandadResumenDto> rankingPopularidad() {
        return hermandadRepository.findAllByOrderByPopularidadDesc()
                .stream()
                .map(hermandadMapper::toResumenDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HermandadResumenDto> rankingDevocion() {
        return hermandadRepository.findAllByOrderByDevocionDesc()
                .stream()
                .map(hermandadMapper::toResumenDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HermandadResumenDto> rankingSolemnidad() {
        return hermandadRepository.findAllByOrderBySolemnidadDesc()
                .stream()
                .map(hermandadMapper::toResumenDto)
                .toList();
    }

    private void resolveAndAssignCarismas(HermandadEntity entity, UUID uuidCarismaPrincipal, Set<UUID> uuidsCarismasSecundarios) {
        CarismaHermandadEntity principal = null;
        if (uuidCarismaPrincipal != null) {
            principal = carismaHermandadRepository.findById(uuidCarismaPrincipal)
                    .orElseThrow(() -> new CarismaHermandadNotFoundException("Carisma principal no encontrado"));
        }

        Set<CarismaHermandadEntity> secundarios = new HashSet<>();
        if (uuidsCarismasSecundarios != null && !uuidsCarismasSecundarios.isEmpty()) {
            List<CarismaHermandadEntity> found = carismaHermandadRepository.findByUuidIn(uuidsCarismasSecundarios);
            if (found.size() != uuidsCarismasSecundarios.size()) {
                Set<UUID> foundUuids = found.stream()
                        .map(CarismaHermandadEntity::getUuid)
                        .collect(java.util.stream.Collectors.toSet());
                Set<UUID> missing = new HashSet<>(uuidsCarismasSecundarios);
                missing.removeAll(foundUuids);
                throw new CarismaHermandadNotFoundException("Carismas secundarios inexistentes: " + missing);
            }
            secundarios.addAll(found);
        }

        UUID principalUuid = principal != null ? principal.getUuid() : null;

        if (principalUuid != null && secundarios.stream().anyMatch(c -> c.getUuid().equals(principalUuid))) {
            throw new CarismaHermandadAssignmentException("El carisma principal no puede estar tambien como secundario");
        }

        entity.setCarismaPrincipal(principal);
        entity.setCarismasSecundarios(secundarios);
    }
}
