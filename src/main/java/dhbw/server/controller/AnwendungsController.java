package dhbw.server.controller;

import dhbw.server.entities.Termin;
import dhbw.server.helper.Termin_VorlesungName;
import dhbw.server.services.CalendarService;
import dhbw.server.services.KursService;
import dhbw.server.services.SchedulerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vorlesungsplaner")
public class AnwendungsController {

    @Autowired
    private KursService kursService;
    @Autowired
    private SchedulerServiceImpl schedulerService;
    @Autowired
    private CalendarService calendarService;

    @GetMapping
    public String viewVorlesungsplaner(Model model) {
        model.addAttribute("kvn_namen", kursService.getKursNamen());
        model.addAttribute("termine", calendarService.getAllTermine());
        return "homepage";
    }

    @GetMapping("/process_endplanning")
    @ResponseBody
    public void endPlanning() throws NoSuchMethodException {
        schedulerService.scheduleJob(172800000);
    }

    @GetMapping("/admin")
    public String viewAdmin(Model model) {
        model.addAttribute("kurse", kursService.getAlleKurseMitNamen());
        return "admin";
    }

}
