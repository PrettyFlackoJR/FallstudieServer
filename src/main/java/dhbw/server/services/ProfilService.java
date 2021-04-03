package dhbw.server.services;

import dhbw.server.repositories.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfilService {

    @Autowired
    ProfilRepository profilRepository;
}
