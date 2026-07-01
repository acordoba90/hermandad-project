package com.hermandadproject.gestionrecompensas.service.impl;

import com.hermandadproject.gestionrecompensas.exception.RewardPackNotFoundException;
import com.hermandadproject.gestionrecompensas.mapper.RewardPackMapper;
import com.hermandadproject.gestionrecompensas.model.dto.RewardPackResponse;
import com.hermandadproject.gestionrecompensas.model.entity.SobreRecompensaEntity;
import com.hermandadproject.gestionrecompensas.repository.RewardPackRepository;
import com.hermandadproject.gestionrecompensas.service.RewardPackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class RewardPackServiceImpl implements RewardPackService {

    private final RewardPackRepository rewardPackRepository;
    private final RewardPackMapper rewardPackMapper;

    public RewardPackServiceImpl(RewardPackRepository rewardPackRepository, RewardPackMapper rewardPackMapper) {
        this.rewardPackRepository = rewardPackRepository;
        this.rewardPackMapper = rewardPackMapper;
    }

    @Override
    public List<RewardPackResponse> findAllActive() {
        return rewardPackRepository.findByActivoTrue()
                .stream()
                .map(rewardPackMapper::toResponse)
                .toList();
    }

    @Override
    public RewardPackResponse findById(UUID id) {
        SobreRecompensaEntity entity = rewardPackRepository.findById(id)
                .orElseThrow(() -> new RewardPackNotFoundException("Sobre de recompensas no encontrado"));
        return rewardPackMapper.toResponse(entity);
    }

    @Override
    public RewardPackResponse findByCodigo(String codigo) {
        SobreRecompensaEntity entity = rewardPackRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RewardPackNotFoundException("Sobre de recompensas no encontrado"));
        return rewardPackMapper.toResponse(entity);
    }
}
