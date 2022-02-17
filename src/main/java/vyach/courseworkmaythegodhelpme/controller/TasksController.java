package vyach.courseworkmaythegodhelpme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import vyach.courseworkmaythegodhelpme.entity.Citizen;
import vyach.courseworkmaythegodhelpme.entity.Task;
import vyach.courseworkmaythegodhelpme.repository.CitizenRepo;
import vyach.courseworkmaythegodhelpme.service.TaskService;
import vyach.courseworkmaythegodhelpme.service.ToolService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@SessionAttributes("citizen")
public class TasksController {

    private Citizen citizen;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ToolService toolService;

    @Autowired
    private CitizenRepo citizenRepo;

    private final Long GRACE_ID = 14L;

    @GetMapping("/tasks")
    public String tasks(@RequestParam(defaultValue = "all") String filter, Model model) {
        if (model.getAttribute("citizen") == null) {
            return "redirect:/";
        }
        updateCitizenInModel(model);
        model.addAttribute("money", Utils.round(citizen.getMoney()));
        List<Task> taskList = taskService.getAllTasksByFilter(filter, citizen);
        Map<Task, Boolean> taskMap = taskService.assignIfTaskExecutable(taskList);
        model.addAttribute("tasks", taskMap);
        if (taskList.isEmpty()) {
            model.addAttribute("noTaskMessageHidden", "false");
            model.addAttribute("tableHidden", "true");
        } else {
            model.addAttribute("noTaskMessageHidden", "true");
            model.addAttribute("tableHidden", "false");
        }

        model.addAttribute("citizenId", citizen.getId());
        return "tasks";
    }

    @PostMapping("/tasks/start")
    public String taskStart(@RequestParam Long taskId, Model model) {
        if (taskService.taskExecutable(taskId)) {
            updateCitizenInModel(model);
            taskService.changeStatus(taskId, 2L);
            toolService.takeNeededTool(taskId, citizen);
        }
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/finish")
    public String taskFinish(@RequestParam Long taskId, Model model) {
        updateCitizenInModel(model);
        taskService.changeStatus(taskId, 3L);
        toolService.leaveNeededTool(taskId, citizen);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/disapprove")
    public String taskDisapprove(@RequestParam Long taskId, Model model) {
        taskService.changeStatus(taskId, 1L);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/approve")
    public String taskApprove(@RequestParam Long taskId, Model model) {
        taskService.addTaskWageToGrace(taskId);
        taskService.deleteTask(taskId);
        return "redirect:/tasks";
    }

    private void updateCitizenInModel(Model model) {
        citizen = (Citizen) model.getAttribute("citizen");
        citizen = citizenRepo.getById(citizen.getId());
        model.addAttribute("citizen", citizen);
    }

}
