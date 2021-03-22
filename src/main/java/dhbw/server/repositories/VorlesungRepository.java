package dhbw.server.repositories;

import dhbw.server.entities.Nutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VorlesungRepository extends JpaRepository<Nutzer, Integer> {

    @Query("SELECT vor_name FROM Vorlesung WHERE vor_id = ?1")
    public Vorlesung findNameById(Integer id);
}

