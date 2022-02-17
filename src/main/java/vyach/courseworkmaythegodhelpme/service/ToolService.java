package vyach.courseworkmaythegodhelpme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vyach.courseworkmaythegodhelpme.entity.Citizen;
import vyach.courseworkmaythegodhelpme.entity.Task;
import vyach.courseworkmaythegodhelpme.entity.Tool;
import vyach.courseworkmaythegodhelpme.entity.ToolType;
import vyach.courseworkmaythegodhelpme.repository.TaskRepo;
import vyach.courseworkmaythegodhelpme.repository.ToolRepo;

import java.util.List;

@Service
public class ToolService {

    @Autowired
    private ToolRepo toolRepo;

    @Autowired
    private TaskRepo taskRepo;

    public List<Tool> getAllTools() {
        return toolRepo.findAll();
    }

    @Transactional
    public void takeTool(Long toolId, Citizen citizen) {
        toolRepo.takeTool(toolId, citizen);
    }

    @Transactional
    public void leaveTool(Long toolId) {
        toolRepo.leaveTool(toolId);
    }

    public boolean suchToolSpare(ToolType toolType) {
        if (toolType == null) return true;
        return toolRepo.existsByToolTypeAndIsBusy(toolType, false);
    }

    public void takeNeededTool(Long taskId, Citizen citizen) {
        Task task = taskRepo.getById(taskId);
        ToolType toolType = task.getTaskType().getToolType();
        if (toolType == null) return;
        Tool tool = toolRepo.findFirstByToolTypeAndIsBusy(toolType, false);
        tool.setBusy(true);
        tool.setHolder(citizen);
        toolRepo.save(tool);
    }

    public void leaveNeededTool(Long taskId, Citizen citizen) {
        Task task = taskRepo.getById(taskId);
        ToolType toolType = task.getTaskType().getToolType();
        if (toolType == null) return;
        Tool tool = toolRepo.findFirstByToolTypeAndHolder(toolType, citizen);
        tool.setBusy(false);
        tool.setHolder(null);
        toolRepo.save(tool);
    }

}
