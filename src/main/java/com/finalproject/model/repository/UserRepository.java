package com.finalproject.model.repository;

import com.finalproject.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository that provides access to user entity mapping in database
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByFullName(String fullName);


    @Transactional
    @Modifying
    @Query(
            "UPDATE User a " +
                    "SET a.enabled = TRUE " +
                    "WHERE a.username = ?1"
    )

    int enableUser(String email);


    @Query(value="SELECT e FROM User e WHERE e.firstName LIKE %:keyword% " +
            "OR e.lastName LIKE %:keyword% OR e.username LIKE %:keyword% " +
            "OR e.department LIKE %:keyword%")
    Page<User> findByKeyword(@Param("keyword") String keyword, Pageable pageable);


}


