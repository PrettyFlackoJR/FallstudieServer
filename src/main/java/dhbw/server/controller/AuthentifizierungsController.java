package dhbw.server.controller;

import dhbw.server.UserService;
import dhbw.server.entities.Nutzer;
import dhbw.server.entities.Nutzer_Role;
import dhbw.server.repositories.Kurs_Von_NutzerRepository;
import dhbw.server.repositories.NutzerRepository;
import dhbw.server.repositories.Nutzer_RolesRepository;
import dhbw.server.repositories.Vorlesung_Von_NutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthentifizierungsController {

    @Autowired
    private NutzerRepository nutzerRepository;
    @Autowired
    private Nutzer_RolesRepository nutzerRolesRepository;
    @Autowired
    private Kurs_Von_NutzerRepository kursVonNutzerRepository;
    @Autowired
    private Vorlesung_Von_NutzerRepository vorlesungVonNutzerRepository;
    @Autowired
    private UserService userService;

    @GetMapping
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Nutzer());

        return "signup_form";
    }

    @Transactional
    @PostMapping("/process_register")
    public String processRegister(@RequestParam(required = false, value = "admin") Boolean b,
                                  Nutzer user,
                                  Model model) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getNut_passwort());
        user.setNut_passwort(encodedPassword);

        boolean admin = false;
        if (b != null) {
            admin = true;
        }

        try {
            Nutzer nutzer = userService.registerNewUserAccount(user);
        } catch (Exception e) {
            model.addAttribute("user", new Nutzer());
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "signup_form";
        }

        nutzerRepository.save(user);

        if (admin) {
            Nutzer_Role nutzer_role = new Nutzer_Role(nutzerRepository.findIdByEmail(user.getNut_email()), 4);
            nutzerRolesRepository.save(nutzer_role);
        } else {
            Nutzer_Role nutzer_role = new Nutzer_Role(nutzerRepository.findIdByEmail(user.getNut_email()), 1);
            nutzerRolesRepository.save(nutzer_role);
        }

        return "register_success";
    }

    @GetMapping("/accessdenied")
    public String accessDenied() {
        return "access_denied";
    }

}
