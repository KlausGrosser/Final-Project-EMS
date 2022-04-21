
package com.finalproject.model.repository;

import com.finalproject.model.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByName(String name);

    @Query(value="SELECT e FROM Company e WHERE e.name LIKE %:keyword% " +
            "OR e.ceo LIKE %:keyword% " +
            "OR e.address LIKE %:keyword%")
    Page<Company> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
