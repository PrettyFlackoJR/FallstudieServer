package dhbw.server.repositories;

import dhbw.server.entities.Vorlesung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VorlesungRepository extends JpaRepository<Vorlesung, Integer> {

    @Query("SELECT vor_id FROM Vorlesung WHERE vor_name = ?1")
    Integer getIdByName(String vorlesung);

    @Query("SELECT vor_name FROM Vorlesung WHERE vor_id = ?1")
    String findNameByVvnId(Integer vvnId);
}

