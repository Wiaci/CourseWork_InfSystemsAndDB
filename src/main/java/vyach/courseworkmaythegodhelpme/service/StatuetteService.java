package vyach.courseworkmaythegodhelpme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vyach.courseworkmaythegodhelpme.entity.Citizen;
import vyach.courseworkmaythegodhelpme.entity.Statuette;
import vyach.courseworkmaythegodhelpme.repository.CitizenRepo;
import vyach.courseworkmaythegodhelpme.repository.StatuetteRepo;

import java.util.List;

@Service
public class StatuetteService {

    @Autowired
    private StatuetteRepo statuetteRepo;

    @Autowired
    private CitizenRepo citizenRepo;

    public List<Statuette> getAll() {
        return statuetteRepo.findAll();
    }

    public void buyStatuette(Long statuetteId, Citizen citizen) {
        Statuette statuette = statuetteRepo.getById(statuetteId);
        if (citizen.getMoney() < statuette.getCost()) return;
        statuette.setBought(true);
        citizen.setMoney(citizen.getMoney() - statuette.getCost());
        statuetteRepo.save(statuette);
        citizenRepo.save(citizen);
    }

}
