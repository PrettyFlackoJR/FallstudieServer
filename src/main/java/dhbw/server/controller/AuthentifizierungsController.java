package dhbw.server.controller;

import dhbw.server.entities.Termin;
import dhbw.server.exceptions.UserAlreadyExistsException;
import dhbw.server.helper.KVS;
import dhbw.server.services.KursService;
import dhbw.server.services.UserService;
import dhbw.server.entities.Nutzer;
import dhbw.server.services.VorlesungsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class AuthentifizierungsController {

    @Autowired
    private UserService userService;
    @Autowired
    private KursService kursService;
    @Autowired
    private VorlesungsService vorlesungsService;

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
        model.addAttribute("kurse", kursService.getAlleKurseMitNamen());
        model.addAttribute("vor_namen", vorlesungsService.getVorNamen());
        return "signup_form";
    }
    @GetMapping("/register_student")
    public String showRegistrationFormStudent(Model model) {
        model.addAttribute("user", new Nutzer());
        return "signup_form_student";
    }

    @Transactional
    @PostMapping("/process_register")
    public String processRegister(Nutzer user,
                                  Model model) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getNut_passwort());
        user.setNut_passwort(encodedPassword);

        try {
            userService.registerNewUserAccount(user);
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("user", new Nutzer());
            model.addAttribute("errorMessage", "Error: " + e.getMessage());
            return "signup_form";
        }

        return "register_success";
    }

    @GetMapping("/accessdenied")
    public String accessDenied() {
        return "access_denied";
    }

    @GetMapping("/process_role")
    @ResponseBody
    public ArrayList<Integer> checkRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalEmail = authentication.getName();
        Integer id = userService.getUserId(currentPrincipalEmail);

        return userService.getRoles(id);
    }

}
