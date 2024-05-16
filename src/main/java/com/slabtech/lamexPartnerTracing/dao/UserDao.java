package com.slabtech.lamexPartnerTracing.dao;

import com.slabtech.lamexPartnerTracing.entity.User;

import java.util.List;

public interface UserDao {

    List<User> findAllUser();
    User findByUserName(String userName);

    User findUserById(int theId);

    Long getUserCount();

    void deleteUserById(int theId);

    User saveUser(User theUser);

}
