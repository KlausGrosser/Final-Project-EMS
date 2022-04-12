package com.finalproject.model.repository;

import com.finalproject.model.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository that provide access to activity entity mapping in database
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
