package com.slabtech.lamexPartnerTracing.service;

import com.slabtech.lamexPartnerTracing.entity.Partner;

import java.util.List;
import java.util.UUID;

public interface PartnerService {

    List<Partner> findAllPartner();
    Partner findPartnerById(UUID theId);
    Partner savePartner(Partner thePartner);
    long countAllPartners();
    long countInactivePartners();
}
