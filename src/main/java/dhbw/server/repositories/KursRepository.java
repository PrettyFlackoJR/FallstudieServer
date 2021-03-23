package dhbw.server.repositories;

import dhbw.server.entities.Kurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface KursRepository extends JpaRepository<Kurs, Integer> {

    @Query("SELECT kurs_id FROM Kurs WHERE kurs_name = ?1")
    public Integer findByKursName(String kurs);

}
