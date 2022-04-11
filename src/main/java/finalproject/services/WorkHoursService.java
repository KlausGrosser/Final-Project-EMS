package finalproject.services;

import finalproject.models.Employee;
import finalproject.models.WorkHours;
import finalproject.repositories.WorkHoursRepository;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;


@Service
@Getter
@Setter
public class WorkHoursService {

    private final EmployeeService employeeService;
    private final WorkHoursRepository workHoursRepository;
    Logger logger = LoggerFactory.getLogger(WorkHoursService.class);

    @Autowired
    public WorkHoursService(EmployeeService employeeService, WorkHoursRepository workHoursRepository) {
        this.employeeService = employeeService;
        this.workHoursRepository = workHoursRepository;

    }

    //Format the date and time
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    public boolean isCurrentWorkHourOnCurrentDate(){
        Employee employee = getCurrentEmployee();
        WorkHours workHours = null;
        int comparison= -1;
        if(!employee.getWorkHours().isEmpty()){
            workHours = findCurrentWorkHours(employee);
            LocalDate currentDate = LocalDate.now();
            if(!workHours.getStopTimes().isEmpty()){
                LocalDateTime lastCheckout = workHours.getStopTimes().get(workHours.getStopTimes().size()-1);
                comparison = currentDate.compareTo(ChronoLocalDate.from(lastCheckout));
            }else{
                LocalDateTime lastCheckin = workHours.getStartTimes().get(workHours.getStartTimes().size()-1);
                comparison = currentDate.compareTo(ChronoLocalDate.from(lastCheckin));
            }

        }

        return comparison == 0;

    }


    public String start() {
        Employee employee = getCurrentEmployee();
        WorkHours workHours = null;

        if(employee.getWorkHours().isEmpty()){
            workHours = new WorkHours();
        }else if(!isCurrentWorkHourOnCurrentDate()){
            workHours = new WorkHours();
        }
        else{
            workHours = findCurrentWorkHours(employee);
        }
        String result = "";

        if (!(workHours.isCurrentlyWorking())) {
            workHours.setCurrentlyWorking(true);
            workHours.setStartTime(LocalDateTime.now());
            workHours.getStartTimes().add(workHours.getStartTime());

            workHoursRepository.save(workHours);

            saveWorkHoursToEmployee(employee, workHours);

            employeeService.save(employee);

            result = "Check in at: " + workHours.getStartTime().format(formatter);
        } else {
            result = "You already checked in! Can't do it again if you don't check-out";
        }
        return result;
    }

    public String stop() {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = getCurrentEmployee();
        WorkHours workHours = findCurrentWorkHours(employee);
        String result = "";

        if(workHours!=null){
            if (workHours.isCurrentlyWorking()) {
                workHours.setCurrentlyWorking(false);
                workHours.setEndTime(LocalDateTime.now());
                workHours.getStopTimes().add(workHours.getEndTime());

                Duration workedHours = this.getTimeBetweenStartAndEnd(workHours);

                workHoursRepository.save(workHours);

                saveWorkHoursToEmployee(employee, workHours);

                //employeeService.save(employee);


                result = "Check out at: " + workHours.getEndTime().format(formatter);
            } else {
                result = "You already checked-out!!";
            }
            return result;
        }else{
            return "Sorry, you need to check in first!";
        }


    }

    public Duration getTimeBetweenStartAndEnd(WorkHours workHours){

        Duration total = Duration.between(workHours.getStartTime(), workHours.getEndTime());

        workHours.setTimeWorked(total);
        workHours.getWorkedTimes().add(total);

        System.out.println("Time worked: " + total.toString());

        return total;
    }


    public String getTotalWorkedTime() {
        Employee employee = getCurrentEmployee();
        WorkHours workHours = findCurrentWorkHours(employee);
        if(workHours!=null){
            if(workHours.getEndTime() != null){
                Duration difference = Duration.between(workHours.getStartTime(), workHours.getEndTime());
                if(difference == null){
                    return "Sorry, you need to check in first";
                } else {
                    long mils = 0;
                    for(Duration d : workHours.getWorkedTimes()){
                        mils+= d.toMillis();
                    }

                    Duration total = Duration.ofMillis(mils);
                    System.out.println("Total time worked: " +total.toString());

                    return "Time worked today: " + total.toHours() + " hours, "
                            + total.toMinutes() + " minutes, " + total.toSeconds() + " seconds";
                }
            }else{
                return "Sorry, you need to check out first";
            }

        }else{
            return "Sorry, you need to check out first";
        }

    }

    public void saveWorkHoursToEmployee(Employee employee, WorkHours workHours){
        WorkHours temp = null;
        if(!employee.getWorkHours().isEmpty()){
            temp = employee.getWorkHours().get(employee.getWorkHours().size()-1);
        }

        long id1 = workHours.getId();
        long id2 = 0;
        if(temp != null){
            id2 = temp.getId();
        }

        boolean idMatches = id1 == id2;
        if(idMatches){
            return;
        }else{
            employee.getWorkHours().add(workHours);
        }

    }

    public WorkHours findCurrentWorkHours(Employee employee){
        WorkHours workHours = null;
        if(!employee.getWorkHours().isEmpty()){
            int currentWorkHours = employee.getWorkHours().size();
            workHours = employee.getWorkHours().get(currentWorkHours-1);
        }
        if(workHours != null){
            return workHoursRepository.getById(workHours.getId());
        }else{
            return null;
        }

    }

    public WorkHours findCurrentWorkHours(){
        Employee employee = this.getCurrentEmployee();
        WorkHours workHours = null;
        if(!employee.getWorkHours().isEmpty()){
            int currentWorkHours = employee.getWorkHours().size();
            workHours = employee.getWorkHours().get(currentWorkHours-1);
        }
        if(workHours != null){
            return workHoursRepository.getById(workHours.getId());
        }else{
            return null;
        }

    }

    public Employee getCurrentEmployee(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return employeeService.findByEmail(auth.getName()).orElseThrow();
    }


    public Duration getTotalTimeWorked(WorkHours workHours) {
        long mils = 0;
        for (Duration d : workHours.getWorkedTimes()) {
            mils += d.toMillis();
        }
        Duration total = Duration.ofMillis(mils);
        workHours.setTotalTimeWorked(total);
        //System.out.println("Total time worked: " + total.toString());

        return total;
    }


}










