package dhbw.server.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "kurs")
public class Kurs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer kurs_id;

    @Column
    private String kurs_name;

    @Column
    private LocalDate kurs_start;

    @Column
    private LocalDate kurs_ende;

    public Integer getKurs_id() {
        return kurs_id;
    }

    public void setKurs_id(Integer kurs_id) {
        this.kurs_id = kurs_id;
    }

    public String getKurs_name() {
        return kurs_name;
    }

    public void setKurs_name(String kurs_name) {
        this.kurs_name = kurs_name;
    }

    public LocalDate getKurs_start() {
        return kurs_start;
    }

    public void setKurs_start(LocalDate kurs_start) {
        this.kurs_start = kurs_start;
    }

    public LocalDate getKurs_ende() {
        return kurs_ende;
    }

    public void setKurs_ende(LocalDate kurs_ende) {
        this.kurs_ende = kurs_ende;
    }
}
