package finalproject.controllers;

import finalproject.services.WorkHoursService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class WorkHoursController {

    private final WorkHoursService workHoursService;

    public WorkHoursController(WorkHoursService workHoursService) {
        this.workHoursService = workHoursService;
    }

    @PostMapping(path = "/checkInOut")
    public String getHoursWorked(){
       // workHoursService.getHoursWorked(action);
        return "hours_worked";
    }
}
