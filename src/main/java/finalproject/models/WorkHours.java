package finalproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@Entity
@Component
public class WorkHours {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration timeWorked;
    private Duration totalTimeWorked;
    @Transient
    private List<Duration> workedTimes = new ArrayList<>();
    private boolean currentlyWorking;

    @ManyToOne
    private Employee employee;

    public WorkHours(LocalDateTime startTime, LocalDateTime endTime, Duration timeWorked, Duration totalTimeWorked, List<Duration> workedTimes, boolean currentlyWorking, Employee employee) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeWorked = timeWorked;
        this.totalTimeWorked = totalTimeWorked;
        this.workedTimes = workedTimes;
        this.currentlyWorking = currentlyWorking;
        this.employee = employee;
    }

    public WorkHours(){}

}
