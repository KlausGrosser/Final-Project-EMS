//package finalproject.services;
//
//import finalproject.models.Employee;
//import finalproject.models.WorkHours;
//import finalproject.repositories.WorkHoursRepository;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//
//@Service
//public class WorkHoursService {
//
//    //Format the date and time
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//
//    private final EmployeeService employeeService;
//    private final WorkHours workHours;
//    private final WorkHoursRepository workHoursRepository;
//
//    //Logger logger = LoggerFactory.getLogger(WorkHoursService.class);
//
//    public WorkHoursService(EmployeeService employeeService, WorkHours workHours, WorkHoursRepository workHoursRepository) {
//        this.employeeService = employeeService;
//        this.workHours = workHours;
//        this.workHoursRepository = workHoursRepository;
//    }
//
//    //Method for starting the check-in
//    public String start(WorkHours workHours) {
//
//        String result = "";
//
//        if(!rangeWorkingHours()){
//            return "The working hours range is from 07.00 am to 19.00 pm";
//        }
//        else if((!(workHours.isCurrentlyWorking()))) {
//            workHours.setCurrentlyWorking(true);
//            workHours.setStartTime(LocalDateTime.now());
//            result = "Check in at: " + workHours.getStartTime().format(formatter);
//        } else {
//            result = "You already check in! Can't do it again if you don't check-out";
//        }
//
//        return result;
//
//    }
//
//    //Method for stopping the check-in
//    public String stop() {
//        String result = "";
//
//        if(!rangeWorkingHours()) {
//            return "The working hours range is from 07.00 am to 19.00 pm";
//        }else if (workHours.isCurrentlyWorking()) {
//            workHours.setCurrentlyWorking(false);
//            workHours.setEndTime(LocalDateTime.now());
//            result = "Check out at: " + workHours.getEndTime().format(formatter);
//        } else {
//            result = "You already check-out!! You need to first check-in";
//        }
//        return result;
//    }
//
//
//    //Method to calculate the time worked during the day
//    public String getTimeBetweenStartAndEnd() {
//        Duration difference = Duration.between(workHours.getStartTime(), workHours.getEndTime());
//        if (difference == null) {
//            return "Sorry, you need to check in first";
//        } else {
//            workHours.setTimeWorked(difference);
//            workHours.getWorkedTimes().add(difference);
//            String total = "Time worked today: " + difference.toHours() + " hours, "
//                    + difference.toMinutes() + " minutes, " + difference.toSeconds() + " seconds";
//
//           return total;
//
//        }
//    }
//
//    public boolean rangeWorkingHours() {
//        LocalTime now = LocalTime.now();
//        return now.isAfter(LocalTime.parse("06:59")) && now.isBefore(LocalTime.parse("19:01"));
//    }
//
//    public void save(Long id){
//        Employee employee = employeeService.getEmployeeById(id).orElseThrow();
//        employee.getWorkHours().add(this.workHours);
//    }
//
//}
//










