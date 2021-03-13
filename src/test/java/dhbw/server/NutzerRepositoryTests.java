package dhbw.server;

import static org.assertj.core.api.Assertions.assertThat;

import dhbw.server.entities.Nutzer;
import dhbw.server.repositories.NutzerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class NutzerRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NutzerRepository repo;

    @Test
    public void testCreateUser() {
        Nutzer nutzer = new Nutzer();
        nutzer.setNut_email("fookumar@gmail.com");
        nutzer.setNut_passwort("ravi2020");
        nutzer.setNut_vorname("Foo");
        nutzer.setNut_nachname("Kumar");
        nutzer.setNut_anrede("Frau");
        nutzer.setNut_istadmin(true);
        nutzer.setNut_titel(null);

        Nutzer savedUser = repo.save(nutzer);

        Nutzer existUser = entityManager.find(Nutzer.class, savedUser.getNut_id());

        assertThat(nutzer.getNut_email()).isEqualTo(existUser.getNut_email());
    }

}
