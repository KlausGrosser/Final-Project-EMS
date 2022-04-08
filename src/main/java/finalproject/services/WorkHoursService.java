package finalproject.services;

import finalproject.models.WorkHours;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class WorkHoursService {


    private final WorkHours workHours;
    Logger logger = LoggerFactory.getLogger(WorkHoursService.class);

    @Autowired
    public WorkHoursService(WorkHours workHours) {
        this.workHours = workHours;
    }

    //Format the date and time
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    public String start() {
        String result = "";
        if (!(workHours.isCurrentlyWorking())) {
            workHours.setCurrentlyWorking(true);
            workHours.setStartTime(LocalDateTime.now());
            result = "Check in at: " + workHours.getStartTime().format(formatter);
        } else {
            result = "You already check in! Can't do it again if you don't check-out";
        }
        return result;
    }

    public String stop() {
        String result = "";
        if (workHours.isCurrentlyWorking()) {
            workHours.setCurrentlyWorking(false);
            workHours.setEndTime(LocalDateTime.now());
            //workHours.getStopTimes().add(workHours.getEndTime());
            result = "Check out at: " + workHours.getEndTime().format(formatter);
        } else {
            result = "You already check-out!!";
        }
        return result;
    }


    public String getTimeBetweenStartAndEnd() {
        Duration difference = Duration.between(workHours.getStartTime(), workHours.getEndTime());
        if(difference == null){
            return "Sorry, you need to check in first";
        } else {
        workHours.setTimeWorked(difference);
        workHours.getWorkedTimes().add(difference);
        String total = "Time worked today: " + difference.toHours() + " hours, "
                + difference.toMinutes() + " minutes, " + difference.toSeconds() + " seconds";

        return total;
        }
    }

//    public boolean rangeWorkHours (){
//        String now = LocalDateTime.now().format(timeFormatter);
//        String startingHour =
//        if ()
//
//    }

}


//    public Duration getTotalTimeWorked() {
//        long mils = 0;
//        Duration total = Duration.ofMillis(mils);
//        for (Duration d : workHours.getWorkedTimes()) {
//            mils += d.toMillis();
//        }
//
//
//        System.out.println("Total time worked: " + total.toString());
//
//        return total;
//    }








