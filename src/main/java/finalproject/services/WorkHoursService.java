package finalproject.services;

import finalproject.models.WorkHours;
import finalproject.models.Employee;
import finalproject.repositories.WorkHoursRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.sql.Time;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WorkHoursService {

    private final WorkHoursRepository workHoursRepository;
    private final EmployeeService employeeService;


    public boolean getHoursWorked(String action) {

        Employee employee = employeeService.getCurrentlyLoggedIn();

        //Current Date and Time
        LocalDate date = LocalDate.now();
        long now = System.currentTimeMillis();
        Time time = new Time(now);

        boolean working = false;

        switch (action){
            case "start":
                WorkHours workHours = new WorkHours(date, time, employee);
                workHoursRepository.save(workHours);
                working = true;
                break;
            case  "stop":
                WorkHours lastWorkHours = workHoursRepository.findByDateAndEndTimeAndEmployee(date, null, employee).orElse(null);
                if(lastWorkHours != null){
                    lastWorkHours.setEndTime(time);

                    //Save last worked hours
                    workHoursRepository.save(lastWorkHours);
                }
                working = false;
                break;
        }
        return working;

    }

}
