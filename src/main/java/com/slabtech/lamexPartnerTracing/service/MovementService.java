package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Movement;

import java.util.List;

public interface MovementService {

    List<Movement> findAllMovement();

    Movement findMovementById(int theId);

    Movement saveMovement(Movement theMovement);
    Movement saveInitialMovement(Movement theMovement);
}
