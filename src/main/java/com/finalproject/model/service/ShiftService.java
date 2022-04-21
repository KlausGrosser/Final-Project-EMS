package com.finalproject.model.service;

import com.finalproject.dto.ShiftDTO;
import com.finalproject.model.entity.User;
import com.finalproject.model.entity.Shift;
import com.finalproject.model.repository.AbsenceRepository;
import com.finalproject.model.repository.ShiftRepository;
import com.finalproject.model.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


@Service
@Log4j2
public class ShiftService {

    private final UserService userService;
    private final ShiftRepository shiftRepository;
    private final AbsenceRepository absenceRepository;
    private final UserRepository userRepository;

    @Autowired
    public ShiftService(UserService userService, ShiftRepository shiftRepository, AbsenceRepository absenceRepository, UserRepository userRepository) {
        this.userService = userService;
        this.shiftRepository = shiftRepository;
        this.absenceRepository = absenceRepository;
        this.userRepository = userRepository;
    }

    //Format the date and time
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    public boolean isLastShiftOnCurrentDate(){
        User employee = userService.getCurrentUser();
        Shift shift = null;
        int comparison= -1;

        if(!employee.getShifts().isEmpty()){
            shift = findCurrentShift(employee);
            LocalDate currentDate = LocalDate.now();
            if(shift.getShiftDay() != null){
                LocalDate lastShiftDate = shift.getShiftDay();
                comparison = currentDate.compareTo(ChronoLocalDate.from(lastShiftDate));
            }
        }

        return comparison == 0;

    }

    public void start() {
        User employee = userService.getCurrentUser();
        Shift shift = findCurrentShift(employee);

        if(Objects.nonNull(shift)){
            shift.setAbsent(false);
            shift.setShiftDay(LocalDate.now());

            if (!(shift.isCurrentlyWorking())) {
                shift.setCurrentlyWorking(true);
                shift.setTempStartTime(LocalDateTime.now());
                shift.getCheckIns().add(shift.getTempStartTime());
                shift.setStatusMessage("Check in at: " + shift.getTempStartTime().format(formatter));
                saveShiftAndUpdateEmployee(employee, shift);
            }else {
                shift.setStatusMessage("You already checked in! Can't do it again if you don't check-out");
            }
        }
    }

    public void stop() {
        User employee = userService.getCurrentUser();
        Shift shift = findCurrentShift(employee);

        if(shift !=null){
            if (shift.isCurrentlyWorking()) {
                shift.setCurrentlyWorking(false);
                shift.setTempEndTime(LocalDateTime.now());
                shift.getCheckOuts().add(shift.getTempEndTime());
                this.getTimeBetweenStartAndEnd(shift);
                shift.setStatusMessage("Check out at: " + shift.getTempEndTime().format(formatter));
                saveShiftAndUpdateEmployee(employee, shift);
            } else {
                shift.setStatusMessage("Sorry, you need to check in first!");
            }
        }


    }

    public void getTotalWorkedTime() {
        User employee = userService.getCurrentUser();
        Shift shift = findCurrentShift(employee);

        if(shift !=null){
            if(shift.getTempEndTime() != null){
                Duration difference = Duration.between(shift.getTempStartTime(), shift.getTempEndTime());
                if(difference != null){
                    Duration total = getTotalTimeWorked(shift);
                    this.saveShiftAndUpdateEmployee(employee, shift);

                    shift.setStatusMessage("Time worked today: " + total.toHours() + " hours, "
                            + total.toMinutes() + " minutes, " + total.toSeconds() + " seconds");
                }
            }else{
                shift.setStatusMessage("Sorry, you need to check in and check out first!");
            }
        }
    }

    public void saveShiftAndUpdateEmployee(User employee, Shift shift){
        if(employee.getShifts().isEmpty()){
            employee.getShifts().add(shift);
            shiftRepository.save(shift);
            userService.save(employee);
        }
        for(Shift temp : employee.getShifts()){
            if(temp.equals(shift)){
                shiftRepository.save(shift);
                userService.save(employee);
            }
        }
    }

    public Shift findCurrentShift(User employee){
        LocalDate currentDate = LocalDate.now();
        Shift shift = null;
        for(Shift temp : employee.getShifts()){
            if(temp.getAssignedDay().equals(currentDate)){
                shift = temp;
            }
        }
        return shift;
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

    public void createNewShift(ShiftDTO shiftDTO) {
        Shift shift = Shift
                .builder()
                .assignedDay(shiftDTO.getAssignedDay())
                .assignedStartTime(shiftDTO.getAssignedStartTime())
                .assignedEndTime(shiftDTO.getAssignedEndTime())
                .employee(userService.findByFullName(shiftDTO.getAssignedEmployeeName()))
                .build();

        try {
            this.saveShiftAndUpdateEmployee(shift.getEmployee(), shift);
            //shiftRepository.save(shift);
            log.info("New shift " + shift);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

