package dhbw.server.controller;

import dhbw.server.entities.Nutzer;
import dhbw.server.entities.Termin;
import dhbw.server.entities.Vorlesung_Von_Nutzer;
import dhbw.server.repositories.Kurs_Von_NutzerRepository;
import dhbw.server.repositories.TerminRepository;
import dhbw.server.repositories.Vorlesung_Von_NutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class KalenderController {

    @Autowired
    private TerminRepository terminRepository;
    @Autowired
    private Vorlesung_Von_NutzerRepository vorlesungVonNutzerRepository;


    @GetMapping("/termin")
    public String showTerminAdding(Model model) {
        model.addAttribute("termin", new Termin());

        return "signup_form";
    }
    @GetMapping("/listVorlesungen")
    public String listVorlesungen(Model model) {

        return "homepage";
    }

}

