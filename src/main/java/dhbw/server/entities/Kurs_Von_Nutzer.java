package dhbw.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "kursvonnutzer")
public class Kurs_Von_Nutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer kurs_von_nut_id;

    @Column
    private Integer kurs_von_nut_nut_id;

    @Column
    private Integer kurs_von_nut_kurs_id;

    public Integer getKurs_von_nut_id() {
        return kurs_von_nut_id;
    }

    public void setKurs_von_nut_id(Integer kurs_von_nut_id) {
        this.kurs_von_nut_id = kurs_von_nut_id;
    }

    public Integer getKurs_von_nut_nut_id() {
        return kurs_von_nut_nut_id;
    }

    public void setKurs_von_nut_nut_id(Integer kurs_von_nut_nut_id) {
        this.kurs_von_nut_nut_id = kurs_von_nut_nut_id;
    }

    public Integer getKurs_von_nut_kurs_id() {
        return kurs_von_nut_kurs_id;
    }

    public void setKurs_von_nut_kurs_id(Integer kurs_von_nut_kurs_id) {
        this.kurs_von_nut_kurs_id = kurs_von_nut_kurs_id;
    }
}
