package finalproject.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class LeaveDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private Date fromDate;

    private Date toDate;

    private int duration;

    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus;

    @Enumerated(EnumType.STRING)
    private LeaveReasons leaveReasons;

    @ElementCollection
    private List<String> leavesRequested = new ArrayList<>();

    @ManyToOne
    @JoinColumn
    private Employee employee;

    public LeaveDetails(String username, Date fromDate, Date toDate, int duration, LeaveStatus leaveStatus, LeaveReasons leaveReasons, List<String> leavesRequested, Employee employee) {
        this.username = username;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.duration = duration;
        this.leaveStatus = leaveStatus;
        this.leaveReasons = leaveReasons;
        this.leavesRequested = leavesRequested;
        this.employee = employee;
    }
}
