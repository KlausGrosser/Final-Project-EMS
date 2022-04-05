package finalproject.controllers;

import finalproject.models.Employee;
import finalproject.repositories.RoleRepository;
import finalproject.services.DepartmentService;
import finalproject.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


/**
 * The Controller class is connected to the Service layer.
 * RestController annotation allows the class to handle the requests made by the client.
 */
@RestController
@RequestMapping(value ="/employees")
@RequiredArgsConstructor
public class EmployeeController {

    //Lombok will generate the constructor for the employeeService field declared as final.
    //Spring will automatically use the Lombok provided constructor to autowire the class.
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final RoleRepository roleRepository;;

    /**
     * Getting the employee by its fullName (FirstName & LastName).
     * @param fullName The name of the employee to be found.
     * @return ResponseEntity = represents the HTTP response with the given status code
     */
    @GetMapping("/{fullName}")
    public ResponseEntity<Employee> findByFullName(@PathVariable String fullName){
      Employee employee = employeeService.findByFullName(fullName);
      if(employee == null) {
        return ResponseEntity.notFound().build();
      }else{
        return ResponseEntity.ok(employee);
      }
    }

    /**
     * Getting all employees.
     * @return ResponseEntity = represents the HTTP response with the given status code.
     */
    @GetMapping
    public ResponseEntity <List<Employee>> getAllEmployees(){
      List<Employee> employees = employeeService.getAllEmployees();
      if(employees == null){
        return ResponseEntity.notFound().build();
      }else{
        return ResponseEntity.ok(employees);
      }
    }

    /**
     * Getting the employee by its id.
     * @param id The id of the employee to be found.
     * @return ResponseEntity = represents the HTTP response with the given status code
     */
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable Long id){

      Optional<Employee> foundEmployee = employeeService.getEmployeeById(id);

      if(foundEmployee.isEmpty()){
        return ResponseEntity.notFound().build();
      } else {
        return ResponseEntity.ok(foundEmployee);
      }
    }

    /**
     * Getting the employee by its email.
     * @param email The email of the employee to be found.
     * @return ResponseEntity = represents the HTTP response with the given status code
     */
    @GetMapping("/{email}")
    public ResponseEntity<Optional<Employee>> findByEmail(@PathVariable String email){

        Optional<Employee> foundEmployee = employeeService.findByEmail(email);

        if(foundEmployee.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundEmployee);
        }
    }

    /**
     * Updating a new employee
     * The RequestBody binds the HTTPRequest body to the domain object
     * The PathVariable indicates that the "id" should be bound to a URI template variable.
     * @return ResponseEntity = represents the HTTP response with the given status code.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable ("id") Long id, @RequestBody Employee employee){
      Employee updatedEmployee = employeeService.updateEmployee(id, employee);
      if (updatedEmployee == null) {
        return ResponseEntity.notFound().build();
      } else {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedEmployee.getId())
                .toUri();
        return ResponseEntity.created(uri).body(updatedEmployee);
      }
    }

    @PutMapping(path = "/update/{id}")
    public String updateForm(@PathVariable ( value = "id") Long id, Model model){
        Employee employee = employeeService.getEmployeeById(id).orElseThrow();
        model.addAttribute("listDepartments", departmentService.getAllDepartments());
        model.addAttribute("listDoctorRoles", roleRepository.findAll());
        model.addAttribute("employee", employee);
        return "update_employee";
    }

    @DeleteMapping(path = "/delete/{id}")
    public String deleteEmployee(@PathVariable ( value = "id") Long id){
        employeeService.deleteEmployeeById(id);
        return "redirect:/employee_list";
    }

    @PostMapping(path = "/new")
    public String addEmployee(@ModelAttribute Employee employee){
        employeeService.updateEmployee(employee.getId(), employee);
        return "redirect:/employee_list";
    }


}







