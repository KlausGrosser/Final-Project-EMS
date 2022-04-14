package com.finalproject.model.service;

import com.finalproject.dto.LeaveDTO;
import com.finalproject.model.entity.*;
import com.finalproject.model.repository.LeaveRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
@Log4j2
public class LeaveService {

    private final LeaveRepository leaveRepository;

    @Autowired
    public LeaveService(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    public Page<Leave> findAllLeavesPageable(Pageable pageable) {
        return leaveRepository.findAll(pageable);
    }

    public Leave createLeave(LeaveDTO leaveDTO) {
        Leave leave = Leave
                .builder()
                .description(leaveDTO.getDescription())
                .leaveReason(leaveDTO.getLeaveReason())
                .startTime(leaveDTO.getStartTime())
                .endTime(leaveDTO.getEndTime())
                .leaveStatus(LeaveStatus.PENDING)
                .build();

            leaveRepository.save(leave);
            log.info("New leave " + leave);

        return leave;
    }

    public Leave findLeaveById(long leaveId) {
        return leaveRepository.findById(leaveId).orElseThrow(() ->
                new IllegalArgumentException("Invalid leave id: " + leaveId));
    }


    public void deleteLeave(long id) {
        Leave leave = findLeaveById(id);
        Set<User> users = leave.getUsers();
        for (User user : users) {
            user.getLeaves().remove(leave);
        }
        leaveRepository.delete(leave);
    }


    public void approveLeaveRequest(long leaveId) {
        Leave leave = findLeaveById(leaveId);
        leave.setLeaveStatus(LeaveStatus.APPROVED);
        leaveRepository.save(leave);
    }


    public void rejectLeaveRequest(long leaveId) {
        Leave leave = findLeaveById(leaveId);
        leave.setLeaveStatus(LeaveStatus.REJECTED);
        leaveRepository.save(leave);
    }

}

