package finalproject.controllers;

import finalproject.models.Employee;
import finalproject.models.WorkHours;
import finalproject.services.EmployeeService;
import finalproject.services.WorkHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WorkHoursController {

    private final WorkHoursService workHoursService;
    private final EmployeeService employeeService;

    @Autowired
    public WorkHoursController(WorkHoursService workHoursService, EmployeeService employeeService) {
        this.workHoursService = workHoursService;
        this.employeeService = employeeService;
    }

    //GetMappings
    @GetMapping(path = "/getTime")
    public String getTimeBetweenStartAndEnd() {
        return workHoursService.getTimeBetweenStartAndEnd();
    }


    //PostMappings
    @PostMapping(path = "/check_in")
    public String startWorking() {
        return workHoursService.start();
    }

    @PostMapping(path = "/check_out")
    public String stopWorking() {
        return workHoursService.stop();
    }

}
