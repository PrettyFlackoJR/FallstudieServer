package dhbw.server.controller;

import dhbw.server.entities.Termin;
import dhbw.server.exceptions.TerminException;
import dhbw.server.helper.Event;
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

    /**
     * Liefert die Events für den Kalender im Frontend.
     * @param kurs
     * @return
     */
    @GetMapping(path = "/process_calendar", produces = {"application/json", "text/json"})
    @ResponseBody
    public ArrayList<Event> processCalendar(@RequestParam(name = "kurs") String kurs) {
        return calendarService.showCalendar(kurs);
    }

    /**
     * Gibt Formular zurück, um  einen Termin erstellen zu können.
     * @param kurs
     * @param model
     * @return template 'termin_form'
     */
    @GetMapping("/termin_add")
    public String addTermin(@RequestParam(name = "kurs") String kurs, Model model) {
        model.addAttribute("termin", new Termin());
        model.addAttribute("vvn_namen", vorlesungsService.getVVNNamen(kurs));
        model.addAttribute("kurse", vorlesungsService.getKursByName(kurs));

        return "termin_form";
    }

    /**
     * Gibt Formular zurück, um  einen Termin modifizieren zu können.
     * @param terminId
     * @param model
     * @return template 'termin_modify_form'
     */
    @GetMapping("/termin_modify")
    public String modifyTermin(@RequestParam(name = "terminId") Integer terminId,
                               Model model) {
        Termin termin = calendarService.getTerminById(terminId).get();
        model.addAttribute("termin", termin);
        model.addAttribute("vorlesung", vorlesungsService.getVorName(termin.getTer_vvn_id()));
        model.addAttribute("kurs", vorlesungsService.getKursById(termin.getTer_kurs_id()).get());

        return "termin_modify_form";
    }

    /**
     * Startet Prozess, um einen Termin hinzuzufügen.
     * Wenn ein Fehler auftritt, wird dieser im Formular ausgegeben.
     * Bei Erfolg kommt es zur Weiterleitung auf die Homepage.
     * @param kurs
     * @param termin
     * @param model
     * @return
     */
    @PostMapping("/process_addtermin")
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

    /**
     * Startet Prozess, um einen Termin zu modifizieren.
     * Wenn ein Fehler auftritt, wird dieser im Formular ausgegeben.
     * Bei Erfolg kommt es zur Weiterleitung auf die Homepage.
     * @param termin
     * @param model
     * @return
     */
    @PostMapping("/process_modifytermin")
    public String processModifyTermin(Termin termin, Model model) {
        try {
            calendarService.modifyTermin(termin);
        } catch (TerminException e) {
            model.addAttribute("termin", termin);
            model.addAttribute("vorlesung", vorlesungsService.getVorName(termin.getTer_vvn_id()));
            model.addAttribute("kurs", vorlesungsService.getKursById(termin.getTer_kurs_id()).get());
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "termin_modify_form";
        }

        return "redirect:/vorlesungsplaner";
    }

    /**
     * Löscht den Termin mit der angegebenen ID.
     * @param id
     */
    @DeleteMapping(value = "/process_deletetermin/{id}")
    @ResponseBody
    public void processDeleteTermin(@PathVariable Integer id) {
        calendarService.deleteTermin(id);
    }

    /**
     * Liefert die verbleibenden Stunden der Vorlesung eines Dozenten.
     * @param vvnId
     * @return
     */
    @GetMapping("/gethours")
    @ResponseBody
    public double getHours(@RequestParam(required = false, name = "vorlesung") Integer vvnId) {
        return vorlesungsService.getHoursOfVorlesung(vvnId);
    }
}

