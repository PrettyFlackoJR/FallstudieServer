package dhbw.server.controller;

import dhbw.server.entities.Nutzer;
import dhbw.server.repositories.NutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AnwendungsController {

    @Autowired
    private NutzerRepository nutzerRepository;


    @GetMapping("/index")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Nutzer());

        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(Nutzer user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getNut_passwort());
        user.setNut_passwort(encodedPassword);

        nutzerRepository.save(user);

        return "register_success";
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

    @GetMapping("/accessdenied")
    public String accessDenied() {
        return "access_denied";
    }

}
