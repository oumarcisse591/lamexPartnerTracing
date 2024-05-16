package com.slabtech.lamexPartnerTracing.dao;

import com.slabtech.lamexPartnerTracing.entity.Society;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SocietyDaoImpl implements SocietyDao{

    private EntityManager entityManager;

    @Autowired
    public SocietyDaoImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public List<Society> findAllSociety() {
        TypedQuery<Society> theQuery = entityManager.createQuery("FROM Society", Society.class);
        return theQuery.getResultList();
    }

    @Override
    public Society findBySocietyName(String userName) {
        return null;
    }

    @Override
    public Society findSocietyById(int theId) {
        Society theSociety = entityManager.find(Society.class, theId);
        return theSociety;
    }

    @Transactional
    @Override
    public void deleteSocietyById(int theId) {
        Society theSociety = entityManager.find(Society.class, theId);
        entityManager.remove(theSociety);
    }

    @Transactional
    @Override
    public Society saveSociety(Society theSociety) {
        Society dbSociety = entityManager.merge(theSociety);
        return dbSociety;
    }
}

