package com.slabtech.lamexPartnerTracing.service;


import com.slabtech.lamexPartnerTracing.entity.Partner;
import com.slabtech.lamexPartnerTracing.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerServiceImpl implements PartnerService{

    private PartnerRepository partnerRepository;

    @Autowired
    public PartnerServiceImpl(PartnerRepository thePartnerRepository){
        partnerRepository = thePartnerRepository;
    }
    @Override
    public List<Partner> findAllPartner() {
        return partnerRepository.findAll();
    }

    @Override
    public Partner findPartnerById(int theId) {
        Optional<Partner> result = partnerRepository.findById(theId);
        Partner thePartner = null;
        if (result.isPresent()){
            thePartner = result.get();
        }
        else {
            throw new RuntimeException("Did not find the Partner with id" + theId);
        }
        return thePartner;
    }

    @Override
    public Partner savePartner(Partner thePartner) {
        return partnerRepository.save(thePartner);
    }

    @Override
    public long countAllPartners() {
        return partnerRepository.countAllPartners();
    }

    @Override
    public long countInactivePartners() {
        return partnerRepository.countInactivePartners();
    }
}
