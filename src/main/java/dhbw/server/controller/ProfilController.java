package dhbw.server.controller;

import dhbw.server.helper.KursZeitraum;
import dhbw.server.services.KursService;
import dhbw.server.services.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vorlesungsplaner/profile")
public class ProfilController {

    @Autowired
    private ProfilService profilService;

    @GetMapping
    public String viewProfile() {
        return "profile";
    }

}
