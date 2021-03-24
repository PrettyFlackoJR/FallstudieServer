package dhbw.server.services;


import dhbw.server.entities.Vorlesung;
import dhbw.server.entities.Vorlesung_Namen;
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
    private VorlesungsService vorlesungsService;
    @Autowired
    private VorlesungRepository vorlesungRepository;
    @Autowired
    private KursRepository kursRepository;


    public ArrayList<Vorlesung_Von_Nutzer> getVvns(String kurs) {

        // Nutzer ID mit E-Mail holen
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();
        int nutzerId = userService.getUserId(currentPrincipalEmail);

        // VVN IDs mit Nutzer ID und Kurs ID holen
        Integer kursId = kursRepository.findByKursName(kurs);
        System.out.println(kursId);
        ArrayList<Vorlesung_Von_Nutzer> vvnIds = vorlesungVonNutzerRepository.findByNutzerId(nutzerId, kursId);

        return vvnIds;
    }

    public ArrayList<Vorlesung> getVorlesungen(Integer vvns) {
        ArrayList<Vorlesung> vorlesungen = vorlesungRepository.findByVvnId(vvns);
        return vorlesungen;
    }
    public ArrayList<Vorlesung_Namen> getVVNNamen(String kurs) {
        ArrayList<Vorlesung_Von_Nutzer> vvns = vorlesungsService.getVvns(kurs);
        ArrayList<Vorlesung> vorlesungen = new ArrayList<>();
        for (Vorlesung_Von_Nutzer vvn: vvns) {
            Integer id = vvn.getVvn_vor_id();
            vorlesungen = vorlesungsService.getVorlesungen(id);

        }
        System.out.println("Vorlesung" + vorlesungen);
        ArrayList<Vorlesung_Namen> vvn_namen = new ArrayList<>();
        for (Vorlesung_Von_Nutzer vvn: vvns) {
            for (Vorlesung vorlesung: vorlesungen) {
                System.out.println(vvn.getVvn_vor_id() +" "+ vorlesung.getVor_id());
                if (vvn.getVvn_vor_id() == vorlesung.getVor_id()) {
                    System.out.println("if");
                    Vorlesung_Namen abc = new Vorlesung_Namen(vvn.getVvn_id(), vorlesung.getVor_name());
                    vvn_namen.add(abc);
                }
            }
        }
        return vvn_namen;
    }
}
