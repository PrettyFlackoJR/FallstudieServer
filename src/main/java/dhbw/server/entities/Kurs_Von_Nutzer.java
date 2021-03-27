package dhbw.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "kursvonnutzer")
public class Kurs_Von_Nutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer kvn_id;

    @Column
    private Integer kvn_nut_id;

    @Column
    private Integer kvn_kurs_id;

    public Kurs_Von_Nutzer() {
    }

    public Kurs_Von_Nutzer(Integer kvn_nut_id, Integer kvn_kurs_id) {
        this.kvn_nut_id = kvn_nut_id;
        this.kvn_kurs_id = kvn_kurs_id;
    }

    public Integer getKvn_id() {
        return kvn_id;
    }

    public void setKvn_id(Integer kvn_id) {
        this.kvn_id = kvn_id;
    }

    public Integer getKvn_nut_id() {
        return kvn_nut_id;
    }

    public void setKvn_nut_id(Integer kvn_nut_id) {
        this.kvn_nut_id = kvn_nut_id;
    }

    public Integer getKvn_kurs_id() {
        return kvn_kurs_id;
    }

    public void setKvn_kurs_id(Integer kvn_kurs_id) {
        this.kvn_kurs_id = kvn_kurs_id;
    }
}
