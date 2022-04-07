package finalproject.controllers;

import finalproject.services.WorkHoursService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class WorkHoursController {

    private final WorkHoursService workHoursService;

    public WorkHoursController(WorkHoursService workHoursService) {
        this.workHoursService = workHoursService;
    }

    //GetMappings
//    @GetMapping(path = "/getTime")
//    public void getTimeBetweenStartAndEnd() {
//        workHoursService.getTimeBetweenStartAndEnd();
//    }

    @GetMapping(path = "/getTotalWorked")
    public String getTotalTimeWorked(Model model){
        model.addAttribute("getTotal", workHoursService.getTotalTimeWorked());
        return "hours_worked";
    }

    //PostMappings
    @PostMapping(path = "/start")
    public String startWorking(Model model) throws Exception {
        model.addAttribute("start", workHoursService.start());
        return "hours_worked";
    }

    @PostMapping("/stop")
    public String stopWorking(Model model) throws Exception {
        model.addAttribute("stop", workHoursService.stop());
        return "hours_worked";

    }
}
