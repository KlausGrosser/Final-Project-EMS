package finalproject.controllers;

import finalproject.models.LeaveDetails;
import finalproject.models.LeaveStatus;
import finalproject.services.EmployeeService;
import finalproject.services.LeaveService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
public class LeaveController {

    private final LeaveService leaveService;
    private final EmployeeService employeeService;


    public LeaveController(LeaveService leaveService, EmployeeService employeeService) {
        this.leaveService = leaveService;
        this.employeeService = employeeService;
    }

    @PostMapping(path = "/new_leave")
    public LeaveDetails applyNewLeave(@PathVariable("startDate")) {
        return leaveService.applyLeave();
    }

    @PostMapping(path = "/date_leave")
    public void date(@RequestParam("date") Date date){

    }




}


