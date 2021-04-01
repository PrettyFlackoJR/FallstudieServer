package dhbw.server.services;


import dhbw.server.entities.Kurs;
import dhbw.server.entities.Vorlesung;
import dhbw.server.helper.Vorlesung_Namen;
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
import java.util.Optional;

@Service
public class VorlesungsService {
    @Autowired
    private Vorlesung_Von_NutzerRepository vorlesungVonNutzerRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private VorlesungsService vorlesungsService;
    @Autowired
    private VorlesungRepository vorlesungRepository;
    @Autowired
    private KursRepository kursRepository;

    public Kurs getKursByName(String kurs) {
       Kurs kurse = kursRepository.findKursByName(kurs);
       return kurse;
    }
    public ArrayList<Vorlesung_Von_Nutzer> getVvns(String kurs) {

        // Nutzer ID mit E-Mail holen
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();
        int nutzerId = userService.getUserId(currentPrincipalEmail);

        // VVN IDs mit Nutzer ID und Kurs ID holen
        Integer kursId = kursRepository.findKursIdByName(kurs);
        ArrayList<Vorlesung_Von_Nutzer> vvnIds = vorlesungVonNutzerRepository.findByNutzerId(nutzerId, kursId);

        return vvnIds;
    }

    public ArrayList<Vorlesung_Namen> getVVNNamen(String kurs) {
        ArrayList<Vorlesung_Von_Nutzer> vvns = vorlesungsService.getVvns(kurs);
        ArrayList<Vorlesung_Namen> vvn_namen = new ArrayList<>();

        for (Vorlesung_Von_Nutzer vvn: vvns) {
            Integer id = vvn.getVvn_vor_id();
            Optional<Vorlesung> vorlesung = vorlesungRepository.findById(id);
            vvn_namen.add(new Vorlesung_Namen(vvn.getVvn_id(), vorlesung.get().getVor_name()));
        }

        return vvn_namen;
    }

    public Double getStundenVonVorlesung(Integer vvnId) {
        return vorlesungVonNutzerRepository.findById(vvnId).get().getVvn_stnd();
    }

    public List<Vorlesung> getAllVorNamen() {
        return vorlesungRepository.findAll();
    }

    public String getVorName(Integer vvn_id) {
        Optional<Vorlesung_Von_Nutzer> vorlesungVonNutzer = vorlesungVonNutzerRepository.findById(vvn_id);
        Optional<Vorlesung> vorlesung = vorlesungRepository.findById(vorlesungVonNutzer.get().getVvn_vor_id());
        return vorlesung.get().getVor_name();
    }

    public Optional<Kurs> getKursById(Integer kurs_id) {
        return kursRepository.findById(kurs_id);
    }
}
