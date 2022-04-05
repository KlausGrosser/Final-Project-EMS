package finalproject.repositories;

import finalproject.models.Employee;
import finalproject.models.WorkHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkHoursRepository extends JpaRepository<WorkHours, Long> {

    Optional<WorkHours> findByDateAndEndTimeAndEmployee(LocalDate date, Time endTime, Employee employee);

    @Query(value = "SELECT TIMEDIFF(end_time, start_time ) from work_hours WHERE id = ?1", nativeQuery = true)
    Time getTotalTimeHours(@Param("id") long id);

    List<WorkHours> findAllByDate(LocalDate date);

}