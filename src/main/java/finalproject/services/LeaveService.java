package finalproject.services;

import finalproject.models.LeaveDetails;
import finalproject.models.LeaveStatus;
import finalproject.repositories.LeaveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.List;


@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRepository leaveRepository;

    public void applyLeave(LeaveDetails leaveDetails){

        Duration duration = Duration.between(leaveDetails.getFromDate(), leaveDetails.getToDate());
        leaveDetails.setDuration(duration);
        leaveDetails.setActive(true);
        leaveRepository.save(leaveDetails);
    }

    public List<LeaveDetails> getAllLeaves(){
        return leaveRepository.findAll();
    }

    public LeaveDetails getLeaveOfEmployee(String username){
        return leaveRepository.getAllLeavesOfUser(username);
    }

    public void updateLeaveDetails(LeaveDetails leaveDetails){
        leaveRepository.save(leaveDetails);
    }

    public List<LeaveDetails> getLeaveStatus(LeaveStatus leaveStatus){
      return leaveRepository.getLeaveStatus(leaveStatus);
    }

}
