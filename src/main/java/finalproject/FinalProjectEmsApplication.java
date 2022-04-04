package finalproject;

import finalproject.models.Employee;
import finalproject.models.Role;
import finalproject.repositories.EmployeeRepository;
import finalproject.repositories.RoleRepository;
import finalproject.services.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;
import java.util.List;

@EnableWebMvc
@SpringBootApplication
public class FinalProjectEmsApplication {

    /*
     * Main() method is entry point of the program.
     * JVM searches for main() method
     */
    public static void main(String[] args) {
        SpringApplication.run(FinalProjectEmsApplication.class, args);
    }

    /**
     * this populates the roles with basic roles for admins and users
     */
    @Bean
    public CommandLineRunner loadData(
            RoleRepository roleRepository,
            EmployeeRepository employeeRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            EmployeeService employeeService
    ) {
        return (args) -> {
            roleRepository.save(new Role("ROLE_USER"));
            roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_SUPER_ADMIN"));

            Employee e1 = new Employee(
                    "Rachel",
                    "Adams",
//                    LocalDate.of(1980, 1, 1),
                    "test@gmail.com",
                    bCryptPasswordEncoder.encode("test"),
                    roleRepository.findByName("ROLE_SUPER_ADMIN")
            );
            employeeService.addEmployee(e1);

            // a list of teachers for saving!
            //List.of(t1, t2, t3).forEach(teacher -> teacherService.addRole(teacher));
            //employeeRepository.saveAll(List.of(t1, t2, t3));
        };
    }
}
