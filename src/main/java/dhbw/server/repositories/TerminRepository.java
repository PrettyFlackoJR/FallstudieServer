package dhbw.server.repositories;

import dhbw.server.entities.Termin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public interface TerminRepository extends JpaRepository<Termin, Integer> {

    @Query("SELECT t FROM Termin t WHERE t.ter_vvn_id = ?1")
    public ArrayList<Termin> findAllByUserId(int id);

}
