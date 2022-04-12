package com.finalproject.model.repository;

import com.finalproject.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Repository layer is implemented to access the database
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    //The Optional is a container object which may or may not contain a non-null value.
    Optional<Department> findByName(String name);
}
