package dhbw.server.controller;

import dhbw.server.exceptions.TimeframeException;
import dhbw.server.helper.KursZeitraum;
import dhbw.server.services.KursService;
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

    @GetMapping
    public String viewAdmin(Model model) {
        model.addAttribute("kurse", kursService.getAllKurseWithNames());
        return "admin";
    }

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
