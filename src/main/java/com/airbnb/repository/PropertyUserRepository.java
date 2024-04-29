package com.airbnb.repository;

import com.airbnb.entity.PropertyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyUserRepository extends JpaRepository<PropertyUser, Long> {

    Optional<PropertyUser> findByUserName(String userName);
    Optional<PropertyUser> findByEmail(String email);

}