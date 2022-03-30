package finalproject.controllers;

import finalproject.models.Employee;
import finalproject.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value ="api/v1/employees")
//@RequiredArgsConstructor
public class EmployeeController {

  private final EmployeeService employeeService;


  @Autowired
 public EmployeeController(EmployeeService employeeService){
    this.EmployeeService = employeeService;
  }

  //display the list of employee
  @GetMapping("/employees")
  public List<Employee> getEmployees(){ return employeeService.getAllEmployees();
  }

  @GetMapping("/{id}")
    public ResponseEntity<Optional<Employee>> getSingleEmployee(@PathVariable Long id){
    Optional<Employee> foundEmployee = EmployeeService.getSingleEmployee( id);
        if (foundEmployee.isEmpty()) {
           throw new ChangeSetPersister.NotFoundException(String.format("Employee with id %s not found", id));
        } else{
         return ResponseEntity.ok(foundEmployee);
         }
    }

 /* @ExceptionHandler(value= NotFoundException.class)
  @ResponseStatus(code= HttpStatus.NOT_FOUND)
  public ErrorDto handleNotFoundException(NotFoundException ex){
    return new ErrorDto(ex.getMessage());
  }
*/


}
