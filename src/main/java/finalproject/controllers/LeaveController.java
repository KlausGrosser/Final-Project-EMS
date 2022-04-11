package finalproject.controllers;

import finalproject.currencyapp.Currency;
import finalproject.models.LeaveDetails;
import finalproject.models.LeaveStatus;
import finalproject.services.EmployeeService;
import finalproject.services.LeaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LeaveController {

    private final LeaveService leaveService;
    private final EmployeeService employeeService;


    public LeaveController(LeaveService leaveService, EmployeeService employeeService) {
        this.leaveService = leaveService;
        this.employeeService = employeeService;
    }

    //GetMappings
    @GetMapping(path = "employee/manage-leaves/{action}/{username}")
    public String acceptOrRejectLeave(@PathVariable("action") String action, @PathVariable("username") String username) {
        LeaveDetails leaveDetails = leaveService.getLeaveOfEmployee(username);
        if (action.equals("approved")) {
            leaveDetails.setLeaveStatus(LeaveStatus.APPROVED);
            leaveDetails.setActive(false);
        } else if (action.equals("rejected")) {
            leaveDetails.setLeaveStatus(LeaveStatus.REJECTED);
            leaveDetails.setActive(false);
        } else if (action.equals("pending")) {
            leaveDetails.setLeaveStatus(LeaveStatus.PENDING);
            leaveDetails.setActive(false);
        }
        leaveService.updateLeaveDetails(leaveDetails);

        return action;
    }


}


