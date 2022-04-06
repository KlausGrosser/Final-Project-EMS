package finalproject.repositories;

import finalproject.models.WorkHours;
import finalproject.models.Employee;
import org.hibernate.jdbc.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WorkHoursRepository extends JpaRepository<WorkHours, Long> {

    Optional<WorkHours>findByDateAndEndTimeAndEmployee(LocalDate date, Time end_time, Employee employee);

   // WorkHours getTotalTime (Time start, Time end);


}
