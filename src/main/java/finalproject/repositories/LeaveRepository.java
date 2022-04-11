package finalproject.repositories;

import finalproject.models.LeaveDetails;
import finalproject.models.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface LeaveRepository extends JpaRepository<LeaveDetails, Long> {

   @Query(value = "SELECT u FROM LeaveDetails u WHERE u.username = ?1")
   LeaveDetails getAllLeavesOfUser(String username);

   @Query(value = "SELECT u FROM LeaveDetails u WHERE u.leaveStatus = ?1")
   List<LeaveDetails> getLeaveStatus(LeaveStatus leaveStatus);




}
