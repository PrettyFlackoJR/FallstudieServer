package dhbw.server.services;

import dhbw.server.entities.Termin;
import dhbw.server.entities.Vorlesung;
import dhbw.server.entities.Vorlesung_Von_Nutzer;
import dhbw.server.exceptions.TerminException;
import dhbw.server.jsonForCalendar.Calendar;
import dhbw.server.jsonForCalendar.Event;
import dhbw.server.jsonForCalendar.HeaderToolbar;
import dhbw.server.repositories.KursRepository;
import dhbw.server.repositories.TerminRepository;
import dhbw.server.repositories.VorlesungRepository;
import dhbw.server.repositories.Vorlesung_Von_NutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

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

    public Calendar showCalendar(String kurs) {
        HeaderToolbar headerToolbar = new HeaderToolbar("prev,next today",
                "title", "dayGridMonth,timeGridWeek,timeGridDay");

        // Nutzer ID mit E-Mail holen
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();
        int nutzerId = userService.getUserId(currentPrincipalEmail);
        // VVN IDs mit Nutzer ID holen
        ArrayList<Integer> vvnIds = getVvnIds(nutzerId, kurs);
        // Alle Termine ermitteln
        ArrayList<Termin> termine = new ArrayList<>();
        for (Integer vvnId : vvnIds) {
            termine.addAll(terminRepository.findAllByVvnId(vvnId));
        }
        // Events für den Kalender erstellen
        ArrayList<Event> events = getEvents(termine);

        Calendar calendar = new Calendar("dayGridMonth", "2021-03-07",
                headerToolbar, events);

        return calendar;
    }

    public void addTermin(Termin termin) throws TerminException {
        if (terminExists(termin.getTer_datum(), termin.getTer_start(), termin.getTer_ende())) {
            throw new TerminException("Dieser Zeitraum ist bereits belegt.");
        } else {
            try {
                terminRepository.save(termin);
            } catch (Exception e) {
                throw new TerminException("Bei der Eingabe ihrer Daten gab es einen Fehler und sie konnten nicht gespeichert werden.");
            }
        }
    }

    public void modifyTermin(Event event) {
        Optional<Termin> termin = terminRepository.findById(event.getTer_id());
        String str = event.getStart();
        String str2 = event.getEnd();
        LocalDate date = LocalDate.parse(str.split("T")[0]);
        LocalTime start = LocalTime.parse(str.split("T")[1]);
        LocalTime end = LocalTime.parse(str2.split("T")[1]);

        termin.get().setTer_datum(date);
        termin.get().setTer_start(start);
        termin.get().setTer_ende(end);

        terminRepository.save(termin.get());
    }

    public void deleteTermin(Integer id) {
        terminRepository.deleteById(id);
    }

    private ArrayList<Event> getEvents(ArrayList<Termin> termine) {
        ArrayList<Event> events = new ArrayList<>();
        String start = "";
        String end = "";

        for (Termin termin : termine) {
            Event event = new Event();
            Optional<Vorlesung_Von_Nutzer> vorlesungVonNutzer = vorlesungVonNutzerRepository.
                    findById(termin.getTer_vvn_id());
            Optional<Vorlesung> vorlesung = vorlesungRepository.findById(vorlesungVonNutzer.
                    get().getVvn_vor_id());

            event.setTitle(vorlesung.get().getVor_kuerzel());

            start = termin.getTer_datum() + "T" + termin.getTer_start();
            event.setStart(start);

            end = termin.getTer_datum() + "T" + termin.getTer_ende();
            event.setEnd(end);

            event.setTer_id(termin.getTer_id());

            events.add(event);
        }

        return events;
    }

    private ArrayList<Integer> getVvnIds(Integer nutzerId, String kurs) {
        Integer kursId = kursRepository.findByKursName(kurs);
        ArrayList<Integer> vvnIds = vorlesungVonNutzerRepository.findIdsByNutzerId(nutzerId, kursId);

        return vvnIds;
    }

    private Boolean terminExists(LocalDate date, LocalTime start, LocalTime end) {
        Boolean b = false;
        ArrayList<Termin> termine = terminRepository.findAllByDate(date);
        for (Termin termin : termine) {
            LocalTime dbStart = termin.getTer_start();
            LocalTime dbEnd = termin.getTer_ende();
            if ((dbStart.isAfter(start) && dbStart.isBefore(end))
                    || (dbEnd.isAfter(start) && dbEnd.isBefore(end))
                    || (start.isAfter(dbStart) && start.isBefore(dbEnd))
                    || (end.isAfter(dbStart) && end.isBefore(dbEnd))
                    || start.compareTo(dbStart) == 0
                    || start.compareTo(dbEnd) == 0
                    || end.compareTo(dbStart) == 0
                    || end.compareTo(dbEnd) == 0) {
                b = true;
            }
        }
        return b;
    }

}
