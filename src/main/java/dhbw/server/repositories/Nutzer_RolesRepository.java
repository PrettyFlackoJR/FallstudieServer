package dhbw.server.repositories;

import dhbw.server.entities.Nutzer_Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public interface Nutzer_RolesRepository extends JpaRepository<Nutzer_Role, Integer> {

    @Query("SELECT n FROM Nutzer_Role n WHERE n.nut_id = ?1 AND n.role_id = 3")
    public Nutzer_Role findEditorByNutzerId(Integer nutzerId);

    @Query("SELECT n.role_id FROM Nutzer_Role n WHERE n.nut_id = ?1")
    public ArrayList<Integer> findRolesByNutzerId(Integer nutzerId);

    @Query("SELECT nut_id FROM Nutzer_Role WHERE role_id = 1")
    public ArrayList<Integer> findAllDozenten();

}
