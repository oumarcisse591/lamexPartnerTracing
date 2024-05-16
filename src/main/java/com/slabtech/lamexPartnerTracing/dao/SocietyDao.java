package com.slabtech.lamexPartnerTracing.dao;

import com.slabtech.lamexPartnerTracing.entity.Society;

import java.util.List;

public interface SocietyDao {

    List<Society> findAllSociety();
    Society findBySocietyName(String userName);

    Society findSocietyById(int theId);

    void deleteSocietyById(int theId);

    Society saveSociety(Society theSociety);
}

