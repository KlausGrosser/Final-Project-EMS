package com.finalproject.model.repository;

import com.finalproject.model.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository that provide access to leave entity mapping in database
 */
@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
}
