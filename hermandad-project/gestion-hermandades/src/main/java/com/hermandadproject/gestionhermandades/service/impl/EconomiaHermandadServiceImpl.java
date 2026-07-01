package com.hermandadproject.gestionhermandades.service.impl;

import com.hermandadproject.gestionhermandades.exception.EconomiaHermandadValidationException;
import com.hermandadproject.gestionhermandades.exception.HermandadNotFoundException;
import com.hermandadproject.gestionhermandades.mapper.EconomiaHermandadMapper;
import com.hermandadproject.gestionhermandades.model.dto.EconomiaHermandadDto;
import com.hermandadproject.gestionhermandades.model.dto.EconomiaHermandadUpdateDto;
import com.hermandadproject.gestionhermandades.model.entity.EconomiaHermandadEntity;
import com.hermandadproject.gestionhermandades.model.entity.HermandadEntity;
import com.hermandadproject.gestionhermandades.repository.EconomiaHermandadRepository;
import com.hermandadproject.gestionhermandades.repository.HermandadRepository;
import com.hermandadproject.gestionhermandades.service.EconomiaHermandadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class EconomiaHermandadServiceImpl implements EconomiaHermandadService {

    private final HermandadRepository hermandadRepository;
    private final EconomiaHermandadRepository economiaHermandadRepository;
    private final EconomiaHermandadMapper economiaHermandadMapper;

    public EconomiaHermandadServiceImpl(
            HermandadRepository hermandadRepository,
            EconomiaHermandadRepository economiaHermandadRepository,
            EconomiaHermandadMapper economiaHermandadMapper
    ) {
        this.hermandadRepository = hermandadRepository;
        this.economiaHermandadRepository = economiaHermandadRepository;
        this.economiaHermandadMapper = economiaHermandadMapper;
    }

    @Override
    public EconomiaHermandadDto findByHermandad(UUID uuidHermandad) {
        HermandadEntity hermandad = hermandadRepository.findById(uuidHermandad)
                .orElseThrow(() -> new HermandadNotFoundException("Hermandad no encontrada"));

        EconomiaHermandadEntity economia = economiaHermandadRepository.findByHermandadId(uuidHermandad)
                .orElseGet(() -> economiaHermandadRepository.save(crearEconomiaInicial(hermandad)));

        return economiaHermandadMapper.toDto(economia);
    }

    @Override
    public EconomiaHermandadDto update(UUID uuidHermandad, EconomiaHermandadUpdateDto dto) {
        HermandadEntity hermandad = hermandadRepository.findById(uuidHermandad)
                .orElseThrow(() -> new HermandadNotFoundException("Hermandad no encontrada"));

        EconomiaHermandadEntity economia = economiaHermandadRepository.findByHermandadId(uuidHermandad)
                .orElseGet(() -> economiaHermandadRepository.save(crearEconomiaInicial(hermandad)));

        BigDecimal ingresos = zeroIfNull(dto.ingresosMensuales());
        BigDecimal gastos = zeroIfNull(dto.gastosMensuales());
        BigDecimal deuda = zeroIfNull(dto.deudaActual());
        BigDecimal patrimonio = zeroIfNull(dto.patrimonioEstimado());

        if (isNegative(ingresos) || isNegative(gastos) || isNegative(deuda) || isNegative(patrimonio)) {
            throw new EconomiaHermandadValidationException("No se permiten valores negativos en economia");
        }

        economia.setIngresosMensuales(ingresos);
        economia.setGastosMensuales(gastos);
        economia.setDeudaActual(deuda);
        economia.setPatrimonioEstimado(patrimonio);

        if (dto.nivelEstabilidadEconomica() != null) {
            int nivel = dto.nivelEstabilidadEconomica();
            if (nivel < 1 || nivel > 10) {
                throw new EconomiaHermandadValidationException("nivelEstabilidadEconomica debe estar entre 1 y 10");
            }
            economia.setNivelEstabilidadEconomica(nivel);
        }

        economia.setFechaUltimaActualizacion(LocalDate.now());
        EconomiaHermandadEntity saved = economiaHermandadRepository.save(economia);
        return economiaHermandadMapper.toDto(saved);
    }

    @Override
    public EconomiaHermandadEntity crearEconomiaInicial(HermandadEntity hermandad) {
        EconomiaHermandadEntity economia = new EconomiaHermandadEntity();
        economia.setHermandad(hermandad);
        economia.setSaldoActual(BigDecimal.ZERO);
        economia.setIngresosMensuales(BigDecimal.ZERO);
        economia.setGastosMensuales(BigDecimal.ZERO);
        economia.setDeudaActual(BigDecimal.ZERO);
        economia.setPatrimonioEstimado(BigDecimal.ZERO);
        economia.setNivelEstabilidadEconomica(5);
        economia.setFechaUltimaActualizacion(LocalDate.now());
        return economia;
    }

    @Override
    public EconomiaHermandadDto recalcularEconomia(UUID uuidHermandad) {
        // Por ahora, recalcular solo asegura que existe y actualiza fecha.
        EconomiaHermandadDto current = findByHermandad(uuidHermandad);
        update(uuidHermandad, new EconomiaHermandadUpdateDto(
                current.ingresosMensuales(),
                current.gastosMensuales(),
                current.deudaActual(),
                current.patrimonioEstimado(),
                current.nivelEstabilidadEconomica()
        ));
        return findByHermandad(uuidHermandad);
    }

    private static BigDecimal zeroIfNull(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private static boolean isNegative(BigDecimal value) {
        return value != null && value.signum() < 0;
    }
}
