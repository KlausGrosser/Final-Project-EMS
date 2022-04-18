package com.finalproject.dto;

import com.finalproject.model.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ShiftDTO {

    LocalDate shiftDay;
    LocalDateTime assignedStartTime;
    LocalDateTime assignedEndTime;
    User assignedEmployee;
}
