package dhbw.server.controller;

import dhbw.server.entities.Termin;
import dhbw.server.exceptions.TerminException;
import dhbw.server.helper.Termin_VorlesungName;
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

    @GetMapping(path = "/process_kalendertable", produces = {"application/json", "text/json"})
    @ResponseBody
    public ArrayList<Termin_VorlesungName> processKalenderTable(@RequestParam(name = "kurs") String kurs) {
        return calendarService.getTermineWithVorlesungsName(kurs);
    }

    @GetMapping("/termin_add")
    public String termin_add(@RequestParam(name = "kurs") String kurs, Model model) {
        model.addAttribute("termin", new Termin());
        model.addAttribute("vvn_namen", vorlesungsService.getVVNNamen(kurs));
        model.addAttribute("kurse", vorlesungsService.getKursByName(kurs));
        return "termin_form";
    }

    @PostMapping("/process_addTermin")
    public String processAddTermin(@RequestParam(name = "kurs") String kurs,
                                   Termin termin,
                                   Model model) {
        try {
            calendarService.addTermin(termin);
        } catch (TerminException e) {
            model.addAttribute("termin", new Termin());
            model.addAttribute("vvn_namen", vorlesungsService.getVVNNamen(kurs));
            model.addAttribute("kurse", vorlesungsService.getKursByName(kurs));
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "termin_form";
        }


        return "redirect:/vorlesungsplaner";
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

