package vyach.courseworkmaythegodhelpme.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "citizen")
public class Citizen {

    @Id
    private Long id;
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @ManyToOne(fetch = FetchType.EAGER)
    private House house;

    private Double money;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "garden_workers",
            joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "garden_id"))
    private List<Garden> gardens;

    @OneToOne(mappedBy = "citizen")
    private Statuette statuette;

    public Statuette getStatuette() {
        return statuette;
    }

    public void setStatuette(Statuette statuette) {
        this.statuette = statuette;
    }

    public List<Garden> getGardens() {
        return gardens;
    }

    public void setGardens(List<Garden> gardens) {
        this.gardens = gardens;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

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

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public String getShortName() {
        return shortName;
    }
}
