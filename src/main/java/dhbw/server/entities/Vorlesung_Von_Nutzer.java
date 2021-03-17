package dhbw.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "vorlesungvonnutzer")
public class Vorlesung_Von_Nutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vor_von_nut_id;

    @Column
    private Integer vor_von_nut_nut_id;

    @Column
    private Integer vor_von_nut_vol_id;

    public Integer getVor_von_nut_id() {
        return vor_von_nut_id;
    }

    public void setVor_von_nut_id(Integer vor_von_nut_id) {
        this.vor_von_nut_id = vor_von_nut_id;
    }

    public Integer getVor_von_nut_nut_id() {
        return vor_von_nut_nut_id;
    }

    public void setVor_von_nut_nut_id(Integer vor_von_nut_nut_id) {
        this.vor_von_nut_nut_id = vor_von_nut_nut_id;
    }

    public Integer getVor_von_nut_vol_id() {
        return vor_von_nut_vol_id;
    }

    public void setVor_von_nut_vol_id(Integer vor_von_nut_vol_id) {
        this.vor_von_nut_vol_id = vor_von_nut_vol_id;
    }
}
