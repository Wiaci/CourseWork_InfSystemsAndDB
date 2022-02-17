package vyach.courseworkmaythegodhelpme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import vyach.courseworkmaythegodhelpme.entity.Citizen;
import vyach.courseworkmaythegodhelpme.repository.CitizenRepo;
import vyach.courseworkmaythegodhelpme.service.StatuetteService;

@Controller
@SessionAttributes("citizen")
public class StatuettesController {

    @Autowired
    private StatuetteService statuetteService;

    @Autowired
    private CitizenRepo citizenRepo;

    private Citizen citizen;

    @GetMapping("/statuettes")
    public String statuettes(Model model) {
        citizen = (Citizen) model.getAttribute("citizen");
        if (citizen == null) return "redirect:/";
        if (citizen.getId() != 14L) return "redirect:/tasks";
        model.addAttribute("money", Utils.round(citizen.getMoney()));
        model.addAttribute("statuettes", statuetteService.getAll());
        return "statuettes";
    }

    @PostMapping("/statuettes/buy")
    public String buy(@RequestParam Long statuetteId, Model model) {
        updateCitizenInModel(model);
        statuetteService.buyStatuette(statuetteId, citizen);
        return "redirect:/statuettes";
    }

    private void updateCitizenInModel(Model model) {
        citizen = (Citizen) model.getAttribute("citizen");
        citizen = citizenRepo.getById(citizen.getId());
        model.addAttribute("citizen", citizen);
    }
}
