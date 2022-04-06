package finalproject.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Time;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class WorkHours {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    private Time startTime;
    private Time endTime;

    @ManyToOne
    private Employee employee;


    public WorkHours(LocalDate date, Time startTime, Employee employee) {
        this.date = date;
        this.startTime = startTime;
        this.employee = employee;
    }

}
