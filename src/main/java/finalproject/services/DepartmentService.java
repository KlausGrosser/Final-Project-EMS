package finalproject.services;

import finalproject.models.Department;
import finalproject.repositories.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * The Service layer facilitates communication between the controller and the persistence layer.
 * Business logic is stored here.
 */
@Service
@AllArgsConstructor
public class DepartmentService {

    //Lombok will generate the constructor for the departmentRepository field declared as final.
    //Spring will automatically use the Lombok provided constructor to autowire the class.
    private final DepartmentRepository departmentRepository;

    /**
     * This method will try to find the department by its name in the DepartmentRepository
     * @param name The name of the department to be found.
     * @exception UsernameNotFoundException if the department is not found.
     * @return the department name if its found.
     */
    public Department findByName (String name){
       return departmentRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Department not found"));
    }

    /**
     * This method will get all the departments
     * @return a List of all the departments
     */
    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    /**
     * This method will add a new department and save it into the departmentRepository
     * @return a new department added.
     */
    public Department addDepartment(Department newDepartment){
        return departmentRepository.save(newDepartment);
    }

    /**
     * This method will update an existing department
     * @return an updated name of a department
     */
    public Department updateDepartment(Long id, Department newNameDepartment){
        Department department = departmentRepository.findById(id).orElseThrow();
        department.setName(newNameDepartment.getName());
        return departmentRepository.save(department);
    }

    /**
     * This method will delete an existing department by its id
     */
    public void deleteDepartmentById (long id){
        departmentRepository.deleteById(id);
    }

}
