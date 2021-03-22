package dhbw.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "vorlesungvonnutzer")
public class Vorlesung_Von_Nutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vvn_id;

    @Column
    private Integer vvn_nut_id;

    @Column
    private Integer vvn_vor_id;

    @Column
    private Float vvn_stnd;

    public Integer getVvn_id() {
        return vvn_id;
    }

    public void setVvn_id(Integer vvn_id) {
        this.vvn_id = vvn_id;
    }

    public Integer getVvn_nut_id() {
        return vvn_nut_id;
    }

    public void setVvn_nut_id(Integer vvn_nut_id) {
        this.vvn_nut_id = vvn_nut_id;
    }

    public Integer getVvn_vor_id() {
        return vvn_vor_id;
    }

    public void setVvn_vor_id(Integer vvn_vol_id) {
        this.vvn_vor_id = vvn_vol_id;
    }

    public Float getVvn_stnd() {
        return vvn_stnd;
    }

    public void setVvn_stnd(Float vvn_stnd) {
        this.vvn_stnd = vvn_stnd;
    }
}
