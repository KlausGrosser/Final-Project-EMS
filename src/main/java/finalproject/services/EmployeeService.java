package finalproject.services;

import finalproject.models.Employee;
import finalproject.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService{

  private final EmployeeRepository employeeRepository;

  //with database
  //create methods for get all, save, delete //see class employeeController
  public List<Employee> getAllEmployees()  {
    return employeeRepository.findAll();
  }

  public Optional<Employee> getSingleEmployee (Long id) {
    return employeeRepository.findById(id);
  }


  public Optional<Employee> findByEmail (String email) {
    return employeeRepository.findByEmail(email);
  }

  public void findBylName (String lName){
    employeeRepository.findBylName(lName)
            .orElseThrow(() -> new UsernameNotFoundException("Employee not found"));
  }

  public Employee addEmployee(Employee newEmployee){
    employeeRepository.save(newEmployee);
    return newEmployee;
  }

  public Employee updateEmployee(Long id, Employee newLNameEmployee){
    Employee employee = employeeRepository.findById(id).orElseThrow();
    employee.setLName(newLNameEmployee.getLName());
    employeeRepository.save(employee);
    return employee;
  }

  public void deleteEmployeeById (long id){
    employeeRepository.deleteById(id);

  }


}
