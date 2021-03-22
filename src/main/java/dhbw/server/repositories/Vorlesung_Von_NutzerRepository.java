package dhbw.server.repositories;

import dhbw.server.entities.Vorlesung_Von_Nutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface Vorlesung_Von_NutzerRepository extends JpaRepository<Vorlesung_Von_Nutzer, Integer> {

}
