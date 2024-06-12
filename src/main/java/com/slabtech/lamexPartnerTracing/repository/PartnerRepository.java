package com.slabtech.lamexPartnerTracing.repository;

import com.slabtech.lamexPartnerTracing.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartnerRepository extends JpaRepository<Partner, Integer> {

    @Query("SELECT COUNT(p) FROM Partner p")
    long countAllPartners();

    @Query("SELECT COUNT(p) FROM Partner p WHERE p.enabled = false")
    long countInactivePartners();
}
