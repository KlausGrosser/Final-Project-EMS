package com.finalproject.model.service;

import com.finalproject.model.entity.Absence;
import com.finalproject.model.entity.User;
import com.finalproject.model.entity.Shift;
import com.finalproject.model.repository.AbsenceRepository;
import com.finalproject.model.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class ShiftService {

    private final UserService userService;
    private final ShiftRepository shiftRepository;
    private final AbsenceRepository absenceRepository;

    @Autowired
    public ShiftService(UserService userService, ShiftRepository shiftRepository, AbsenceRepository absenceRepository) {
        this.userService = userService;
        this.shiftRepository = shiftRepository;
        this.absenceRepository = absenceRepository;
    }

    //Format the date and time
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    public boolean isLastShiftOnCurrentDate(){
        User employee = userService.getCurrentUser();
        Shift shift = null;
        int comparison= -1;

        if(!employee.getShifts().isEmpty()){
            shift = findLastShift(employee);
            LocalDate currentDate = LocalDate.now();
            if(shift.getShiftDay() != null){
                LocalDate lastShiftDate = shift.getShiftDay();
                comparison = currentDate.compareTo(ChronoLocalDate.from(lastShiftDate));
            }
        }

        return comparison == 0;

    }

    public String start() {
        User employee = userService.getCurrentUser();
        Shift shift = null;

        shift = findLastShift(employee);
        shift.setAbsent(false);
        shift.setShiftDay(LocalDate.now());

        String result = "";

        if (!(shift.isCurrentlyWorking())) {
            shift.setCurrentlyWorking(true);
            shift.setTempStartTime(LocalDateTime.now());
            shift.getCheckIns().add(shift.getTempStartTime());

            saveShiftToEmployee(employee, shift);

            result = "Check in at: " + shift.getTempStartTime().format(formatter);
        } else {
            result = "You already checked in! Can't do it again if you don't check-out";
        }
        return result;
    }

    public String stop() {
        User employee = userService.getCurrentUser();
        Shift shift = findLastShift(employee);
        String result = "";

        if(shift !=null){
            if (shift.isCurrentlyWorking()) {
                shift.setCurrentlyWorking(false);
                shift.setTempEndTime(LocalDateTime.now());
                shift.getCheckOuts().add(shift.getTempEndTime());
                this.getTimeBetweenStartAndEnd(shift);

                saveShiftToEmployee(employee, shift);

                result = "Check out at: " + shift.getTempEndTime().format(formatter);
            } else {
                result = "You already checked-out!!";
            }
            return result;
        }else{
            return "Sorry, you need to check in first!";
        }


    }

    public String getTotalWorkedTime() {
        User employee = userService.getCurrentUser();
        Shift shift = findLastShift(employee);

        if(shift !=null){
            if(shift.getTempEndTime() != null){
                Duration difference = Duration.between(shift.getTempStartTime(), shift.getTempEndTime());
                if(difference == null){
                    return "Sorry, you need to check in first";
                } else {
                    Duration total = getTotalTimeWorked(shift);

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

    public void saveShiftToEmployee(User employee, Shift shift){
        Shift temp = null;
        if(!employee.getShifts().isEmpty()){
            temp = employee.getShifts().get(employee.getShifts().size()-1);
        }

        long id1 = shift.getId();
        long id2 = 0;
        if(temp != null){
            id2 = temp.getId();
        }

        boolean idMatches = id1 == id2;
        if(idMatches){
            return;
        }else{
            employee.getShifts().add(shift);
        }

        shiftRepository.save(shift);

        userService.save(employee);

    }

    public Shift findLastShift(User employee){
        Shift shift = null;
        if(!employee.getShifts().isEmpty()){
            int currentWorkHours = employee.getShifts().size();
            shift = employee.getShifts().get(currentWorkHours-1);
        }
        if(shift != null){
            return shiftRepository.getById(shift.getId());
        }else{
            return null;
        }

    }

    public Duration getTotalTimeWorked(Shift shift) {
        long mils = 0;
        for (Duration d : shift.getWorkedPeriods()) {
            mils += d.toMillis();
        }
        Duration total = Duration.ofMillis(mils);
        shift.setTotalTimeWorked(total);

        return total;
    }

    public void getTimeBetweenStartAndEnd(Shift shift){
        Duration total = Duration.between(shift.getTempStartTime(), shift.getTempEndTime());

        shift.setTimeWorkedInLastPeriod(total);
        shift.getWorkedPeriods().add(total);
    }


}

