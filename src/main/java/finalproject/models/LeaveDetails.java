package finalproject.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class LeaveDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String employeeName;

    @NotNull(message = "Please provide start date!")
    private LocalDateTime fromDate;

    @NotNull(message = "Please provide end date!")
    private LocalDateTime toDate;

    @NotEmpty(message = "Please select type of leave!")
    private String leaveType;

    @NotEmpty(message = "Please provide a reason for the leave!")
    private String reason;

    private Duration duration;

    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus;

    @Enumerated(EnumType.STRING)
    private LeaveReasons leaveReasons;

    private boolean active;

}
