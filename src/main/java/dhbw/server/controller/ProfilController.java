package dhbw.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfilController {

    @GetMapping("/profile")
    public String showProfile(){
        return "profile";
    }

}
