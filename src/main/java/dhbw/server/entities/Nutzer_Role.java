package dhbw.server.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "nutzer_roles")
@IdClass(Nutzer_RoleId.class)
public class Nutzer_Role implements Serializable {

    @Id
    private Integer nut_id;

    @Id
    private Integer role_id;

    public Nutzer_Role() {}

    public Nutzer_Role(Integer nut_id, Integer role_id) {
        this.nut_id = nut_id;
        this.role_id = role_id;
    }

    public Integer getNut_id() {
        return nut_id;
    }

    public void setNut_id(Integer nut_id) {
        this.nut_id = nut_id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }
}
