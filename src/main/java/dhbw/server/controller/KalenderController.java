package dhbw.server.controller;

import dhbw.server.jsonForCalendar.Calendar;
import dhbw.server.services.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/vorlesungsplaner/kalender")
public class KalenderController {

    @Autowired
    private CalendarService calendarService;

    @GetMapping
    public String kalender(Model model) {

        return "kalender";
    }
    @GetMapping(path = "/process_kalender", produces = {"application/json", "text/json"})
    @ResponseBody
    public Calendar processKalender(@RequestParam(required = false, name = "kurs") String kurs) {
        String kurs2 = "A";
        return calendarService.showCalendar(kurs2);
    }

}

