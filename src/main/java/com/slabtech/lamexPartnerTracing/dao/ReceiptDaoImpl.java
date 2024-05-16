package com.slabtech.lamexPartnerTracing.dao;

import com.slabtech.lamexPartnerTracing.entity.Receipt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ReceiptDaoImpl implements ReceiptDao {

    private EntityManager entityManager;

    @Autowired
    public ReceiptDaoImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public List<Receipt> findAllReceipt() {
        TypedQuery<Receipt> theQuery = entityManager.createQuery("SELECT c FROM Receipt c WHERE c.enabled = true ORDER BY c.id DESC", Receipt.class);
        return theQuery.getResultList();
    }

    @Override
    public List<Receipt> findLastsReceipts() {
        TypedQuery<Receipt> theQuery = entityManager.createQuery(
                "SELECT c FROM Receipt c WHERE c.enabled = true ORDER BY c.id DESC",
                Receipt.class
        );
        theQuery.setMaxResults(5);
        return theQuery.getResultList();
    }

    @Override
    public Receipt findFirstByOrderByUdDesc() {
        return entityManager.createQuery("SELECT c FROM Receipt c ORDER BY c.id DESC", Receipt.class)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public Receipt findByReceiptName(String userName) {
        return null;
    }

    @Override
    public Long getCount() {
        Query theQuery = entityManager.createQuery("SELECT COUNT(*) FROM Receipt c WHERE c.enabled = true ", Receipt.class);
        return (Long) theQuery.getSingleResult();
    }

    @Override
    public Receipt findReceiptById(int theId) {
        Receipt theReceipt = entityManager.find(Receipt.class, theId);
        return theReceipt;
    }

    @Override
    public void deleteReceiptById(int theId) {

    }

    @Transactional
    @Override
    public Receipt saveReceipt(Receipt theReceipt) {
        Receipt dbReceipt = entityManager.merge(theReceipt);
        return dbReceipt;
    }
}

