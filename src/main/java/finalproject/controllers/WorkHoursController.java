package finalproject.controllers;

import finalproject.models.WorkHours;
import finalproject.services.WorkHoursService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WorkHoursController {

    private final WorkHoursService workHoursService;
    private final WorkHours workHours;

    public WorkHoursController(WorkHoursService workHoursService, WorkHours workHours) {
        this.workHoursService = workHoursService;
        this.workHours = workHours;
    }

    //GetMappings
    @GetMapping(path = "/getTime")
    public String getTimeBetweenStartAndEnd() {
        if (workHours.getStartTime() == null) {
            return "Please Check in first";
        } else if(workHours.getEndTime() == null) {
            return "Please Check out first";
        }
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
