package dhbw.server.controller;

import dhbw.server.entities.Nutzer;
import dhbw.server.repositories.NutzerRepository;
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

    @Autowired
    private NutzerRepository nutzerRepository;

    @GetMapping
    public String viewVorlesungsplaner() {
        return "homepage";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        // Beispiel um email des eingeloggten users zu bekommen
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();

        List<Nutzer> nutzerList = nutzerRepository.findAll();
        model.addAttribute("listUsers", nutzerList);
        return "users";
    }

}
