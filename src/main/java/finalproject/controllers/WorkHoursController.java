package finalproject.controllers;


import finalproject.services.WorkHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class WorkHoursController {

    private final WorkHoursService workHoursService;

    @Autowired
    public WorkHoursController(WorkHoursService workHoursService) {
        this.workHoursService = workHoursService;
    }


    //GetMappings
    @GetMapping(path = "/getTime")
    public String getTotalWorkedTime() {
        return workHoursService.getTotalWorkedTime();
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
