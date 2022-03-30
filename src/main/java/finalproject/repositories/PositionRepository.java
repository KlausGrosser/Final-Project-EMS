package finalproject.repositories;

import finalproject.models.Employee;
import finalproject.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    Position findByPositionName(String position);
}
