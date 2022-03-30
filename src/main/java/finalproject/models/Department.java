package finalproject.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "department")
@NoArgsConstructor
public class Department {

    //The ID will be generated automatically
    @Id
    @SequenceGenerator(
            name = "department_sequence",
            sequenceName = "department_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "department_sequence"
    )
    private Long id;
    //Name refers to the department name
    private String name;
    //One department can have many Employees
    @OneToMany
    @JoinColumn (name = "employee_id")
    private Employee employee;

    //Constructor = only with the department name
    public Department(String name) {
        this.name = name;
    }
}
