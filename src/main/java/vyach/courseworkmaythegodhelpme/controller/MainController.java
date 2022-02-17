package vyach.courseworkmaythegodhelpme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import vyach.courseworkmaythegodhelpme.entity.Citizen;
import vyach.courseworkmaythegodhelpme.service.CitizenService;

@Controller
@SessionAttributes("citizen")
public class MainController {

    @Autowired
    private CitizenService citizenService;

    @GetMapping("/")
    public String home(Model model) {
        if (model.getAttribute("citizen") != null) {
            return "redirect:/tasks";
        }
        model.addAttribute("isCorrect", "true");
        return "enter";
    }

    @GetMapping("/exit")
    public String exit(Model model, SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return "redirect:/";
    }

    @PostMapping("/")
    public String enterName(@RequestParam String username, Model model) {
        Citizen citizen = citizenService.getCitizenByName(username);
        if (citizen == null) {
            model.addAttribute("isCorrect", "false");
            return "enter";
        }
        model.addAttribute("citizen", citizen);
        return "redirect:/tasks";
    }



}
