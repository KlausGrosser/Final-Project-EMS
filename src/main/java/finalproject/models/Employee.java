package finalproject.models;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
//@EqualsAndHashCode   //Generates hashCode and equals implementations from the fields of your object.
// If applying @EqualsAndHashCode to a class that extends another, this feature gets a bit trickier.  https://projectlombok.org/features/EqualsAndHashCode

@Entity
@Table(name = "employees") // be default is the table name the class name, but with @table it`` possible to give another name
public class Employee extends User{

  @Id //@Id marks a field in a model class as the primary key:
  @SequenceGenerator(   //we declared a SequenceGenerator with emp_SEQ. It points to emp_SEQ which is a database object.
                        // The SequenceGenerator name “emp_SEQ” will be used by @GeneratedValue annotation.
  name = "emp_SEQ",
  sequenceName = "emp_SEQ",
  allocationSize = 1
  )

  //@GeneratedValue(strategy=SEQUENCE, generator="emp_SEQ")
  @GeneratedValue(strategy = GenerationType.IDENTITY)//The GeneratedValue annotation may be applied to a primary key property of field of an entity or mapped superclass  in a conjunction with the Id annotation.
  private long Id;

  @Column(name = "first_name")
  private String fName;

  @Column(name = "last_name")
  private String lName;

  private LocalDate birthDate;
  private LocalDate hireDate;
  private int iBAN;
  private int ssn; //better variablename for ssn...?
  private int taxId;
  private int holidays;
  private float salary;
  @Enumerated(EnumType.STRING)
  private JobLevel jobLevel;
  @Enumerated(EnumType.STRING)
  private Office office;
  public Department department;
  public Address address;
  public Manager manager;
  //public Position position;


 /* //many Employees can be in one department
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false,
  name ="id"
  )*/

  //Constructor without ID because it's generated automatically
  public Employee(String fName, String lName, LocalDate birthDate) {
    this.fName = fName;
    this.lName = lName;
    this.birthDate = birthDate;
  }


  public Employee(String fName, String lName, LocalDate birthDate, LocalDate hireDate, int iBAN, int ssn, int taxId, int holidays, float salary, JobLevel jobLevel, Office office) {
    this.fName = fName;
    this.lName = lName;
    this.birthDate = birthDate;
    this.hireDate = hireDate;
    this.iBAN = iBAN;
    this.ssn = ssn;
    this.taxId = taxId;
    this.holidays = holidays;
    this.salary = salary;
    this.jobLevel = jobLevel;
    this.office = office;
  }


  //Class SimpleGrantedAuthority stores a String representation of an authority granted to the Authentication object.
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority =
            new SimpleGrantedAuthority(jobLevel.name());
    return Collections.singletonList(authority);
  }
  }









