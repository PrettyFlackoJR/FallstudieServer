package dhbw.server.controller;

import dhbw.server.entities.Nutzer;
import dhbw.server.entities.Vorlesung_Von_Nutzer;
import dhbw.server.repositories.NutzerRepository;
import dhbw.server.repositories.VorlesungRepository;
import dhbw.server.repositories.Vorlesung_Von_NutzerRepository;
import dhbw.server.services.VorlesungsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/vorlesungsplaner")
public class AnwendungsController {


    @GetMapping
    public String viewVorlesungsplaner(Model model) {
        return "homepage";
    }




}
