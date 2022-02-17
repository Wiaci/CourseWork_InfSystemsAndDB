package vyach.courseworkmaythegodhelpme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vyach.courseworkmaythegodhelpme.entity.House;

@Repository
public interface HouseRepo extends JpaRepository<House, Long> {
}
