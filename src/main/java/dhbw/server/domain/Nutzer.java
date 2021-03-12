package dhbw.server.domain;

import org.springframework.stereotype.Component;

@Component
public class Nutzer {

    public String vorname;
    public String nachname;
    public String email;
    public String anrede;
    public String titel;
    public Boolean istAdmin;
    public String passwort;

    public Nutzer() {}

    public Nutzer(String vorname, String nachname, String email,
                  String anrede, String titel, Boolean istAdmin, String passwort) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.anrede = anrede;
        this.titel = titel;
        this.istAdmin = istAdmin;
        this.passwort = passwort;
    }

}
