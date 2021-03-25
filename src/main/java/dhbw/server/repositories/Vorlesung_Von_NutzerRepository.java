package dhbw.server.repositories;

import dhbw.server.entities.Vorlesung_Von_Nutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public interface Vorlesung_Von_NutzerRepository extends JpaRepository<Vorlesung_Von_Nutzer, Integer> {

    @Query("SELECT vvn_id FROM Vorlesung_Von_Nutzer WHERE vvn_nut_id = ?1 AND vvn_kurs_id = ?2")
    public ArrayList<Integer> findIdsByNutzerId(Integer nutzerId, Integer kursId);

    @Query("SELECT v FROM Vorlesung_Von_Nutzer v WHERE v.vvn_nut_id = ?1 AND v.vvn_kurs_id = ?2")
    public ArrayList<Vorlesung_Von_Nutzer> findByNutzerId(Integer nutzerId, Integer kursId);

    @Query("SELECT vvn_stnd FROM Vorlesung_Von_Nutzer WHERE vvn_nut_id = ?1 AND vvn_vor_id = ?2")
    float getStundenVonVVNbyId(Integer id, Integer v_id);
}
