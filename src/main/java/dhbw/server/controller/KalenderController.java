package dhbw.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KalenderController {

    @GetMapping("/kalender")
    public String kalender() {
        return "kalender";
    }

}

