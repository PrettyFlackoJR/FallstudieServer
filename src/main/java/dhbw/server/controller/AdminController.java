package dhbw.server.controller;

import dhbw.server.helper.KursZeitraum;
import dhbw.server.services.KursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vorlesungsplaner/admin")
public class AdminController {

    @Autowired
    private KursService kursService;

    @GetMapping
    public String viewAdmin(Model model) {
        model.addAttribute("kurse", kursService.getAlleKurseMitNamen());
        return "admin";
    }

    @PostMapping("/process_setperiod")
    @ResponseBody
    public void setPeriod(@RequestBody KursZeitraum kursZeitraum) {
        kursService.setPeriod(kursZeitraum);
    }


}
