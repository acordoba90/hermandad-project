package com.hermandadproject.gestionhermandades.service.impl;

import com.hermandadproject.gestionhermandades.exception.HermandadNotFoundException;
import com.hermandadproject.gestionhermandades.exception.MovimientoEconomicoHermandadValidationException;
import com.hermandadproject.gestionhermandades.mapper.MovimientoEconomicoHermandadMapper;
import com.hermandadproject.gestionhermandades.model.dto.MovimientoEconomicoHermandadCreateDto;
import com.hermandadproject.gestionhermandades.model.dto.MovimientoEconomicoHermandadDto;
import com.hermandadproject.gestionhermandades.model.entity.EconomiaHermandadEntity;
import com.hermandadproject.gestionhermandades.model.entity.HermandadEntity;
import com.hermandadproject.gestionhermandades.model.entity.MovimientoEconomicoHermandadEntity;
import com.hermandadproject.gestionhermandades.model.enums.TipoMovimientoEconomico;
import com.hermandadproject.gestionhermandades.repository.EconomiaHermandadRepository;
import com.hermandadproject.gestionhermandades.repository.HermandadRepository;
import com.hermandadproject.gestionhermandades.repository.MovimientoEconomicoHermandadRepository;
import com.hermandadproject.gestionhermandades.service.EconomiaHermandadService;
import com.hermandadproject.gestionhermandades.service.MovimientoEconomicoHermandadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MovimientoEconomicoHermandadServiceImpl implements MovimientoEconomicoHermandadService {

    private static final boolean ALLOW_NEGATIVE_BALANCE = true;

    private final HermandadRepository hermandadRepository;
    private final EconomiaHermandadRepository economiaHermandadRepository;
    private final EconomiaHermandadService economiaHermandadService;
    private final MovimientoEconomicoHermandadRepository movimientoEconomicoHermandadRepository;
    private final MovimientoEconomicoHermandadMapper movimientoEconomicoHermandadMapper;

    public MovimientoEconomicoHermandadServiceImpl(
            HermandadRepository hermandadRepository,
            EconomiaHermandadRepository economiaHermandadRepository,
            EconomiaHermandadService economiaHermandadService,
            MovimientoEconomicoHermandadRepository movimientoEconomicoHermandadRepository,
            MovimientoEconomicoHermandadMapper movimientoEconomicoHermandadMapper
    ) {
        this.hermandadRepository = hermandadRepository;
        this.economiaHermandadRepository = economiaHermandadRepository;
        this.economiaHermandadService = economiaHermandadService;
        this.movimientoEconomicoHermandadRepository = movimientoEconomicoHermandadRepository;
        this.movimientoEconomicoHermandadMapper = movimientoEconomicoHermandadMapper;
    }

    @Override
    public MovimientoEconomicoHermandadDto create(MovimientoEconomicoHermandadCreateDto dto) {
        UUID uuidHermandad = dto.uuidHermandad();
        if (uuidHermandad == null) {
            throw new MovimientoEconomicoHermandadValidationException("uuidHermandad es obligatorio");
        }

        HermandadEntity hermandad = hermandadRepository.findById(uuidHermandad)
                .orElseThrow(() -> new HermandadNotFoundException("Hermandad no encontrada"));

        if (dto.importe() == null || dto.importe().signum() <= 0) {
            throw new MovimientoEconomicoHermandadValidationException("importe debe ser > 0");
        }

        // Asegura que existe economia.
        economiaHermandadService.findByHermandad(uuidHermandad);
        EconomiaHermandadEntity economia = economiaHermandadRepository.findByHermandadId(uuidHermandad)
                .orElseThrow(() -> new MovimientoEconomicoHermandadValidationException("La hermandad no tiene economia"));

        MovimientoEconomicoHermandadEntity entity = movimientoEconomicoHermandadMapper.toEntity(dto);
        entity.setHermandad(hermandad);
        entity.setFechaMovimiento(dto.fechaMovimiento() == null ? LocalDate.now() : dto.fechaMovimiento());
        entity.setFechaRegistro(LocalDateTime.now());

        BigDecimal newBalance = applyToBalance(economia.getSaldoActual(), dto.tipoMovimiento(), dto.importe());
        if (!ALLOW_NEGATIVE_BALANCE && newBalance.signum() < 0) {
            throw new MovimientoEconomicoHermandadValidationException("Saldo insuficiente para registrar el gasto");
        }

        economia.setSaldoActual(newBalance);
        economiaHermandadRepository.save(economia);

        MovimientoEconomicoHermandadEntity saved = movimientoEconomicoHermandadRepository.save(entity);
        return movimientoEconomicoHermandadMapper.toDto(saved);
    }

    @Override
    public List<MovimientoEconomicoHermandadDto> findByHermandad(UUID uuidHermandad) {
        return movimientoEconomicoHermandadRepository.findByHermandadIdOrderByFechaMovimientoDesc(uuidHermandad)
                .stream()
                .map(movimientoEconomicoHermandadMapper::toDto)
                .toList();
    }

    @Override
    public List<MovimientoEconomicoHermandadDto> findIngresosByHermandad(UUID uuidHermandad) {
        return movimientoEconomicoHermandadRepository
                .findByHermandadIdAndTipoMovimientoOrderByFechaMovimientoDesc(uuidHermandad, TipoMovimientoEconomico.INGRESO)
                .stream()
                .map(movimientoEconomicoHermandadMapper::toDto)
                .toList();
    }

    @Override
    public List<MovimientoEconomicoHermandadDto> findGastosByHermandad(UUID uuidHermandad) {
        return movimientoEconomicoHermandadRepository
                .findByHermandadIdAndTipoMovimientoOrderByFechaMovimientoDesc(uuidHermandad, TipoMovimientoEconomico.GASTO)
                .stream()
                .map(movimientoEconomicoHermandadMapper::toDto)
                .toList();
    }

    @Override
    public void delete(UUID uuidMovimiento) {
        movimientoEconomicoHermandadRepository.deleteById(uuidMovimiento);
    }

    private static BigDecimal applyToBalance(BigDecimal current, TipoMovimientoEconomico tipo, BigDecimal importe) {
        BigDecimal base = current == null ? BigDecimal.ZERO : current;
        return tipo == TipoMovimientoEconomico.INGRESO ? base.add(importe) : base.subtract(importe);
    }
}
