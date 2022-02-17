package vyach.courseworkmaythegodhelpme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vyach.courseworkmaythegodhelpme.entity.*;
import vyach.courseworkmaythegodhelpme.repository.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    @Autowired
    private TaskTypeRepo taskTypeRepo;

    @Autowired
    private HouseRepo houseRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private CitizenRepo citizenRepo;

    @Autowired
    private ToolService toolService;

    private final Long GRACE_ID = 14L;

    public List<TaskType> getTaskTypes() {
        return taskTypeRepo.findAll();
    }

    public List<House> getHousesByTaskType(Long taskTypeId, Citizen citizen) {
        if (taskTypeId == 1L || taskTypeId == 2L) {
            List<House> oneHouse = new ArrayList<>();
            oneHouse.add(citizen.getHouse());
            return oneHouse;
        } else {
            return getHousesFromGardensOfCitizen(citizen);
        }
    }

    public double getTaskCost(Long taskTypeId, Citizen citizen) {
        TaskType type = taskTypeRepo.getById(taskTypeId);
        double cost = type.getRate();
        if (statuetteBought(citizen)) {
            cost *= 1.5;
        }
        return cost;
    }

    public List<Task> getAllTasksByFilter(String filter, Citizen citizen) {
        switch (filter) {
            case "my": return taskRepo.findAllByGiver(citizen);
            case "new": return taskRepo.findAllByStatus(statusRepo.getById(1L));
            case "exec": return taskRepo.findAllByStatus(statusRepo.getById(2L));
            case "wait": return taskRepo.findAllByStatus(statusRepo.getById(3L));
            default: return taskRepo.findAll();
        }
    }

    @Transactional
    public void addNewTask(Long taskTypeId, Long houseId, String comment, Citizen citizen) {
        TaskType type = taskTypeRepo.getById(taskTypeId);
        House house = houseRepo.getById(houseId);
        Status status = statusRepo.getById(1L);
        double taskCost = getTaskCost(taskTypeId, citizen);
        Task task = new Task(type, citizen, house, taskCost, comment, status);
        taskRepo.save(task);
        citizenRepo.setCitizenMoney(citizen.getId(), citizen.getMoney() - taskCost);
    }

    @Transactional
    public void changeStatus(Long taskId, Long statusId) {
        Status status = statusRepo.getById(statusId);
        taskRepo.setTaskStatus(taskId, status);
    }

    @Transactional
    public void addTaskWageToGrace(Long taskId) {
        Task task = taskRepo.getById(taskId);
        double money = task.getWage();
        Citizen grace = citizenRepo.getById(GRACE_ID);
        citizenRepo.setCitizenMoney(GRACE_ID, grace.getMoney() + money);
    }

    public void deleteTask(Long taskId) {
        taskRepo.deleteById(taskId);
    }

    public Map<Task, Boolean> assignIfTaskExecutable(List<Task> taskList) {
        Map<Task, Boolean> taskMap = new HashMap<>();
        for (Task task : taskList) {
            if (toolService.suchToolSpare(task.getTaskType().getToolType())) {
                taskMap.put(task, true);
            } else {
                taskMap.put(task, false);
            }
        }
        return taskMap;
    }

    public boolean taskExecutable(Long taskId) {
        Task task = taskRepo.getById(taskId);
        return toolService.suchToolSpare(task.getTaskType().getToolType());
    }

    private boolean statuetteBought(Citizen citizen) {
        return citizen.getStatuette().getBought();
    }

    private List<House> getHousesFromGardensOfCitizen(Citizen citizen) {
        List<Garden> gardens = citizen.getGardens();
        List<House> houses = new ArrayList<>();
        for (Garden garden : gardens) {
            houses.add(garden.getHouse());
        }
        return houses;
    }


}
