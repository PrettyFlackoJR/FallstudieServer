package dhbw.server.services;


import dhbw.server.entities.Vorlesung;
import dhbw.server.entities.Vorlesung_Von_Nutzer;
import dhbw.server.repositories.KursRepository;
import dhbw.server.repositories.VorlesungRepository;
import dhbw.server.repositories.Vorlesung_Von_NutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VorlesungsService {
    @Autowired
    private Vorlesung_Von_NutzerRepository vorlesungVonNutzerRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private VorlesungRepository vorlesungRepository;
    @Autowired
    private KursRepository kursRepository;


    public List<Vorlesung_Von_Nutzer> getVvns(String kurs) {
        List<Vorlesung_Von_Nutzer> vorlesungList =vorlesungVonNutzerRepository.findAll();

        // Nutzer ID mit E-Mail holen
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();
        int nutzerId = userService.getUserId(currentPrincipalEmail);

        // VVN IDs mit Nutzer ID und Kurs ID holen
        Integer kursId = kursRepository.findByKursName(kurs);
        ArrayList<Vorlesung_Von_Nutzer> vvnIds = vorlesungVonNutzerRepository.findByNutzerId(nutzerId, kursId);

        return vvnIds;
    }

    public List<Vorlesung> getVorlesungen(List<Vorlesung_Von_Nutzer> vvns) {
        List<Vorlesung> vorlesungen = vorlesungRepository.findByVvnId(vvns);
        return vorlesungen;
    }
}
