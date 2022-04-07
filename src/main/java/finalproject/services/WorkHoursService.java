package finalproject.services;

import finalproject.models.WorkHours;
import finalproject.repositories.WorkHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;


@Service
public class WorkHoursService {


    private final WorkHoursRepository workHoursRepository;
    private final EmployeeService employeeService;
    private final WorkHours workHours;

    @Autowired
    public WorkHoursService(WorkHoursRepository workHoursRepository, EmployeeService employeeService, WorkHours workHours) {
        this.workHoursRepository = workHoursRepository;
        this.employeeService = employeeService;
        this.workHours = workHours;
    }


    public LocalDateTime start() throws Exception {
        if (!(workHours.isCurrentlyWorking())) {
            workHours.setCurrentlyWorking(true);
            workHours.setStartTime(workHours.getCurrentDate());

            System.out.println("Check in at: " + workHours.getStartTime().toString());
        } else {
            throw new Exception("Can't start since employee is already working");
        }
        return null;
    }

    public Object stop() throws Exception {
        if (workHours.isCurrentlyWorking()) {
            workHours.setCurrentlyWorking(false);
            workHours.setEndTime(workHours.getCurrentDate());
            workHours.getStopTimes().add(workHours.getEndTime());
            System.out.println("Stopped working at: " + workHours.getEndTime().toString());
        } else {
            throw new Exception("Can't stop since employee is not working");
        }
        return null;
    }

    public Duration getTimeBetweenStartAndEnd() {
        Duration total = Duration.between(workHours.getStartTime(), workHours.getEndTime());

        workHours.setTimeWorked(total);
        workHours.getWorkedTimes().add(total);

        System.out.println("Time worked: " + total.toString());

        return total;
    }


    public Duration getTotalTimeWorked() {
        long mils = 0;
        Duration total = Duration.ofMillis(mils);
        for (Duration d : workHours.getWorkedTimes()) {
            mils += d.toMillis();
        }


        System.out.println("Total time worked: " + total.toString());

        return total;
    }
}







