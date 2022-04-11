//package finalproject.controllers;
//
//import finalproject.models.Employee;
//import finalproject.models.WorkHours;
//import finalproject.services.WorkHoursService;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.security.Principal;
//
//
//@RestController
//public class WorkHoursController {
//
//    private final WorkHoursService workHoursService;
//    private final WorkHours workHours;
//
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//    public WorkHoursController(WorkHoursService workHoursService, WorkHours workHours) {
//        this.workHoursService = workHoursService;
//        this.workHours = workHours;
//    }
//
//    //GetMappings
//    @GetMapping(path = "/getTime")
//    public String getTimeBetweenStartAndEnd() {
//        if (workHours.getStartTime() == null) {
//            return "Please Check in first";
//        } else if(workHours.getEndTime() == null) {
//            return "Please Check out first";
//        }
//        return workHoursService.getTimeBetweenStartAndEnd();
//    }
//
//    @GetMapping
//    public String currentUserName(Principal principal) {
//        return principal.getName();
//    }
//
//
//    //PostMappings
//    @PostMapping(path = "/check_in")
//    public String startWorking() {
//        return workHoursService.start(workHours);
//    }
//
//    @PostMapping(path = "/check_out")
//    public String stopWorking() {
//        Employee employee = (Employee) auth.getDetails();
//        workHoursService.save(employee.getId());
//        return workHoursService.stop();
//    }
//
//}
