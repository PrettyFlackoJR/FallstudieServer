package dhbw.server.services;


import dhbw.server.entities.Kurs;
import dhbw.server.entities.Vorlesung;
import dhbw.server.entities.Vorlesung_Von_Nutzer;
import dhbw.server.helper.Vorlesung_Namen;
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

    /**
     * Liefert den Kurs mit dem angegebenen Namen.
     * @param kurs
     * @return
     */
    public Kurs getKursByName(String kurs) {
        Kurs kurse = kursRepository.findKursByName(kurs);
        return kurse;
    }

    /**
     * Liefert Liste mit den Vorlesungen des derzeit eingeloggten Users,
     * mit dem angegebenen Kurs.
     * @param kurs
     * @return
     */
    public ArrayList<Vorlesung_Von_Nutzer> getVvns(String kurs) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();
        int nutzerId = userService.getUserId(currentPrincipalEmail);

        Integer kursId = kursRepository.findKursIdByName(kurs);
        ArrayList<Vorlesung_Von_Nutzer> vvnIds = vorlesungVonNutzerRepository.findByNutzerId(nutzerId, kursId);

        return vvnIds;
    }

    /**
     * Liefert eine Liste, welche die Vorlesungen des derzeit eingeloggten Nutzers,
     * mit dem angegebenen Kurs, sowie der Vorlesungsnamen enthält.
     * @param kurs
     * @return
     */
    public ArrayList<Vorlesung_Namen> getVVNNamen(String kurs) {
        ArrayList<Vorlesung_Von_Nutzer> vvns = vorlesungsService.getVvns(kurs);
        ArrayList<Vorlesung_Namen> vvn_namen = new ArrayList<>();

        for (Vorlesung_Von_Nutzer vvn : vvns) {
            Integer id = vvn.getVvn_vor_id();
            Optional<Vorlesung> vorlesung = vorlesungRepository.findById(id);
            vvn_namen.add(new Vorlesung_Namen(vvn.getVvn_id(), vorlesung.get().getVor_name()));
        }

        return vvn_namen;
    }

    /**
     * Liefert die übrigen Stunden der Vorlesung mit der angegebenen ID.
     * @param vvnId
     * @return
     */
    public Double getHoursOfVorlesung(Integer vvnId) {
        return vorlesungVonNutzerRepository.findById(vvnId).get().getVvn_stnd();
    }

    /**
     * Liefert alle Vorlesungen zurück.
     * @return
     */
    public List<Vorlesung> getAllVorNamen() {
        return vorlesungRepository.findAll();
    }

    /**
     * Liefert den Namen der angegebenen Vorlesung zurück.
     * @param vvn_id
     * @return
     */
    public String getVorName(Integer vvn_id) {
        Optional<Vorlesung_Von_Nutzer> vorlesungVonNutzer = vorlesungVonNutzerRepository.findById(vvn_id);
        Optional<Vorlesung> vorlesung = vorlesungRepository.findById(vorlesungVonNutzer.get().getVvn_vor_id());
        return vorlesung.get().getVor_name();
    }

    /**
     * Liefert das Kurs-Objekt anhand der angegebenen Kurs ID.
     * @param kurs_id
     * @return
     */
    public Optional<Kurs> getKursById(Integer kurs_id) {
        return kursRepository.findById(kurs_id);
    }
}
