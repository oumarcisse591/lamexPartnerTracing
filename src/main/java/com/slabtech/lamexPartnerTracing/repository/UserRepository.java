package com.slabtech.lamexPartnerTracing.repository;

import com.slabtech.lamexPartnerTracing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String username);
}
