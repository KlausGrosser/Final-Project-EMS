package com.finalproject.model.service;

import com.finalproject.model.entity.Attendance;
import com.finalproject.model.entity.User;
import com.finalproject.model.repository.AttendanceRepository;
import com.finalproject.util.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmailService emailService;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository, EmailService emailService) {
        this.attendanceRepository = attendanceRepository;
        this.emailService = emailService;
    }

    public void sendMessageToManager(){

    }



    public List<String> getEmailsFromAbsentEmployees(){
        List<String> emails = new ArrayList<>();
        List<User> absentEmployees = this.getEmployeesWithUnusualAbsenteeism();

        for(User user : absentEmployees){
            emails.add(user.getUsername());
        }

        return emails;
    }

    public List<User> getEmployeesWithUnusualAbsenteeism(){
        List<Attendance> allAttendances = attendanceRepository.findAll();
        List<User> absentEmployees = this.makeListOfAbsentEmployees(allAttendances);

        return this.makeListOfEmployeesWithWarning(absentEmployees);
        }



    public List<User> makeListOfAbsentEmployees(List<Attendance> allAttendances) {
        List<User> absentEmployees = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        for(Attendance attendance : allAttendances){
            if(!attendance.getEmployee().getLastAttendance().getAttendedDay().equals(currentDate)){
                absentEmployees.add(attendance.getEmployee());
            }
        }
        return absentEmployees;
    }

    public List<User> makeListOfEmployeesWithWarning(List<User> absentEmployees){
        List<User> employeesWithWarning = new ArrayList<>();

        for(User employee : absentEmployees) {
            List<Attendance> attendances = employee.getAttendances();
            int daysBetweenAttendances = 0;

            for (int i = attendances.size(); i-- > 0; ) {
                Attendance attendance = attendances.get(i);

                daysBetweenAttendances = attendance.getAttendedDay()
                        .compareTo(ChronoLocalDate.from(attendances.get(i - 1).getAttendedDay()));

                if (daysBetweenAttendances > 1) {
                    employeesWithWarning.add(employee);
                }
            }
        }
        return employeesWithWarning;
    }




}
