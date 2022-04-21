package com.finalproject.model.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Shift {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate shiftDay;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assignedDay;
    private LocalDateTime assignedStartTime;
    private LocalDateTime assignedEndTime;
    private LocalDateTime tempStartTime;
    private LocalDateTime tempEndTime;
    private Duration timeWorkedInLastPeriod;
    private Duration totalTimeWorked;
    @ElementCollection
    private List<Duration> workedPeriods = new ArrayList<>();
    @ElementCollection
    private List<LocalDateTime> checkIns = new ArrayList<>();
    @ElementCollection
    private List<LocalDateTime> checkOuts = new ArrayList<>();

    private boolean currentlyWorking;

    private boolean absent = true;

    private String statusMessage = "Please check in";

    @ManyToOne
    private User employee;

    public Shift(LocalDate assignedDay, LocalDateTime assignedStartTime, LocalDateTime assignedEndTime, User employee) {
        this.assignedDay = assignedDay;
        this.assignedStartTime = assignedStartTime;
        this.assignedEndTime = assignedEndTime;
        this.employee = employee;
    }
}
