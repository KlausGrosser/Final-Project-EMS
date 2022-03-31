package finalproject.repositories;

import finalproject.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

//Repository layer is implemented to access the database
@Repository
public interface EmployeeRepository extends JpaRepository <Employee, Long> {

  Optional<Employee>findByFullName(String fullName);

  Optional<Employee>findByEmail(String email);

  Optional<Employee>findById(Long id);

  @Transactional
  @Modifying
  @Query(
          "UPDATE Employee a "+
                  "SET a.enabled = TRUE "+
                  "WHERE a.email = ?1"
  )
  int enableEmployee(String email);
}
