package com.slabtech.lamexPartnerTracing.repository;

import com.slabtech.lamexPartnerTracing.entity.Partner;
import com.slabtech.lamexPartnerTracing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUserName(String username);
    boolean existsByUserName(String userName);
    @Query("SELECT COUNT(u) FROM User u WHERE u.partner = :partner")
    long countUsersByPartner(@Param("partner") Partner partner);
}
