package finalproject.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @SequenceGenerator(
            name = "role_sequence",
            sequenceName = "role_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "role_sequence",
            strategy = GenerationType.AUTO
    )
    private Long id;
    private String name;

    public Role(String name) {
        this.name = name;
    }


    @Override
    public String getAuthority() {
        return this.getName();
    }
}
