package vyach.courseworkmaythegodhelpme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vyach.courseworkmaythegodhelpme.entity.Citizen;
import vyach.courseworkmaythegodhelpme.entity.Status;
import vyach.courseworkmaythegodhelpme.entity.Task;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findAllByGiver(Citizen citizen);
    List<Task> findAllByStatus(Status status);

    @Modifying
    @Query("update Task t set t.status=?2 where t.id=?1")
    void setTaskStatus(Long taskId, Status status);
}
