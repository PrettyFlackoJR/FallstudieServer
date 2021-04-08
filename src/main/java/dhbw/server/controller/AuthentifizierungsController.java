package dhbw.server.controller;

import dhbw.server.entities.Kurs;
import dhbw.server.entities.Vorlesung;
import dhbw.server.exceptions.UserAlreadyExistsException;
import dhbw.server.helper.Kurs_Vorlesung_Stunden;
import dhbw.server.helper.RegisterForm;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/register_success")
    public String registerSuccess() {
        return "register_success";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }

    @GetMapping("/vorlesungsplaner/kursNamen")
    @ResponseBody
    public List<Kurs> kursnamen() {
        return kursService.getAlleKurseMitNamen();
    }

    @GetMapping("/vorlesungsplaner/vorlesungsNamen")
    @ResponseBody
    public List<Vorlesung> vorlesungsNamen() {
        return vorlesungsService.getAllVorNamen();
    }

    @GetMapping("/vorlesungsplaner/admin/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Nutzer());
        model.addAttribute("kurse", kursService.getAlleKurseMitNamen());
        model.addAttribute("vor_namen", vorlesungsService.getAllVorNamen());
        model.addAttribute("kvs", new ArrayList<Kurs_Vorlesung_Stunden>());
        return "signup_form";
    }

    @GetMapping("/vorlesungsplaner/admin/register_student")
    public String showRegistrationFormStudent(Model model) {
        model.addAttribute("kurse", kursService.getAlleKurseMitNamen());
        model.addAttribute("user", new Nutzer());
        return "signup_form_student";
    }

    @PostMapping("/vorlesungsplaner/admin/process_registerdozent")
    @ResponseBody
    public String processDozentRegister(HttpServletResponse response,
                                        @RequestBody(required = false) RegisterForm registerForm) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(registerForm.getNut_passwort());
        registerForm.setNut_passwort(encodedPassword);

        try {
            userService.registerNewDozentAccount(registerForm);
        } catch (UserAlreadyExistsException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Error: " + e.getMessage();
        }

        return "";
    }

    @PostMapping("/vorlesungsplaner/admin/process_registerstudent")
    @ResponseBody
    public String processStudentRegister(@RequestParam(name = "kursId") Integer kursId,
                                         @RequestBody(required = false) Nutzer nutzer,
                                         HttpServletResponse response) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(nutzer.getNut_passwort());
        nutzer.setNut_passwort(encodedPassword);

        try {
            userService.registerNewStudentAccount(nutzer, kursId);
        } catch (UserAlreadyExistsException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Error: " + e.getMessage();
        }

        return "";
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
