package dhbw.server.repositories;

import dhbw.server.entities.Termin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TerminRepository extends JpaRepository<Termin, Integer> {

}
