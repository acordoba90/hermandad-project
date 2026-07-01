package com.hermandadproject.gestionrecompensas.service;

import com.hermandadproject.gestionrecompensas.model.dto.RewardPackResponse;

import java.util.List;
import java.util.UUID;

public interface RewardPackService {
    List<RewardPackResponse> findAllActive();

    RewardPackResponse findById(UUID id);

    RewardPackResponse findByCodigo(String codigo);
}
