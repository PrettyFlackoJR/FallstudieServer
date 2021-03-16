package dhbw.server.entities;

import java.io.Serializable;
import java.util.Objects;

public class Nutzer_RoleId implements Serializable {

    private Integer nut_id;
    private Integer role_id;

    public Nutzer_RoleId() {}

    public Nutzer_RoleId(Integer nut_id, Integer role_id) {
        this.nut_id = nut_id;
        this.role_id = role_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nut_id, role_id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Nutzer_RoleId nutzer_roleId = (Nutzer_RoleId) obj;
        return nutzer_roleId.equals(nutzer_roleId.nut_id) &&
                nutzer_roleId.equals(nutzer_roleId.role_id);
    }
}
