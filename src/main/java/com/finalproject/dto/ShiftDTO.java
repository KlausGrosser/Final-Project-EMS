package com.finalproject.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ShiftDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assignedDay;
    private LocalDateTime assignedStartTime;
    private LocalDateTime assignedEndTime;
    private String assignedEmployeeName;
}
