package finalproject.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;


/**
 * Employee class in an Entity that can be persisted to the database
 * Lombok's annotations were used
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employees") // by default is the table name the class name, but with @table it`s possible to give another name
public class Employee extends AppUser {

  @Column(name = "first_name")
  private String fName;
  @Column(name = "last_name")
  private String lName;
  private String fullName = fName+" "+lName;
  private LocalDate birthDate;
  private LocalDate hireDate;
  private int iBAN;
  private int socialSecurityNum;
  private int taxId;
  private int holidays;
  private float salary;
  @Enumerated(EnumType.STRING)
  private JobLevel jobLevel;
  @Enumerated(EnumType.STRING)
  private Office office;
  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "department_id")
  private Department department;
  @OneToOne
  @JoinColumn(name = "address_id")
  private Address address;
  private String managerName;
  @ManyToOne
  @JsonBackReference
  @JoinColumn(name = "position_id")
  private Position position;

  //Constructor without ID because it's generated automatically
  public Employee(String fName, String lName, LocalDate birthDate) {
    super();
    this.fName = fName;
    this.lName = lName;
    this.birthDate = birthDate;
  }

  public Employee(
          String fName,
          String lName,
          LocalDate birthDate,
          LocalDate hireDate,
          int iBAN,
          int socialSecurityNum,
          int taxId,
          int holidays,
          float salary,
          JobLevel jobLevel,
          Office office) {
    this.fName = fName;
    this.lName = lName;
    this.birthDate = birthDate;
    this.hireDate = hireDate;
    this.iBAN = iBAN;
    this.socialSecurityNum = socialSecurityNum;
    this.taxId = taxId;
    this.holidays = holidays;
    this.salary = salary;
    this.jobLevel = jobLevel;
    this.office = office;
  }

  public Employee(String fName, String lName, String email, String password, Role role) {
    this.fName = fName;
    this.lName = lName;
    super.setEmail(email);
    super.setPassword(password);
    super.setRole(role);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority =
            new SimpleGrantedAuthority(this.getRole().getName());
    return Collections.singletonList(authority);
    }

  @Override
  public String getUsername() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}









