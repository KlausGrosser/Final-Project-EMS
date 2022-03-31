package finalproject.services;

import finalproject.models.Employee;
import finalproject.repositories.EmployeeRepository;
import finalproject.security.token.ConfirmationToken;
import finalproject.security.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeeService{

  private final EmployeeRepository employeeRepository;
  private final static String USER_NOT_FOUND_MSG =
          "user with email %s not found";
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final ConfirmationTokenService confirmationTokenService;

  public String newEmployee(Employee employee) {
    boolean userExists = employeeRepository
            .findByEmail(employee.getEmail())
            .isPresent();
    if (userExists) {
      throw new IllegalStateException("email is taken");
    }

    String encodedPassword = bCryptPasswordEncoder.encode(employee.getPassword());
    employee.setPassword(encodedPassword);
    employeeRepository.save(employee);


    String token =  UUID.randomUUID().toString();
    ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            employee
    );
    confirmationTokenService.saveConfirmationToken(confirmationToken);

    return token;
  }

  public void enableDoctor(String email) {
    employeeRepository.enableEmployee(email);
  }

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

  public void findByFullName (String fullName){
    employeeRepository.findByFullName(fullName)
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

  public int enableEmployee(String email) {
    return employeeRepository.enableEmployee(email);
  }


}
