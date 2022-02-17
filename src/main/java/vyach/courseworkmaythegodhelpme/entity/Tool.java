package vyach.courseworkmaythegodhelpme.entity;

import javax.persistence.*;

@Entity
@Table(name = "tool")
public class Tool {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ToolType toolType;

    @ManyToOne
    private Citizen holder;

    @Column(name = "is_busy")
    private Boolean isBusy;

    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public Citizen getHolder() {
        return holder;
    }

    public void setHolder(Citizen holder) {
        this.holder = holder;
    }

    public Boolean getBusy() {
        return isBusy;
    }

    public void setBusy(Boolean busy) {
        isBusy = busy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
