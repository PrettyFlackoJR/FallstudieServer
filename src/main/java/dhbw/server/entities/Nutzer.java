package dhbw.server.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "nutzer")
public class Nutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nut_id;

    @Column
    private String nut_vorname;

    @Column
    private String nut_nachname;

    @Column
    private String nut_email;

    @Column
    private String nut_anrede;

    @Column
    private String nut_titel;

    @Column
    private String nut_passwort;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "nutzer_roles",
            joinColumns = @JoinColumn(name = "nut_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Integer getNut_id() {
        return nut_id;
    }

    public void setNut_id(Integer nut_id) {
        this.nut_id = nut_id;
    }

    public String getNut_vorname() {
        return nut_vorname;
    }

    public void setNut_vorname(String nut_vorname) {
        this.nut_vorname = nut_vorname;
    }

    public String getNut_nachname() {
        return nut_nachname;
    }

    public void setNut_nachname(String nut_nachname) {
        this.nut_nachname = nut_nachname;
    }

    public String getNut_email() {
        return nut_email;
    }

    public void setNut_email(String nut_email) {
        this.nut_email = nut_email;
    }

    public String getNut_anrede() {
        return nut_anrede;
    }

    public void setNut_anrede(String nut_anrede) {
        this.nut_anrede = nut_anrede;
    }

    public String getNut_titel() {
        return nut_titel;
    }

    public void setNut_titel(String nut_titel) {
        this.nut_titel = nut_titel;
    }

    public String getNut_passwort() {
        return nut_passwort;
    }

    public void setNut_passwort(String nut_passwort) {
        this.nut_passwort = nut_passwort;
    }
}
