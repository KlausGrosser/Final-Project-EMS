package finalproject.services;

import finalproject.models.Employee;
import finalproject.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService{

  private final EmployeeRepository employeeRepository;

  //add a constructor
  @Autowired
  public EmployeeService(EmployeeRepository employeeRepository) {this.employeeRepository = employeeRepository;
  }


  //with database
  //create methods for get all, save, delete //see class employeeController
  public List<Employee> getAllEmployees(){
    return employeeRepository.findAll();
  }


  public Optional<Employee> getSingleEmployee (Long id) {
    return employeeRepository.findById(id);
  }

  @Override
  public UserDetails loadEmployeeByUsername(String email) throws UsernameNotFoundException {
    return employeeRepository.findByEmail(email)
            .orElseThrow(
                    () ->
                            new UsernameNotFoundException(
                                    String.format(EMPLOYEE_NOT_FOUND_MSG,email)
                            )
            );
  }

}
