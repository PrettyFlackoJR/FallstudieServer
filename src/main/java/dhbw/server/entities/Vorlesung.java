package dhbw.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "vorlesung")
public class Vorlesung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vor_id;

    @Column
    private String vor_name;

    @Column
    private String vor_kuerzel;

    public Integer getVor_id() {
        return vor_id;
    }

    public void setVor_id(Integer vor_id) {
        this.vor_id = vor_id;
    }

    public String getVor_name() {
        return vor_name;
    }

    public void setVor_name(String vor_name) {
        this.vor_name = vor_name;
    }

    public String getVor_kuerzel() {
        return vor_kuerzel;
    }

    public void setVor_kuerzel(String vor_kuerzel) {
        this.vor_kuerzel = vor_kuerzel;
    }
}
