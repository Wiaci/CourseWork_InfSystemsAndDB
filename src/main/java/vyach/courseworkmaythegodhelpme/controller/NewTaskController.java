package vyach.courseworkmaythegodhelpme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import vyach.courseworkmaythegodhelpme.entity.Citizen;
import vyach.courseworkmaythegodhelpme.entity.House;
import vyach.courseworkmaythegodhelpme.repository.CitizenRepo;
import vyach.courseworkmaythegodhelpme.repository.TaskTypeRepo;
import vyach.courseworkmaythegodhelpme.service.TaskService;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("citizen")
public class NewTaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CitizenRepo citizenRepo;

    private Citizen citizen;

    @GetMapping("/newTask")
    public String newTask(Model model) {
        if (model.getAttribute("citizen") == null) {
            return "redirect:/";
        }
        citizen = (Citizen) model.getAttribute("citizen");
        if (citizen.getId() == 14L) return "redirect:/";
        citizen = citizenRepo.getById(citizen.getId());
        model.addAttribute("citizen", citizen);
        model.addAttribute("money", Utils.round(citizen.getMoney()));
        model.addAttribute("taskTypes", taskService.getTaskTypes());
        List<House> houseList = new ArrayList<>();
        houseList.add(citizen.getHouse());
        model.addAttribute("houses", houseList);
        model.addAttribute("errorMessageHidden", true);
        model.addAttribute("cost", taskService.getTaskCost(1L, citizen));
        return "newTask";
    }

    @PostMapping("/newTask/typeSelect")
    public String typeSelect(@RequestParam Long typeId, Model model) {
        List<House> houseList = taskService.getHousesByTaskType(typeId, citizen);
        model.addAttribute("houses", houseList);
        model.addAttribute("money", Utils.round(citizen.getMoney()));
        model.addAttribute("taskTypes", taskService.getTaskTypes());
        model.addAttribute("errorMessageHidden", true);
        model.addAttribute("cost", taskService.getTaskCost(typeId, citizen));
        return "newTask";
    }

    @PostMapping("/newTask/add")
    public String addNewTask(@RequestParam Long typeId,
                             @RequestParam(required = false) Long houseId,
                             @RequestParam String comment,
                             Model model) {
        if (houseId == null) return "redirect:/newTask";
        double taskCost = taskService.getTaskCost(typeId, citizen);
        if (citizen.getMoney() - taskCost < -0.01) {
            model.addAttribute("errorMessageHidden", false);
            model.addAttribute("diff", Utils.round(taskCost - citizen.getMoney()));
            List<House> houseList = taskService.getHousesByTaskType(typeId, citizen);
            model.addAttribute("houses", houseList);
            model.addAttribute("money", Utils.round(citizen.getMoney()));
            model.addAttribute("taskTypes", taskService.getTaskTypes());
            model.addAttribute("cost", taskCost);
            return "newTask";
        } else {
            taskService.addNewTask(typeId, houseId, comment, citizen);
            citizen.setMoney(Utils.round(citizen.getMoney() - taskCost));
            model.addAttribute("citizen", citizen);
            return "redirect:/tasks";
        }
    }
}
