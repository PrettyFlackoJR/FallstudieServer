package dhbw.server.services;

import dhbw.server.entities.*;
import dhbw.server.repositories.KursRepository;
import dhbw.server.repositories.Kurs_Von_NutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KursService {

    @Autowired
    UserService userService;
    @Autowired
    Kurs_Von_NutzerRepository kurs_von_nutzerRepository;
    @Autowired
    KursRepository kursRepository;

    public ArrayList<Kurs_Von_Nutzer> getKvns() {

        // Nutzer ID mit E-Mail holen
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();
        int nutzerId = userService.getUserId(currentPrincipalEmail);

        // VVN IDs mit Nutzer ID und Kurs ID holen
        ArrayList<Kurs_Von_Nutzer> kvnIds = kurs_von_nutzerRepository.findByNutzerId(nutzerId);
        return kvnIds;
    }
    public List<Kurs> getKurse(Integer kvns) {
        List<Kurs> kurse = kursRepository.findByKvns(kvns);
        return kurse;
    }
    public ArrayList<Kurs_Namen> getKursNamen() {
        ArrayList<Kurs_Von_Nutzer> kvns = getKvns();
        List<Kurs> kurse = new ArrayList<>();
        for (Kurs_Von_Nutzer kvn: kvns) {
            Integer id = kvn.getKvn_kurs_id();
            kurse = getKurse(id);

        }
        ArrayList<Kurs_Namen> kurs_namen = new ArrayList<>();
        for (Kurs_Von_Nutzer kvn: kvns) {
            for (Kurs kurs: kurse) {
                if (kvn.getKvn_kurs_id() == kurs.getKurs_id()) {
                    Kurs_Namen abc = new Kurs_Namen(kvn.getKvn_id(), kurs.getKurs_name());
                    kurs_namen.add(abc);
                }
            }
        }
        System.out.println(kurs_namen);
        return kurs_namen;
    }
    public List<Kurs> getAlleKurseMitNamen() {
        return kursRepository.findAll();
    }
}
