package com.slabtech.lamexPartnerTracing.dao;

import com.slabtech.lamexPartnerTracing.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    private EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }
    @Override
    public List<User> findAllUser() {
        TypedQuery<User> theQuery = entityManager.createQuery("FROM User", User.class);
        return theQuery.getResultList();
    }

    @Override
    public User findByUserName(String userName) {
        TypedQuery<User> theQuery = entityManager.createQuery("from User where userName=:uName", User.class);
        theQuery.setParameter("uName", userName);

        User theUser = null;
        try{
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }
        return theUser;
    }

    @Override
    public User findUserById(int theId) {
        User theUser = entityManager.find(User.class, theId);
        return theUser;
    }

    @Override
    public Long getUserCount() {
        Query theQuery = entityManager.createQuery("SELECT COUNT(*) FROM User", User.class);
        return (Long) theQuery.getSingleResult();
    }

    @Transactional
    @Override
    public void deleteUserById(int theId) {
        User theUser = entityManager.find(User.class, theId);
        entityManager.remove(theUser);
    }

    @Transactional
    @Override
    public User saveUser(User theUser) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(theUser.getPassword());

        theUser.setPassword(hashedPassword);
        theUser.setEnabled(true);

        User bdUser = entityManager.merge(theUser);
        return bdUser;
    }
}

