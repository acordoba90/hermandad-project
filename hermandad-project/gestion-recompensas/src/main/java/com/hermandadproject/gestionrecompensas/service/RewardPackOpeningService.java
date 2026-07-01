package com.hermandadproject.gestionrecompensas.service;

import com.hermandadproject.gestionrecompensas.model.dto.OpenRewardPackByCodeRequest;
import com.hermandadproject.gestionrecompensas.model.dto.OpenRewardPackRequest;
import com.hermandadproject.gestionrecompensas.model.dto.RewardPackOpeningResponse;

import java.util.List;
import java.util.UUID;

public interface RewardPackOpeningService {
    RewardPackOpeningResponse openPack(OpenRewardPackRequest request);

    RewardPackOpeningResponse openPackByCode(OpenRewardPackByCodeRequest request);

    List<RewardPackOpeningResponse> findOpeningsByHermandadId(UUID idHermandad);
}
