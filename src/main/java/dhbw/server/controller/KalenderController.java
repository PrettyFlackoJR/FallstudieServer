package dhbw.server.controller;

import dhbw.server.entities.Termin;
import dhbw.server.entities.Vorlesung;
import dhbw.server.entities.Vorlesung_Von_Nutzer;
import dhbw.server.jsonForCalendar.Calendar;
import dhbw.server.services.CalendarService;
import dhbw.server.entities.Vorlesung_Namen;
import dhbw.server.services.VorlesungsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vorlesungsplaner")
public class KalenderController {

    @Autowired
    private CalendarService calendarService;
    @Autowired
    private VorlesungsService vorlesungsService;

    @GetMapping(path = "/process_kalender", produces = {"application/json", "text/json"})
    @ResponseBody
    public Calendar processKalender(@RequestParam(name = "kurs") String kurs) {
        return calendarService.showCalendar(kurs);
    }

    @GetMapping("/termin_add")
    public String termin_add(@RequestParam(name = "kurs") String kurs, Model model) {

        model.addAttribute("termin",new Termin());
        model.addAttribute("vvn_namen", vorlesungsService.getVVNNamen(kurs));
        return "termin_add";
    }

    @PostMapping("/process_termin")
    public void processTermin(@RequestBody Termin termin) {
        calendarService.addTermin(termin);
    }

}

