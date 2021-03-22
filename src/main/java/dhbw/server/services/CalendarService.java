package dhbw.server.services;

import dhbw.server.entities.Termin;
import dhbw.server.jsonForCalendar.Calendar;
import dhbw.server.jsonForCalendar.Event;
import dhbw.server.jsonForCalendar.HeaderToolbar;
import dhbw.server.repositories.TerminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalendarService {

    @Autowired
    private TerminRepository terminRepository;

    public Calendar showCalendar() {
        HeaderToolbar headerToolbar = new HeaderToolbar("prev,next today",
                "title", "dayGridMonth,timeGridWeek,timeGridDay");
        ArrayList<Event> events = new ArrayList<>();

        // Nach Kurs und Nutzer filtern!
        // ...

        List<Termin> termine = terminRepository.findAll();
        for (Termin termin : termine) {
            Event event = new Event();
            event.setTitle("Test");
            event.setStart(String.valueOf(termin.getTer_datum()));
            event.setEnd(null);

            events.add(event);
        }

        Calendar calendar = new Calendar("dayGridMonth", "2021-03-07",
                headerToolbar, events);

        return calendar;
    }

}
