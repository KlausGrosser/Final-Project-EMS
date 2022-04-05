package finalproject.controllers;

import finalproject.models.Employee;
import finalproject.models.WorkHours;
import finalproject.repositories.WorkHoursRepository;
import finalproject.services.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class WorkHoursController {

    private final AuthUtil authUtil;
    private final WorkHoursRepository workHoursRepository;

    @PostMapping("/api/startPauseWork")
    public Object startPauseWork(@RequestParam("action") String action) {
        //get logged in user
        Employee employee = authUtil.getLoggedInUser();

        if (employee == null)
            return "unauthenticated";

        //get current date
        long now = System.currentTimeMillis();
        LocalDate date = LocalDate.now();

        //get current time
        Time time = new Time(now);

        boolean working = false;

        switch (action) {
            case "start":
                WorkHours workHours = new WorkHours(date, time, employee);
                workHoursRepository.save(workHours);
                working = true;
                break;
            case "pause":
                //get last hours that has not ended
                WorkHours lastWorkHours = workHoursRepository.findByDateAndEndTimeAndEmployee(date, null, employee).orElse(null);
                if (lastWorkHours != null) {
                    lastWorkHours.setEndTime(time);

                    long id = lastWorkHours.getId();

                    //save last work hour
                    workHoursRepository.save(lastWorkHours);

                    lastWorkHours.setTotalTime(workHoursRepository.getTotalTimeHours(id));

                    //set Total Time Worked
                    workHoursRepository.save(lastWorkHours);
                }
                working = false;
                break;
        }

        return working;
    }
}
