package vyach.courseworkmaythegodhelpme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vyach.courseworkmaythegodhelpme.entity.Citizen;
import vyach.courseworkmaythegodhelpme.entity.Tool;
import vyach.courseworkmaythegodhelpme.entity.ToolType;

@Repository
public interface ToolRepo extends JpaRepository<Tool, Long> {
    boolean existsByToolTypeAndIsBusy(ToolType toolType, Boolean isBusy);
    boolean existsByHolder_Id(Long holder_id);
    Tool findFirstByToolTypeAndIsBusy(ToolType toolType, Boolean isBusy);
    Tool findFirstByToolTypeAndHolder(ToolType toolType, Citizen holder);

    @Modifying
    @Query("update Tool t set t.isBusy=true, t.holder=?2 where t.id=?1")
    void takeTool(Long toolId, Citizen citizen);

    @Modifying
    @Query("update Tool t set t.isBusy=false, t.holder=NULL where t.id=?1")
    void leaveTool(Long toolId);
}
