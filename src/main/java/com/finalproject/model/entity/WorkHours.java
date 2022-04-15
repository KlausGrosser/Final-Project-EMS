package com.finalproject.model.entity;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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
    private User employee;

}
