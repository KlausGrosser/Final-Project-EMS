package com.finalproject.model.service;

import com.finalproject.model.entity.Absence;
import com.finalproject.model.entity.Shift;
import com.finalproject.model.entity.User;
import com.finalproject.model.repository.AbsenceRepository;
import com.finalproject.model.repository.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final ShiftRepository shiftRepository;
    private final UserService userService;

    @Autowired
    public AbsenceService(AbsenceRepository absenceRepository, ShiftRepository shiftRepository, UserService userService) {
        this.absenceRepository = absenceRepository;
        this.shiftRepository = shiftRepository;
        this.userService = userService;
    }

    public void sendMessageToSupervisor(){
        List<User> supervisors = new ArrayList<User>();
        HashMap<User, User> employeesAndSupervisorRelation = new HashMap<>();



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
        List<Absence> allAbsences = absenceRepository.findAll();
        List<User> absentEmployees = this.makeListOfAbsentEmployees(allAbsences);

        return this.makeListOfEmployeesWithWarning(absentEmployees);
        }



    public List<User> makeListOfAbsentEmployees(List<Absence> allAbsences) {
        List<User> absentEmployees = new ArrayList<>();
        for(Absence absence : allAbsences){
            absentEmployees.add(absence.getEmployee());
        }
        return absentEmployees;
    }

    public List<User> makeListOfEmployeesWithWarning(List<User> absentEmployees){
        List<User> employeesWithWarning = new ArrayList<>();

        for(User employee : absentEmployees) {
            List<Absence> absences = employee.getAbsences();
            int daysBetweenAttendances = 0;

            for (int i = absences.size(); i-- > 0; ) {
                Absence absence = absences.get(i);

                daysBetweenAttendances = absence.getAbsenceDay()
                        .compareTo(ChronoLocalDate.from(absences.get(i - 1).getAbsenceDay()));

                if (daysBetweenAttendances > 1) {
                    employeesWithWarning.add(employee);
                }
            }
        }
        return employeesWithWarning;
    }

    @Scheduled(cron="0 30 23 * * MON-FRI")
    public void checkIfEmployeesWereAbsent(){
        for(Shift shift : shiftRepository.findAll()){
            if(shift.isAbsent()){
                User employee = shift.getEmployee();
                Absence absence = new Absence(shift.getAssignedDay(), employee);
                absenceRepository.save(absence);
                employee.getAbsences().add(absence);
                userService.save(employee);
            }
        }
    }

}
