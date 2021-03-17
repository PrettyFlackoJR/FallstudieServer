package dhbw.server.repositories;

import dhbw.server.entities.Kurs_Von_Nutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface Kurs_Von_NutzerRepository extends JpaRepository<Kurs_Von_Nutzer, Integer> {
}
