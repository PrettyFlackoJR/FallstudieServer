package dhbw.server.controller;

import dhbw.server.entities.Termin;
import dhbw.server.jsonForCalendar.Calendar;
import dhbw.server.services.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vorlesungsplaner")
public class KalenderController {

    @Autowired
    private CalendarService calendarService;

    @GetMapping(path = "/process_kalender", produces = {"application/json", "text/json"})
    @ResponseBody
    public Calendar processKalender(@RequestParam(required = false, name = "kurs") String kurs) {
        String kurs2 = "A";
        return calendarService.showCalendar(kurs2);
    }

    @GetMapping("/termin_add")
    public String termin_add() {
        return "termin_add";
    }

    @PostMapping("/process_termin")
    public void processTermin(@RequestBody Termin termin) {
        calendarService.addTermin(termin);
    }

}

