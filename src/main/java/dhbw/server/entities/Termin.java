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
    private Integer ter_vor_von_nut_id;

    @Column
    private LocalTime ter_start;

    @Column
    private LocalTime ter_ende;

    @Column
    private Integer ter_kurs_id;

}
