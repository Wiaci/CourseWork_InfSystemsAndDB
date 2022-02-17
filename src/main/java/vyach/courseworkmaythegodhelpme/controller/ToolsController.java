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
import vyach.courseworkmaythegodhelpme.service.ToolService;

@Controller
@SessionAttributes("citizen")
public class ToolsController {

    private Citizen citizen;

    @Autowired
    private CitizenRepo citizenRepo;

    @Autowired
    private ToolService toolService;

    @GetMapping("/tools")
    public String tools(Model model) {
        if (model.getAttribute("citizen") == null) {
            return "redirect:/";
        }
        citizen = (Citizen) model.getAttribute("citizen");
        citizen = citizenRepo.getById(citizen.getId());
        model.addAttribute("citizen", citizen);
        model.addAttribute("money", Utils.round(citizen.getMoney()));

        model.addAttribute("tools", toolService.getAllTools());
        model.addAttribute("citizenId", citizen.getId());
        return "tools";
    }

    @PostMapping("/tools/take")
    public String takeTool(@RequestParam Long toolId, Model model) {
        toolService.takeTool(toolId, citizen);
        return "redirect:/tools";
    }

    @PostMapping("/tools/leave")
    public String leaveTool(@RequestParam Long toolId, Model model) {
        toolService.leaveTool(toolId);
        return "redirect:/tools";
    }
}
