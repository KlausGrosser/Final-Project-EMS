package finalproject.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Set;

/**
 * Position class in an Entity which means it can be mapped to a table.
 * Lombok's annotations were used
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String positionName;
    @OneToMany
    @JoinColumn (name = "employee_id")
    @JsonManagedReference
    private Set<Employee> employees;

    public Position(String positionName) {
        this.positionName = positionName;
    }
}
