package finalproject.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Set;

/**
 * Department class in an Entity that can be persisted to the database
 * Lombok's annotations were used
 */
@Getter
@Setter
@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @SequenceGenerator(
            name = "department_sequence",
            sequenceName = "department_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            //GenerationType.SEQUENCE generates a unique ID value using a sequence
            strategy = GenerationType.SEQUENCE,
            generator = "department_sequence"
    )
    private Long id;
    //Name refers to the department name
    private String name;
    //One department can have many Employees
    @OneToMany
    @JoinColumn (name = "employee_id")
    @JsonManagedReference
    private Set<Employee> employees;

    //Constructor = only with the department name
    public Department(String name) {
        this.name = name;
    }
}
