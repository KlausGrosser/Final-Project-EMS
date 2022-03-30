package finalproject.controllers;

import finalproject.models.Employee;
import finalproject.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.ResourceUtils.toURI;

@RestController
@RequestMapping(value ="/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeService employeeService;


  /*@Autowired
 public EmployeeController(EmployeeService employeeService){
    this.EmployeeService = employeeService;
  }*/

  //Get the list of all employees
  @GetMapping
  public ResponseEntity <List<Employee>> getAllEmployees(){
    List<Employee> employees = employeeService.getAllEmployees();
    if(employees == null){
      return ResponseEntity.notFound().build();
    }else{
      return ResponseEntity.ok(employees);
    }
  }


  //Get employee by lName
  @GetMapping("/{lName}")
  public ResponseEntity<Employee> findBylName(String lName){
    employeeService.findBylName(lName);
  }


  //get employee by id
  @GetMapping("/{id}")
   public ResponseEntity<Optional<Employee>> getSingleEmployee(@PathVariable Long id){

    Optional<Employee> foundEmployee = employeeService.getSingleEmployee(id);

    if(foundEmployee.isEmpty()){
      return ResponseEntity.notFound().build();
    } else {
      return ResponseEntity.ok(foundEmployee);
    }

  }

 /* @ExceptionHandler(value= NotFoundException.class)
  @ResponseStatus(code= HttpStatus.NOT_FOUND)
  public ErrorDto handleNotFoundException(NotFoundException ex){
    return new ErrorDto(ex.getMessage());
  }
*/

  //Add new employee
  @PostMapping
    public void addEmployee(@ModelAttribute Employee employee){
    employeeService.addEmployee(employee);
  }
/*  public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) throws URISyntaxException {

    Employee employeeAdded = employeeService.addEmployee(employee);

    if ( employeeAdded == null) {
      return ResponseEntity.notFound().build();
    } else {
      URI uri = ServletUriComponentsBuilder
              .fromCurrentRequest()
              .path("/{id}")
              .buildAndExpand(employeeAdded.getId());
              .toUri();
      return ResponseEntity.created(uri).body(employeeAdded);
    }
  }*/


  //Update employee
  @PutMapping("/{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable ("id") Long id, @RequestBody Employee updateEmployee){

    Employee employeeUpdated = employeeService.updateEmployee(id,updateEmployee);

    if(employeeUpdated == null){
      return ResponseEntity.notFound().build();
    } else{
      return ResponseEntity.ok(employeeUpdated);
    }
  }


   /* public void updateEmployee(@RequestBody Employee employee , @PathVariable("id") Long id){
    employeeService.updateEmployee(id, employee);
  }*/


  //Delete employee
  @DeleteMapping("/{id}")
  public ResponseEntity<Employee> deleteEmployee(@PathVariable Long id){
    employeeService.deleteEmployeeById(id);
    return ResponseEntity.noContent().build();

  }

}
