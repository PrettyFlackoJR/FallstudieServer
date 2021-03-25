package dhbw.server.controller;

import dhbw.server.services.KursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vorlesungsplaner")
public class AnwendungsController {

    @Autowired
    private KursService kursService;

    @GetMapping
    public String viewVorlesungsplaner(Model model) {
        model.addAttribute("kvn_namen", kursService.getKursNamen());
        return "homepage";
    }




}
