package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Guichet;
import com.slabtech.lamexPartnerTracing.repository.GuichetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuichetServiceImpl implements GuichetService{

    @Autowired
    private GuichetRepository guichetRepository;

    @Override
    public Guichet saveGuichet(Guichet theGuichet) {
        return guichetRepository.save(theGuichet);
    }
}
