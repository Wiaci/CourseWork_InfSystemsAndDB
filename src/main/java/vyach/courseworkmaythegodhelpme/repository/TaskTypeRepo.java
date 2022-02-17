package vyach.courseworkmaythegodhelpme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vyach.courseworkmaythegodhelpme.entity.TaskType;

@Repository
public interface TaskTypeRepo extends JpaRepository<TaskType, Long> {

}
