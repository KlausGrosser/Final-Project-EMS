package com.finalproject.dto;

import com.finalproject.model.entity.User;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ShiftDTO {

    private boolean checkIn;
    private boolean checkOut;
    private boolean currentTimeWorked;
    private boolean totalTimeWorked;
}
