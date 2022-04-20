package com.finalproject.model.service;

import com.finalproject.model.entity.Absence;
import com.finalproject.model.entity.Shift;
import com.finalproject.model.entity.Supervisor;
import com.finalproject.model.entity.User;
import com.finalproject.model.repository.AbsenceRepository;
import com.finalproject.model.repository.ShiftRepository;
import com.finalproject.model.repository.SupervisorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final ShiftRepository shiftRepository;
    private final SupervisorRepository supervisorRepository;
    private final UserService userService;

    @Autowired
    public AbsenceService(
            AbsenceRepository absenceRepository,
            ShiftRepository shiftRepository,
            SupervisorRepository supervisorRepository,
            UserService userService) {
        this.absenceRepository = absenceRepository;
        this.shiftRepository = shiftRepository;
        this.supervisorRepository = supervisorRepository;
        this.userService = userService;
    }

    public HashMap<User, List<User>> getEmployeesAndSupervisorsRelation(){
        List<User> absentEmployees = this.makeListOfEmployeesWithAbsences();
        HashMap<User, List<User>> employeesAndSupervisorRelation = new HashMap<>();

        for(Supervisor supervisor : supervisorRepository.findAll()){
            employeesAndSupervisorRelation.put(userService.findByFullName(supervisor.getFullName()), new ArrayList<>());
        }

        for(Map.Entry<User, List<User>> entry : employeesAndSupervisorRelation.entrySet()){
            for(User absentEmployee : absentEmployees){
                if(absentEmployee.getDepartment().equals(entry.getKey().getDepartment())){
                    entry.getValue().add(absentEmployee);
                }
            }
        }

        return employeesAndSupervisorRelation;
    }

    public List<String> getEmailsFromAbsentEmployees(List<User> absentEmployees){
        List<String> emails = new ArrayList<>();

        for(User user : absentEmployees){
            emails.add(user.getUsername());
        }

        return emails;
    }

    public List<User> makeListOfEmployeesWithAbsences(){
        List<User> employeesWithAbsences = new ArrayList<>();

        for(Absence absence : absenceRepository.findAll()){
            employeesWithAbsences.add(absence.getEmployee());
        }

        return employeesWithAbsences;
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

    public List<User> getAssignedEmployees(User supervisor){
        List<User> assignedEmployees = new ArrayList<>();

        for(User user : this.makeListOfEmployeesWithAbsences()){
            if(user.getSupervisorName().equals(supervisor.getFullName())){
                assignedEmployees.add(user);
            }
        }

        return assignedEmployees;
    }

}
