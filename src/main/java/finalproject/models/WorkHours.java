package finalproject.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WorkHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;
    private Time startTime;
    private Time endTime;
    private Time totalTime;

    @ManyToOne
    private Employee employee;

    public WorkHours(LocalDate date, Time startTime, Employee employee) {
        this.date = date;
        this.startTime = startTime;
        this.employee = employee;
    }
}