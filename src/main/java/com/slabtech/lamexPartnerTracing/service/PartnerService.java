package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Partner;

import java.util.List;

public interface PartnerService {

    List<Partner> findAllPartner();
    Partner findPartnerById(int theId);
    Partner savePartner(Partner thePartner);
    long countAllPartners();
    long countInactivePartners();
}
