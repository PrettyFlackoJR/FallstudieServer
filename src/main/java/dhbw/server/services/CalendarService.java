package dhbw.server.services;

import dhbw.server.entities.Termin;
import dhbw.server.entities.Vorlesung;
import dhbw.server.entities.Vorlesung_Von_Nutzer;
import dhbw.server.jsonForCalendar.Calendar;
import dhbw.server.jsonForCalendar.Event;
import dhbw.server.jsonForCalendar.HeaderToolbar;
import dhbw.server.repositories.TerminRepository;
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
public class CalendarService {

    @Autowired
    private TerminRepository terminRepository;
    @Autowired
    private Vorlesung_Von_NutzerRepository vorlesungVonNutzerRepository;
    @Autowired
    private VorlesungRepository vorlesungRepository;
    @Autowired
    private UserService userService;

    public Calendar showCalendar() {
        HeaderToolbar headerToolbar = new HeaderToolbar("prev,next today",
                "title", "dayGridMonth,timeGridWeek,timeGridDay");
        ArrayList<Event> events = new ArrayList<>();

        // Nach Kurs und Nutzer filtern!
        // ...
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();

        int id = userService.getUserId(currentPrincipalEmail);
        System.out.println("ID: " + id);
        //FALSCH
        List<Termin> termine = terminRepository.findAllByUserId(1);

        for (Termin termin : termine) {
            Event event = new Event();
            Optional<Vorlesung_Von_Nutzer> vorlesungVonNutzer = vorlesungVonNutzerRepository.
                    findById(termin.getTer_vor_von_nut_id());
            Optional<Vorlesung> vorlesung = vorlesungRepository.findById(vorlesungVonNutzer.
                    get().getVor_von_nut_vol_id());
            event.setTitle(vorlesung.get().getVor_kuerzel());
            event.setStart(String.valueOf(termin.getTer_datum()));
            event.setEnd(null);

            events.add(event);
        }

        Calendar calendar = new Calendar("dayGridMonth", "2021-03-07",
                headerToolbar, events);

        return calendar;
    }

}
