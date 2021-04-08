package dhbw.server.repositories;

import dhbw.server.entities.Kurs_Von_Nutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public interface Kurs_Von_NutzerRepository extends JpaRepository<Kurs_Von_Nutzer, Integer> {

    @Query("SELECT k FROM Kurs_Von_Nutzer k WHERE k.kvn_nut_id = ?1")
    ArrayList<Kurs_Von_Nutzer> findByNutzerId(Integer nutzerId);
}
