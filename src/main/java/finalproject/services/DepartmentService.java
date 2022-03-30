package finalproject.services;

import finalproject.models.Department;
import finalproject.repositories.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public void findByName (String name){
        departmentRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Department not found"));

    }

    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public void addDepartment(Department newDepartment){
        departmentRepository.save(newDepartment);
    }

    public Department updateDepartment(Long id, Department newNameDepartment){
        Department department = departmentRepository.findById(id).orElseThrow();
        department.setName(newNameDepartment.getName());
        departmentRepository.save(department);
        return department;
    }

    public void deleteDepartmentById (long id){
        departmentRepository.deleteById(id);

    }



}
