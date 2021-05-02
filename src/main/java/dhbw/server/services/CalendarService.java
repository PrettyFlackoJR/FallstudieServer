package dhbw.server.services;

import dhbw.server.entities.Termin;
import dhbw.server.entities.Vorlesung;
import dhbw.server.entities.Vorlesung_Von_Nutzer;
import dhbw.server.exceptions.TerminException;
import dhbw.server.helper.Event;
import dhbw.server.repositories.KursRepository;
import dhbw.server.repositories.TerminRepository;
import dhbw.server.repositories.VorlesungRepository;
import dhbw.server.repositories.Vorlesung_Von_NutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.MINUTES;


@Service
public class CalendarService {

    @Autowired
    private TerminRepository terminRepository;
    @Autowired
    private Vorlesung_Von_NutzerRepository vorlesungVonNutzerRepository;
    @Autowired
    private VorlesungRepository vorlesungRepository;
    @Autowired
    private KursRepository kursRepository;
    @Autowired
    private UserService userService;

    /**
     * Liefert die Events für den Kalender.
     * @param kurs
     * @return
     */
    public ArrayList<Event> showCalendar(String kurs) {
        ArrayList<Termin> termine = getTermineOfCurrentCourse(kurs);
        ArrayList<Event> events = getEvents(termine, kurs);

        return events;
    }

    /**
     * Liefert alle Termine des angeforderten Kurses.
     * @param course
     * @return
     */
    public ArrayList<Termin> getTermineOfCurrentCourse(String course) {
        Integer courseId = kursRepository.findKursIdByName(course);
        return terminRepository.findAllByCourse(courseId);
    }

    /**
     * Fügt einen Termin in der Datenbank hinzu.
     * Dabei wird überprüft, ob der Zeitraum frei ist,
     * und ob die Stundenzahl nicht überschritten wird.
     * @param termin
     * @throws TerminException
     */
    @Transactional(rollbackFor = TerminException.class)
    public void addTermin(Termin termin) throws TerminException {
        if (terminExists(termin.getTer_datum(), termin.getTer_start(), termin.getTer_ende(),
                termin.getTer_kurs_id(), termin.getTer_id())){
            throw new TerminException("Dieser Zeitraum ist bereits belegt.");
        } else{
            try {
                terminRepository.save(termin);
            } catch (Exception e) {
                throw new TerminException("Bei der Eingabe Ihrer Daten gab es einen Fehler. Sie konnten nicht gespeichert werden.");
            }
            Optional<Vorlesung_Von_Nutzer> vorlesungVonNutzer = vorlesungVonNutzerRepository.
                    findById(termin.getTer_vvn_id());
            double delta = MINUTES.between(termin.getTer_start(), termin.getTer_ende()) / 60.0;
            if ((vorlesungVonNutzer.get().getVvn_stnd() - delta) < 0) {
                throw new TerminException("Bitte beachten Sie die Anzahl der verfügbaren Stunden.");
            }
            vorlesungVonNutzer.get().setVvn_stnd(vorlesungVonNutzer.get().getVvn_stnd() - delta);
            vorlesungVonNutzerRepository.save(vorlesungVonNutzer.get());
        }
    }

    /**
     * Modifiziert einen Termin in der Datenbank.
     * Dabei wird überprüft, ob der Zeitraum frei ist,
     * und ob die Stundenzahl nicht überschritten wird.
     * @param termin
     * @throws TerminException
     */
    @Transactional(rollbackFor = TerminException.class)
    public void modifyTermin(Termin termin) throws TerminException {
        if (terminExists(termin.getTer_datum(), termin.getTer_start(), termin.getTer_ende(),
                termin.getTer_kurs_id(), termin.getTer_id())) {
            throw new TerminException("Dieser Zeitraum ist bereits belegt.");
        }

        Termin oldTermin = terminRepository.findById(termin.getTer_id()).get();
        Vorlesung_Von_Nutzer vorlesungVonNutzer = vorlesungVonNutzerRepository.findById(termin.getTer_vvn_id()).get();

        double oldDelta = MINUTES.between(oldTermin.getTer_start(), oldTermin.getTer_ende()) / 60.0;
        double newDelta = MINUTES.between(termin.getTer_start(), termin.getTer_ende()) / 60.0;

        vorlesungVonNutzer.setVvn_stnd(vorlesungVonNutzer.getVvn_stnd() + oldDelta);
        if ((vorlesungVonNutzer.getVvn_stnd() - newDelta) < 0) {
            throw new TerminException("Bitte beachten Sie die Anzahl der verfügbaren Stunden.");
        }
        vorlesungVonNutzer.setVvn_stnd(vorlesungVonNutzer.getVvn_stnd() - newDelta);

        try {
            terminRepository.save(termin);
            vorlesungVonNutzerRepository.save(vorlesungVonNutzer);
        } catch (Exception e) {
            throw new TerminException("Bei der Eingabe Ihrer Daten gab es einen Fehler. Sie konnten nicht gespeichert werden.");
        }
    }

    /**
     * Löscht den Termin mit der angegebenen ID aus der Datenbank.
     * @param id
     */
    public void deleteTermin(Integer id) {
        terminRepository.deleteById(id);
    }

    /**
     * Stellt die Events für den Kalender zusammen.
     * Dafür werden alle Termine aus der Datenbank überprüft.
     * Wenn der Termin zu dem derzeit eingeloggten User gehört,
     * wird er mit "setBackgroundColor('red')" rot im Kalender angezeigt.
     * @param termine
     * @param kurs
     * @return
     */
    private ArrayList<Event> getEvents(ArrayList<Termin> termine, String kurs) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();
        Integer userId = userService.getUserId(currentPrincipalEmail);
        Integer kursId = kursRepository.findKursIdByName(kurs);
        ArrayList<Integer> vorlesungVonNutzerIds = vorlesungVonNutzerRepository.findIdsByNutzerId(userId, kursId);

        ArrayList<Event> events = new ArrayList<>();

        for (Termin termin : termine) {
            Event event = new Event();
            Optional<Vorlesung_Von_Nutzer> vorlesungVonNutzer = vorlesungVonNutzerRepository.
                    findById(termin.getTer_vvn_id());
            Optional<Vorlesung> vorlesung = vorlesungRepository.findById(vorlesungVonNutzer.
                    get().getVvn_vor_id());

            event.setTitle(vorlesung.get().getVor_kuerzel());

            event.setStart(termin.getTer_datum() + "T" + termin.getTer_start());
            event.setEnd(termin.getTer_datum() + "T" + termin.getTer_ende());

            event.setTer_id(termin.getTer_id());

            for (Integer i : vorlesungVonNutzerIds) {
                if (i == termin.getTer_vvn_id()) {
                    event.setBackgroundColor("red");
                }
            }
            events.add(event);
        }

        return events;
    }

    /**
     * Überprüft, ob der Zeitraum für den Termin frei ist.
     * @param date
     * @param start
     * @param end
     * @param kursId
     * @param terminId
     * @return
     */
    private Boolean terminExists(LocalDate date, LocalTime start, LocalTime end, Integer kursId, Integer terminId) {
        Boolean b = false;
        ArrayList<Termin> termine = terminRepository.findAllByDate(date, kursId);
        for (Termin termin : termine) {
            LocalTime dbStart = termin.getTer_start();
            LocalTime dbEnd = termin.getTer_ende();
            if (termin.getTer_id() != terminId && ((dbStart.isAfter(start) && dbStart.isBefore(end))
                    || (dbEnd.isAfter(start) && dbEnd.isBefore(end))
                    || (start.isAfter(dbStart) && start.isBefore(dbEnd))
                    || (end.isAfter(dbStart) && end.isBefore(dbEnd))
                    || start.compareTo(dbStart) == 0
                    || start.compareTo(dbEnd) == 0
                    || end.compareTo(dbStart) == 0
                    || end.compareTo(dbEnd) == 0)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * Liefert den Termin mit der angegebenen ID.
     * @param terminId
     * @return
     */
    public Optional<Termin> getTerminById(Integer terminId) {
        return terminRepository.findById(terminId);
    }
}
