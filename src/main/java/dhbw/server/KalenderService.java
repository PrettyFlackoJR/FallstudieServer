package dhbw.server;

import dhbw.server.entities.Nutzer;
import dhbw.server.entities.Vorlesung;
import dhbw.server.repositories.NutzerRepository;
import dhbw.server.repositories.VorlesungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class KalenderService {
    @Autowired
    private VorlesungRepository vorlesungRepository;

}
