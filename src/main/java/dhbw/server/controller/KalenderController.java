package dhbw.server.controller;

import dhbw.server.entities.Termin;
import dhbw.server.exceptions.TerminException;
import dhbw.server.helper.Termin_VorlesungName;
import dhbw.server.helper.Vorlesung_Namen;
import dhbw.server.jsonForCalendar.Calendar;
import dhbw.server.jsonForCalendar.Event;
import dhbw.server.services.CalendarService;
import dhbw.server.services.VorlesungsService;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Optional;

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

    @GetMapping("/termin_modify")
    public String termin_modify(@RequestParam(name = "terminId") Integer terminId,
                                Model model) {
        Termin termin = calendarService.getTerminById(terminId).get();
        model.addAttribute("termin", termin);
        model.addAttribute("vorlesung", vorlesungsService.getVorName(termin.getTer_vvn_id()));
        model.addAttribute("kurs", vorlesungsService.getKursById(termin.getTer_kurs_id()).get());

        return "termin_modify_form";
    }

    @PostMapping("/process_addTermin")
    public String processAddTermin(@RequestParam(name = "kurs") String kurs,
                                   Termin termin,
                                   Model model) {
        try {
            calendarService.addTermin(termin);
        } catch (TerminException e) {
            System.out.println("test");
            model.addAttribute("termin", new Termin());
            model.addAttribute("vvn_namen", vorlesungsService.getVVNNamen(kurs));
            model.addAttribute("kurse", vorlesungsService.getKursByName(kurs));
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "termin_form";
        }


        return "redirect:/vorlesungsplaner";
    }

    @PostMapping("/process_modifyTermin")
    public String processModifyTermin(Termin termin) {
        calendarService.modifyTermin(termin);
        return "redirect:/vorlesungsplaner";
    }

    @DeleteMapping(value = "/process_deleteTermin/{id}")
    @ResponseBody
    public void processDeleteTermin(@PathVariable Integer id) {
        calendarService.deleteTermin(id);
    }

    @GetMapping("/getStunden")
    @ResponseBody
    public double getStunden(@RequestParam(required = false,name= "vorlesung") Integer vvnId,
                             Model model) {
        return vorlesungsService.getStundenVonVorlesung(vvnId);
    }
}

