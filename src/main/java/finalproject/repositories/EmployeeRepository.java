package finalproject.repositories;

import finalproject.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository <Employee, Long> {

  Optional<Employee>findBylName(String lName);

  Optional<Employee>findByEmail(String email);

  Optional<Employee>findById(Long id);
}
