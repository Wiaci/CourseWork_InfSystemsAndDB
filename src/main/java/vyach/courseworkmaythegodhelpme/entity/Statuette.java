package vyach.courseworkmaythegodhelpme.entity;

import javax.persistence.*;

@Entity
@Table(name = "statuette")
public class Statuette {

    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "citizen_id", referencedColumnName = "id")
    private Citizen citizen;

    private Double cost;

    @Column(name = "is_bought")
    private Boolean isBought;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Boolean getBought() {
        return isBought;
    }

    public void setBought(Boolean bought) {
        isBought = bought;
    }
}
