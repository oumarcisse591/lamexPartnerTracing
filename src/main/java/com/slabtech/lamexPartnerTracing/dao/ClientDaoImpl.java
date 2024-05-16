package com.slabtech.lamexPartnerTracing.dao;

import com.slabtech.lamexPartnerTracing.entity.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ClientDaoImpl implements ClientDao{

    private EntityManager entityManager;

    @Autowired
    public ClientDaoImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }
    @Override
    public List<Client> findAllClient() {
        TypedQuery<Client> theQuery = entityManager.createQuery("SELECT c FROM Client c WHERE c.enabled = true ORDER BY c.id DESC", Client.class);
        return theQuery.getResultList();
    }

    @Override
    public Client findFirstByOrderByUdDesc() {
        Client theClient = entityManager.createQuery("SELECT c FROM Client c ORDER BY c.id DESC", Client.class)
                .setMaxResults(1)
                .getSingleResult();

        return theClient;
    }

    @Override
    public Client findByClientName(String userName) {
        return null;
    }

    @Override
    public Long getClientCount() {
        Query theQuery = entityManager.createQuery("SELECT COUNT(*) FROM Client c WHERE c.enabled = true", Client.class);
        return (Long) theQuery.getSingleResult();
    }

    @Override
    public Client findClientById(int theId) {
        Client theClient = entityManager.find(Client.class, theId);
        return theClient;
    }

    @Transactional
    @Override
    public void deleteClientById(int theId) {
        Client theClient = entityManager.find(Client.class, theId);
        entityManager.remove(theClient);
    }

    @Transactional
    @Override
    public Client saveClient(Client theClient) {
        Client dbClient = entityManager.merge(theClient);
        return dbClient;
    }
}

