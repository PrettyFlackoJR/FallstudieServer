package dhbw.server.entities;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Objects;

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
    private Double vvn_stnd;

    @Column
    private Integer vvn_kurs_id;

    public Vorlesung_Von_Nutzer() {
    }

    public Vorlesung_Von_Nutzer(Integer vvn_nut_id, Integer vvn_vor_id, Double vvn_stnd, Integer vvn_kurs_id) {
        this.vvn_nut_id = vvn_nut_id;
        this.vvn_vor_id = vvn_vor_id;
        this.vvn_stnd = vvn_stnd;
        this.vvn_kurs_id = vvn_kurs_id;
    }

    public static Comparator<Vorlesung_Von_Nutzer> ascendingComp = new Comparator<Vorlesung_Von_Nutzer>() {
        @Override
        public int compare(Vorlesung_Von_Nutzer o1, Vorlesung_Von_Nutzer o2) {
            double stnd1 = o1.getVvn_stnd();
            double stnd2 = o2.getVvn_stnd();

            return (int) (stnd1 - stnd2);
        }
    };

    public static Comparator<Vorlesung_Von_Nutzer> descendingComp = new Comparator<Vorlesung_Von_Nutzer>() {
        @Override
        public int compare(Vorlesung_Von_Nutzer o1, Vorlesung_Von_Nutzer o2) {
            double stnd1 = o1.getVvn_stnd();
            double stnd2 = o2.getVvn_stnd();

            return (int) (stnd2 - stnd1);
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vorlesung_Von_Nutzer that = (Vorlesung_Von_Nutzer) o;
        return Objects.equals(vvn_nut_id, that.vvn_nut_id);
    }

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

    public Double getVvn_stnd() {
        return vvn_stnd;
    }

    public void setVvn_stnd(Double vvn_stnd) {
        this.vvn_stnd = vvn_stnd;
    }

    public Integer getVvn_kurs_id() {
        return vvn_kurs_id;
    }

    public void setVvn_kurs_id(Integer vvn_kurs_id) {
        this.vvn_kurs_id = vvn_kurs_id;
    }
}
