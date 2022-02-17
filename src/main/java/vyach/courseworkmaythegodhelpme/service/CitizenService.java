package vyach.courseworkmaythegodhelpme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vyach.courseworkmaythegodhelpme.entity.Citizen;
import vyach.courseworkmaythegodhelpme.repository.CitizenRepo;

@Service
public class CitizenService {

    @Autowired
    private CitizenRepo citizenRepo;

    public Citizen getCitizenByName(String name) {
        return citizenRepo.getCitizenByName(name);
    }

}
