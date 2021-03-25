package dhbw.server.controller;

import dhbw.server.entities.Termin;
import dhbw.server.entities.Vorlesung;
import dhbw.server.entities.Vorlesung_Namen;
import dhbw.server.entities.Vorlesung_Von_Nutzer;
import dhbw.server.jsonForCalendar.Calendar;
import dhbw.server.jsonForCalendar.Event;
import dhbw.server.repositories.KursRepository;
import dhbw.server.services.CalendarService;
import dhbw.server.services.VorlesungsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/vorlesungsplaner")
public class KalenderController {

    @Autowired
    private CalendarService calendarService;
    @Autowired
    private VorlesungsService vorlesungsService;
    @Autowired
    private KursRepository kursRepository;

    @GetMapping(path = "/process_kalender", produces = {"application/json", "text/json"})
    @ResponseBody
    public Calendar processKalender(@RequestParam(name = "kurs") String kurs) {
        return calendarService.showCalendar(kurs);
    }

    @GetMapping("/termin_add")
    public String termin_add(@RequestParam(name = "kurs") String kurs, Model model) {
        model.addAttribute("termin", new Termin());
        model.addAttribute("vvn_namen", vorlesungsService.getVVNNamen(kurs));
        model.addAttribute("kurse", vorlesungsService.getKursByName(kurs));
        return "termin_add";
    }

    @PostMapping("/process_addTermin")
    public String processAddTermin(Termin termin) {
        System.out.println(termin);
        calendarService.addTermin(termin);

        return "termin_success";
    }

    @PutMapping("/process_modifyTermin")
    public void processModifyTermin(@RequestBody Event event) {
        calendarService.modifyTermin(event);
    }

    @DeleteMapping("/process_deleteTermin")
    public void processDeleteTermin(@RequestParam(name = "id") Integer id) {
        calendarService.deleteTermin(id);
    }

    @GetMapping("/getStunden")
    @ResponseBody
    public double getStunden(@RequestParam(required = false,name= "vvnId") Integer vvnId, Model model) {
        return 2;
    }

}

