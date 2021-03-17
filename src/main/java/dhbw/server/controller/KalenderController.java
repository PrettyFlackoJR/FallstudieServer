package dhbw.server.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class KalenderController {

    @GetMapping("/kalender")
    public String kalender() {
        return "kalender";
    }

}

