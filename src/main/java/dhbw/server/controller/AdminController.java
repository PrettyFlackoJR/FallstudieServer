package dhbw.server.controller;

import dhbw.server.exceptions.TimeframeException;
import dhbw.server.helper.KursZeitraum;
import dhbw.server.services.KursService;
import dhbw.server.services.SchedulerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/vorlesungsplaner/admin")
public class AdminController {

    @Autowired
    private KursService kursService;
    @Autowired
    private SchedulerServiceImpl schedulerService;

    /**
     * Route für Admin-Template
     * @param model
     * @return template 'admin'
     */
    @GetMapping
    public String viewAdmin(Model model) {
        model.addAttribute("kurse", kursService.getAllKurseWithNames());
        return "admin";
    }

    /**
     * Prüft, ob Planung bereits läuft.
     * @param response
     */
    @GetMapping("/process_checkinitial")
    @ResponseBody
    public void isInitial(HttpServletResponse response) {
        if (schedulerService.isInitial()) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    /**
     * Setzt den Zeitraum für einen Kurs.
     * @param kursZeitraum
     * @param response
     * @return
     */
    @PostMapping("/process_setperiod")
    @ResponseBody
    public String setPeriod(@RequestBody KursZeitraum kursZeitraum, HttpServletResponse response) {
        try {
            kursService.setPeriod(kursZeitraum);
        } catch (TimeframeException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return e.getMessage();
        }
        return "";
    }
}
