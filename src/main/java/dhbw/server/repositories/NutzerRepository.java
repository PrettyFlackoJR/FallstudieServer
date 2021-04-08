package dhbw.server.repositories;

import dhbw.server.entities.Nutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface NutzerRepository extends JpaRepository<Nutzer, Integer> {

    @Query("SELECT n FROM Nutzer n WHERE n.nut_email = ?1")
    Nutzer findByEmail(String email);

    @Query("SELECT nut_id FROM Nutzer WHERE nut_email = ?1")
    Integer findIdByEmail(String email);
}
