package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Movement;

import java.util.List;
import java.util.UUID;

public interface MovementService {

    List<Movement> findAllMovement();

    Movement findMovementById(UUID theId);

    Movement saveMovement(Movement theMovement);
    Movement saveInitialMovement(Movement theMovement);
}
