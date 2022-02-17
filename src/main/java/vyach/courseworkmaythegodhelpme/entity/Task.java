package vyach.courseworkmaythegodhelpme.entity;

import javax.persistence.*;

@Entity
@Table(name = "task")
public class Task {

    public Task() {
    }

    public Task(TaskType taskType, Citizen giver, House house, Double wage, String comment, Status status) {
        this.taskType = taskType;
        this.giver = giver;
        this.house = house;
        this.wage = wage;
        this.comment = comment;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private TaskType taskType;

    @ManyToOne
    private Citizen giver;

    @ManyToOne
    private House house;

    @ManyToOne
    private Status status;

    private Double wage;
    String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Citizen getGiver() {
        return giver;
    }

    public void setGiver(Citizen giver) {
        this.giver = giver;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getWage() {
        return wage;
    }

    public void setWage(Double wage) {
        this.wage = wage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
