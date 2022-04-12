package com.finalproject.model.repository;

import com.finalproject.model.entity.WorkHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkHoursRepository extends JpaRepository<WorkHours, Long> {


}
