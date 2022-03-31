package finalproject.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Email;
import static javax.persistence.FetchType.EAGER;

/**
 * AppUser class in an Entity which means it can be mapped to a table.
 * Is an abstract class which means it can be subclassed
 * Lombok's annotations were used
 */
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AppUser implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "user_sequence",
            strategy = GenerationType.AUTO
    )
    private Long id;
    //Email must be unique
    @Column(unique = true)
    @Email
    private String email;
    private String password;
    @ManyToOne(fetch = EAGER)
    private Role role;
    private Boolean enabled = false;
    private Boolean locked = false;

}
