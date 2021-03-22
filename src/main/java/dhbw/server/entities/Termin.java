package dhbw.server.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "termin")
public class Termin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ter_id;

    @Column
    private LocalDate ter_datum;

    @Column
    private Integer ter_vvn_id;

    @Column
    private LocalTime ter_start;

    @Column
    private LocalTime ter_ende;

    @Column
    private Integer ter_kurs_id;

    public Integer getTer_id() {
        return ter_id;
    }

    public void setTer_id(Integer ter_id) {
        this.ter_id = ter_id;
    }

    public LocalDate getTer_datum() {
        return ter_datum;
    }

    public void setTer_datum(LocalDate ter_datum) {
        this.ter_datum = ter_datum;
    }

    public Integer getTer_vvn_id() {
        return ter_vvn_id;
    }

    public void setTer_vvn_id(Integer ter_vvn_id) {
        this.ter_vvn_id = ter_vvn_id;
    }

    public LocalTime getTer_start() {
        return ter_start;
    }

    public void setTer_start(LocalTime ter_start) {
        this.ter_start = ter_start;
    }

    public LocalTime getTer_ende() {
        return ter_ende;
    }

    public void setTer_ende(LocalTime ter_ende) {
        this.ter_ende = ter_ende;
    }

    public Integer getTer_kurs_id() {
        return ter_kurs_id;
    }

    public void setTer_kurs_id(Integer ter_kurs_id) {
        this.ter_kurs_id = ter_kurs_id;
    }
}
