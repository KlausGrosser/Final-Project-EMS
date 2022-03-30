package finalproject.controllers;

import finalproject.models.Department;
import finalproject.services.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController ("/departments")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    //Get department by Name
    @GetMapping
    public ResponseEntity<Department> findByName(String name){
        departmentService.findByName(name);
    }

    //Get all departments
    @GetMapping()
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    //Add new department
    @PostMapping
    public void addDepartment(@ModelAttribute Department department){
        departmentService.addDepartment(department);
    }

    //Update department
    @PutMapping
    public void updateDepartment(@RequestBody Department department , @PathVariable("id") Long id){
        departmentService.updateDepartment(id, department);
    }

    //Delete department
    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable("id") Long id) {
        departmentService.deleteDepartmentById(id);
    }

}
