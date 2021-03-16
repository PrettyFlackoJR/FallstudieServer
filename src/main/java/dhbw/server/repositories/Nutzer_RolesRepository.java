package dhbw.server.repositories;

import dhbw.server.entities.Nutzer_Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface Nutzer_RolesRepository extends JpaRepository<Nutzer_Role, Integer> {

}
