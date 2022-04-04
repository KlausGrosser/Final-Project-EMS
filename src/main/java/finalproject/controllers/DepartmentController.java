package finalproject.controllers;

import finalproject.models.Department;
import finalproject.services.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

/**
 * The Controller class is connected to the Service layer.
 * RestController annotation allows the class to handle the requests made by the client.
 */
@RestController
@RequestMapping ("/api/v1/departments")
@AllArgsConstructor
public class DepartmentController {

    //Lombok will generate the constructor for the departmentService field declared as final.
    //Spring will automatically use the Lombok provided constructor to autowire the class.
    private final DepartmentService departmentService;

    /**
     * Getting the department by its name.
     * @param name The name of the department to be found.
     * @return ResponseEntity = represents the HTTP response with the given status code
     */
    @GetMapping("/{departmentName}")
    public ResponseEntity<Department> findByName(@PathVariable ("departmentName") String name){
        Department department = departmentService.findByName(name);
        if(department == null) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(department);
        }
    }

    /**
     * Getting all departments.
     * @return ResponseEntity = represents the HTTP response with the given status code.
     */
    @GetMapping()
    public ResponseEntity<List<Department>> getAllDepartments(){
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    /**
     * Adding a new department
     * The RequestBody binds the HTTPRequest body to the domain object
     * @return ResponseEntity = represents the HTTP response with the given status code.
     */
    @PostMapping
    public ResponseEntity<Department> addDepartment(@RequestBody Department department){

        Department newDepartment = departmentService.addDepartment(department);
        if (newDepartment == null) {
            return ResponseEntity.notFound().build();
        } else {

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newDepartment.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(newDepartment);
        }
    }

    /**
     * Updating a new department
     * The RequestBody binds the HTTPRequest body to the domain object
     * The PathVariable indicates that the "id" should be bound to a URI template variable.
     * @return ResponseEntity = represents the HTTP response with the given status code.
     */
    @PutMapping
    public ResponseEntity<Department> updateDepartment(@RequestBody Department department , @PathVariable("id") Long id){
        Department updatedDepartment = departmentService.updateDepartment(id, department);
        if (updatedDepartment == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(updatedDepartment.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(updatedDepartment);
        }
    }

    /**
     * Deleting a department by its {id}.
     * @return ResponseEntity = represents the HTTP response with the given status code.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable("id") Long id) {
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.noContent().build();
    }
}
