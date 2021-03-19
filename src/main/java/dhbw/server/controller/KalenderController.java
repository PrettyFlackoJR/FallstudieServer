package dhbw.server.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import dhbw.server.entities.Termin;
import dhbw.server.json.Calendar;
import dhbw.server.json.Event;
import dhbw.server.json.HeaderToolbar;
import dhbw.server.repositories.TerminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class KalenderController {

    @Autowired
    TerminRepository terminRepository;

    @GetMapping("/kalender")
    public String kalender(Model model) {

        return "kalender";
    }

    @GetMapping(value = "/process_kalender", produces = {"application/json", "text/json"})
    @ResponseBody
    public Calendar processKalender() {
        HeaderToolbar headerToolbar = new HeaderToolbar("prev,next today",
                "title", "dayGridMonth,timeGridWeek,timeGridDay");

        ArrayList<Event> events = new ArrayList<>();

        // Nach Kurs und Nutzer filtern!

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

