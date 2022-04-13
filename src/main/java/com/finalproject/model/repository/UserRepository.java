package com.finalproject.model.repository;

import com.finalproject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * JPA repository that provides access to user entity mapping in database
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query(
            "UPDATE User a "+
                    "SET a.enabled = TRUE "+
                    "WHERE a.username = ?1"
    )
    int enableUser(String email);

}