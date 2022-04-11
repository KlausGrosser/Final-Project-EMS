package finalproject.services;

import finalproject.models.Employee;
import finalproject.models.LeaveDetails;
import finalproject.models.LeaveStatus;
import finalproject.repositories.LeaveRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;


@Service
public class LeaveService {

    private final EmployeeService employeeService;
    private final LeaveRepository leaveRepository;

    public LeaveService(EmployeeService employeeService, LeaveRepository leaveRepository) {
        this.employeeService = employeeService;
        this.leaveRepository = leaveRepository;
    }

    public void applyLeave(LeaveDetails leaveDetails){
        Employee employee = getCurrentEmployee();

        int differenceDays = getDifferenceDays(leaveDetails.getToDate(),leaveDetails.getFromDate());
        System.out.println(differenceDays);
        leaveDetails.setDuration(differenceDays);
        employee.getLeaves().add(leaveDetails);
        leaveRepository.save(leaveDetails);
    }


    public int getDifferenceDays(Date from, Date to) {
        int noOfDays = (int) DAYS.between((Temporal) from, (Temporal) to);
        return noOfDays;
    }

    public List<LeaveDetails> getAllLeaves(){
        return leaveRepository.findAll();
    }

    public LeaveDetails getLeaveOfEmployee(String username){
        return leaveRepository.getAllLeavesOfUser(username);
    }

    public void updateLeaveDetails(LeaveDetails leaveDetails){
        leaveRepository.save(leaveDetails);
    }

    public List<LeaveDetails> getLeaveStatus(String username){
      return leaveRepository.getLeaveStatus(username);
    }

    public Employee getCurrentEmployee(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return employeeService.findByEmail(auth.getName()).orElseThrow();
    }


}
