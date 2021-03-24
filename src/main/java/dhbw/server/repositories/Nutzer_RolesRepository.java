package dhbw.server.repositories;

import dhbw.server.entities.Nutzer_Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface Nutzer_RolesRepository extends JpaRepository<Nutzer_Role, Integer> {

    @Query("SELECT n FROM Nutzer_Role n WHERE n.nut_id = ?1 AND n.role_id = 3")
    public Nutzer_Role findByNutzerId(Integer nutzerId);

}
