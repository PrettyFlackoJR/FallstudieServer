package dhbw.server.repositories;

import dhbw.server.entities.Nutzer;
import dhbw.server.entities.Vorlesung;
import dhbw.server.entities.Vorlesung_Von_Nutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface VorlesungRepository extends JpaRepository<Vorlesung, Integer> {

    @Query("SELECT v FROM Vorlesung v WHERE v.vor_id = ?1")
    public ArrayList<Vorlesung> findByVvnId(Integer vvns);
}

