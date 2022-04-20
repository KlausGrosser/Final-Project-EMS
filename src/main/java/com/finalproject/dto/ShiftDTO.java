package com.finalproject.dto;

import com.finalproject.model.entity.User;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ShiftDTO {

    private LocalDate assignedDay;
    private LocalDateTime assignedStartTime;
    private LocalDateTime assignedEndTime;
    private String assignedEmployeeName;
}
