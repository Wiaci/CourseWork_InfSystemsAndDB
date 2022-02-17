package vyach.courseworkmaythegodhelpme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vyach.courseworkmaythegodhelpme.entity.Status;

public interface StatusRepo extends JpaRepository<Status, Long> {
}
