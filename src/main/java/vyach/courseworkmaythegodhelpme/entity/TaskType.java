package vyach.courseworkmaythegodhelpme.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "task_type")
public class TaskType {

    @Id
    private Long id;
    private String name;
    private double rate;

    @ManyToOne(fetch = FetchType.EAGER)
    @Nullable
    private ToolType toolType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }
}
