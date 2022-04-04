package finalproject.repositories;

import finalproject.models.TimeKeeping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeKeepingRepository extends JpaRepository<TimeKeeping, String> {

}



