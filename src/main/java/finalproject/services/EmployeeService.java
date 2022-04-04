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

/**
 * The Service layer facilitates communication between the controller and the persistence layer.
 * Business logic is stored here.
 */
@Service
@AllArgsConstructor
public class EmployeeService {

    //Lombok will generate the constructor for the employeeRepository field declared as final.
    //Spring will automatically use the Lombok provided constructor to autowire the class.
    private final EmployeeRepository employeeRepository;

    //Fields
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    //Method for signing up a new employee
    public String signUp(Employee employee) {
      //Check if the email already exists or not in the Repository
      boolean userExists = employeeRepository
              .findByEmail(employee.getEmail())
              .isPresent();
      if (userExists) {
        throw new IllegalStateException("email is taken");
      }

      //if the email doesn't exist it will encode the password
      String encodedPassword = bCryptPasswordEncoder.encode(employee.getPassword());
      employee.setPassword(encodedPassword);
      employeeRepository.save(employee);

      //Generating a random token
      String token = UUID.randomUUID().toString();
      ConfirmationToken confirmationToken = new ConfirmationToken(
              token,
              LocalDateTime.now(),
              LocalDateTime.now().plusMinutes(15),
              employee
      );
      confirmationTokenService.saveConfirmationToken(confirmationToken);
      return token;
    }

    /**
     * This method will try to find the employee by its fullName (FirstName & LastName) in the DepartmentRepository
     *
     * @param fullName The name of the employee to be found.
     * @return the employee name if its found.
     * @throws UsernameNotFoundException if the employee is not found.
     */
    public Employee findByFullName(String fullName) {
      return employeeRepository.findByFullName(fullName)
              .orElseThrow(() -> new UsernameNotFoundException("Employee not found"));
    }

    /**
     * This method will get all the employees
     * @return a List of all the employees
     */
    public List<Employee> getAllEmployees() {
      return employeeRepository.findAll();
    }

    /**
     * This method will get the employee by its id
     * @return the employee name if it's found by the id.
     */
    public Optional<Employee> getEmployeeById(Long id) {
      return employeeRepository.findById(id);
    }


    /**
     * This method will get the employee by its email
     * @return the employee name if it's found by the email.
     */
    public Optional<Employee> findByEmail(String email) {
      return employeeRepository.findByEmail(email);
    }

    /**
     * This method will add a new employee and save it into the employeeRepository
     * @return a new employee added.
     */
    public Employee addEmployee(Employee newEmployee) {
      employeeRepository.save(newEmployee);
      return newEmployee;
    }

    /**
     * This method will update an existing employee
     * @return an updated name of an employee
     */
    public Employee updateEmployee(Long id, Employee newLNameEmployee) {
      Employee employee = employeeRepository.findById(id).orElseThrow();
      employee.setLName(newLNameEmployee.getLName());
      return employeeRepository.save(employee);
    }

    /**
     * This method will delete an existing employee by its id
     */
    public void deleteEmployeeById(long id) {
      employeeRepository.deleteById(id);
    }

  /**
   * This method will enable an employee
   */
    public int enableEmployee(String email) {
      return employeeRepository.enableEmployee(email);
    }
  }



