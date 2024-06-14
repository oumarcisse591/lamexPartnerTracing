package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Movement;
import com.slabtech.lamexPartnerTracing.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovementServiceImpl implements MovementService{

    private MovementRepository movementRepository;
    private StockService stockService;

    @Autowired
    public MovementServiceImpl(MovementRepository theMovementRepository, StockService theStockService){
        movementRepository = theMovementRepository;
        stockService = theStockService;
    }


    @Override
    public List<Movement> findAllMovement() {
        return movementRepository.findAllByOrderByIdTransactionDesc();
    }

    @Override
    public Movement findMovementById(int theId) {
        Optional<Movement> result = movementRepository.findById(theId);
        Movement theMovement = null;
        if (result.isPresent()){
            theMovement = result.get();
        }
        else {
            throw new RuntimeException("Did not find the movement with id " + theId);
        }
        return theMovement;
    }

    @Override
    public Movement saveMovement(Movement theMovement) {
        if (theMovement.getTransactionType().equals("debit")){
            stockService.decreaseStockQuantity(theMovement.getStock().getId(), theMovement.getTransactionAmount());
        }
        else if (theMovement.getTransactionType().equals("credit")){
            stockService.increaseStockQuantity(theMovement.getStock().getId(), theMovement.getTransactionAmount());
        } else if (theMovement.getTransactionType().equals("recharge")) {
            stockService.rechargeStockQuantity(theMovement.getStock().getId(), theMovement.getTransactionAmount());
        }
        return movementRepository.save(theMovement);
    }

    @Override
    public Movement saveInitialMovement(Movement theMovement) {
        return movementRepository.save(theMovement);
    }
}
