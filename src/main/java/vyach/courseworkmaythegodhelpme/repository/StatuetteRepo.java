package vyach.courseworkmaythegodhelpme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import vyach.courseworkmaythegodhelpme.entity.Statuette;

@Repository
public interface StatuetteRepo extends JpaRepository<Statuette, Long> {
}

