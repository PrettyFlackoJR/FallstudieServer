package dhbw.server.repositories;

import dhbw.server.entities.Nutzer;
import dhbw.server.entities.Vorlesung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VorlesungRepository extends JpaRepository<Vorlesung, Integer> {

}

