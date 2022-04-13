package com.finalproject.model.service;

import com.finalproject.model.entity.User;
import com.finalproject.model.entity.WorkHours;
import com.finalproject.model.repository.WorkHoursRepository;
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
public class WorkHoursService {

    private final UserService userService;
    private final WorkHoursRepository workHoursRepository;

    @Autowired
    public WorkHoursService(UserService userService, WorkHoursRepository workHoursRepository) {
        this.userService = userService;
        this.workHoursRepository = workHoursRepository;
    }

    //Format the date and time
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    public boolean isCurrentWorkHourOnCurrentDate(){
        User employee = getCurrentEmployee();
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
        User employee = getCurrentEmployee();
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

            userService.save(employee);

            result = "Check in at: " + workHours.getStartTime().format(formatter);
        } else {
            result = "You already checked in! Can't do it again if you don't check-out";
        }
        return result;
    }

    public String stop() {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User employee = getCurrentEmployee();
        WorkHours workHours = findCurrentWorkHours(employee);
        String result = "";

        if(workHours!=null){
            if (workHours.isCurrentlyWorking()) {
                workHours.setCurrentlyWorking(false);
                workHours.setEndTime(LocalDateTime.now());
                workHours.getStopTimes().add(workHours.getEndTime());

                workHoursRepository.save(workHours);

                saveWorkHoursToEmployee(employee, workHours);

                userService.save(employee);


                result = "Check out at: " + workHours.getEndTime().format(formatter);
            } else {
                result = "You already checked-out!!";
            }
            return result;
        }else{
            return "Sorry, you need to check in first!";
        }


    }

    public String getTimeBetweenStartAndEnd() {
        User employee = getCurrentEmployee();
        WorkHours workHours = findCurrentWorkHours(employee);
        if(workHours!=null){
            Duration difference = Duration.between(workHours.getStartTime(), workHours.getEndTime());
            if(difference == null){
                return "Sorry, you need to check out first";
            } else {
                workHours.setTimeWorked(difference);

                Duration lastWorkedTime = Duration.ofDays(0);

                if(!workHours.getWorkedTimes().isEmpty()){
                    lastWorkedTime = workHours.getWorkedTimes().get(workHours.getWorkedTimes().size() -1);
                }

                if(lastWorkedTime!=difference){
                    workHours.getWorkedTimes().add(difference);
                }


                Duration currentTotalWorkedTime = getTotalTimeWorked(workHours);

                workHoursRepository.save(workHours);
                saveWorkHoursToEmployee(employee, workHours);

                String total = "Time worked today: " + currentTotalWorkedTime.toHours() + " hours, "
                        + currentTotalWorkedTime.toMinutes() + " minutes, " + currentTotalWorkedTime.toSeconds() + " seconds";

                return total;
            }
        }else{
            return "Sorry, you need to check out first";
        }

    }

    public void saveWorkHoursToEmployee(User employee, WorkHours workHours){
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

    public WorkHours findCurrentWorkHours(User employee){
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

    public User getCurrentEmployee(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName());
    }

    public Duration getTotalTimeWorked(WorkHours workHours) {
        long mils = 0;
        for (Duration d : workHours.getWorkedTimes()) {
            mils += d.toMillis();
        }
        Duration total = Duration.ofMillis(mils);
        workHours.setTotalTimeWorked(total);

        return total;
    }



}

