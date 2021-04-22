package dhbw.server.services;

import dhbw.server.entities.Kurs;
import dhbw.server.entities.Kurs_Von_Nutzer;
import dhbw.server.exceptions.TimeframeException;
import dhbw.server.helper.KursZeitraum;
import dhbw.server.helper.Kurs_Namen;
import dhbw.server.repositories.KursRepository;
import dhbw.server.repositories.Kurs_Von_NutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class KursService {

    @Autowired
    UserService userService;
    @Autowired
    Kurs_Von_NutzerRepository kursVonNutzerRepository;
    @Autowired
    KursRepository kursRepository;

    public ArrayList<Kurs_Von_Nutzer> getKvns() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();
        int nutzerId = userService.getUserId(currentPrincipalEmail);

        ArrayList<Kurs_Von_Nutzer> kvns = kursVonNutzerRepository.findByNutzerId(nutzerId);
        return kvns;
    }

    public Kurs getKurs(Integer kursId) {
        return kursRepository.findByKursId(kursId);
    }

    public Kurs getKursByName(String name) {
        return kursRepository.findKursByName(name);
    }

    public ArrayList<Kurs_Namen> getKursNamen() {
        ArrayList<Kurs_Von_Nutzer> kvns = getKvns();
        List<Kurs> kurse = new ArrayList<>();
        for (Kurs_Von_Nutzer kvn : kvns) {
            Integer id = kvn.getKvn_kurs_id();
            kurse.add(getKurs(id));
        }
        ArrayList<Kurs_Namen> kurs_namen = new ArrayList<>();
        for (Kurs_Von_Nutzer kvn : kvns) {
            for (Kurs kurs : kurse) {
                if (kvn.getKvn_kurs_id() == kurs.getKurs_id()) {
                    Kurs_Namen kursNamen = new Kurs_Namen(kvn.getKvn_id(), kurs.getKurs_name());
                    kurs_namen.add(kursNamen);
                }
            }
        }

        return kurs_namen;
    }

    public List<Kurs> getAllKurseWithNames() {
        return kursRepository.findAll();
    }

    public void setPeriod(KursZeitraum kursZeitraum) throws TimeframeException {
        if (kursZeitraum == null || kursZeitraum.getStart() == null
                || kursZeitraum.getEnd() == null || kursZeitraum.getKurs() == null) {
            throw new TimeframeException("Bitte füllen Sie alle Felder aus.");
        }
        Kurs kurs = getKursByName(kursZeitraum.getKurs());
        if (!kursZeitraum.getStart().isAfter(LocalDate.now())) {
            throw new TimeframeException("Der Startpunkt des Semesters muss sich in der Zukunft befinden.");
        } else if (!kursZeitraum.getEnd().isAfter(kursZeitraum.getStart())) {
            throw new TimeframeException("Das Enddatum des Semesters muss nach dem Startdatum liegen.");
        }
        kurs.setKurs_start(kursZeitraum.getStart());
        kurs.setKurs_ende(kursZeitraum.getEnd());

        kursRepository.save(kurs);
    }
}
