package dhbw.server.controller;

import dhbw.server.entities.Termin;
import dhbw.server.entities.Vorlesung;
import dhbw.server.entities.Vorlesung_Namen;
import dhbw.server.entities.Vorlesung_Von_Nutzer;
import dhbw.server.jsonForCalendar.Calendar;
import dhbw.server.jsonForCalendar.Event;
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

    @GetMapping(path = "/process_kalender", produces = {"application/json", "text/json"})
    @ResponseBody
    public Calendar processKalender(@RequestParam(name = "kurs") String kurs) {
        return calendarService.showCalendar(kurs);
    }

    @GetMapping("/termin_add")
    public String termin_add(@RequestParam(name = "kurs") String kurs, Model model) {
        ArrayList<Vorlesung_Von_Nutzer> vvns = vorlesungsService.getVvns(kurs);
        ArrayList<Vorlesung> vorlesungen = new ArrayList<>();
        for (Vorlesung_Von_Nutzer vvn : vvns) {
            Integer id = vvn.getVvn_vor_id();
            vorlesungen = vorlesungsService.getVorlesungen(id);
        }
        System.out.println("Vorlesung" + vorlesungen);
        ArrayList<Vorlesung_Namen> vvn_namen = new ArrayList<>();
        for (Vorlesung_Von_Nutzer vvn : vvns) {
            for (Vorlesung vorlesung : vorlesungen) {
                System.out.println(vvn.getVvn_vor_id() + " " + vorlesung.getVor_id());
                if (vvn.getVvn_vor_id() == vorlesung.getVor_id()) {
                    System.out.println("if");
                    Vorlesung_Namen abc = new Vorlesung_Namen(vvn.getVvn_id(), vorlesung.getVor_name());
                    vvn_namen.add(abc);
                }
            }
        }
        model.addAttribute("termin", new Termin());
        model.addAttribute("vvn_namen", vvn_namen);
        return "termin_add";
    }

    @PostMapping("/process_addTermin")
    public void processAddTermin(@RequestBody Termin termin) {
        calendarService.addTermin(termin);
    }

    @PutMapping("/process_modifyTermin")
    public void processModifyTermin(@RequestBody Event event) {
        calendarService.modifyTermin(event);
    }

    @DeleteMapping("/process_deleteTermin")
    public void processDeleteTermin(@RequestParam(name = "id") Integer id) {
        calendarService.deleteTermin(id);
    }

}

