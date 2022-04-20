package com.finalproject.model.service;

import com.finalproject.model.entity.Shift;
import com.finalproject.model.entity.User;
import com.finalproject.util.email.EmailValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class SupervisorService {

    private final UserService userService;
    private final EmailValidator emailValidator;
    private final ShiftService shiftService;

    public SupervisorService(UserService userService, EmailValidator emailValidator, ShiftService shiftService) {
        this.userService = userService;
        this.emailValidator = emailValidator;
        this.shiftService = shiftService;
    }

    public void assignShiftToEmployee(String employeeInfo, LocalDate shiftDate, LocalDateTime shiftStart, LocalDateTime shiftEnd){
        User employee = null;

        if(emailValidator.test(employeeInfo)){
            employee = userService.findByUsername(employeeInfo);
        }else{
            employee = userService.findByFullName(employeeInfo);
        }

        Shift newShift = new Shift(shiftDate, shiftStart, shiftEnd, employee);

        shiftService.saveShiftAndUpdateEmployee(employee, newShift);
    }


}
