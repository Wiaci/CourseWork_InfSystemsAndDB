package vyach.courseworkmaythegodhelpme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vyach.courseworkmaythegodhelpme.entity.Citizen;


@Repository
public interface CitizenRepo extends JpaRepository<Citizen, Long> {
    Citizen getCitizenByName(String name);

    @Modifying
    @Query("update Citizen c set c.money=?2 where c.id=?1")
    void setCitizenMoney(Long id, Double money);
}
