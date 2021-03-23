package dhbw.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vorlesungsplaner")
public class TerminController {
    @GetMapping("/termin_add")
    public String termin_add() {
        return "termin_add";
    }
}
