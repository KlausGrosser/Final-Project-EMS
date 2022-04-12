package com.finalproject.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class WorkHours {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate currentDay;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration timeWorked;
    private Duration totalTimeWorked;
    @ElementCollection
    private List<Duration> workedTimes = new ArrayList<>();
    @ElementCollection
    private List<LocalDateTime> startTimes = new ArrayList<>();
    @ElementCollection
    private List<LocalDateTime> stopTimes = new ArrayList<>();

    private boolean currentlyWorking;

    @ManyToOne
    //@JoinColumn
    private User employee;

    public WorkHours(LocalDate currentDay, LocalDateTime startTime, LocalDateTime endTime, Duration timeWorked, Duration totalTimeWorked, List<Duration> workedTimes, List<LocalDateTime> startTimes, List<LocalDateTime> stopTimes, boolean currentlyWorking, User employee) {
        this.currentDay = currentDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeWorked = timeWorked;
        this.totalTimeWorked = totalTimeWorked;
        this.workedTimes = workedTimes;
        this.startTimes = startTimes;
        this.stopTimes = stopTimes;
        this.currentlyWorking = currentlyWorking;
        this.employee = employee;
    }

    public WorkHours(LocalDateTime startTime, LocalDateTime endTime, Duration timeWorked, Duration totalTimeWorked, List<Duration> workedTimes, boolean currentlyWorking, User employee) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeWorked = timeWorked;
        this.totalTimeWorked = totalTimeWorked;
        this.workedTimes = workedTimes;
        this.currentlyWorking = currentlyWorking;
        this.employee = employee;
    }

}
