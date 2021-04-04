package dhbw.server.helper;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;

public class RegisterForm {

    private String nut_vorname;
    private String nut_nachname;
    private String nut_email;
    private String nut_anrede;
    private String nut_titel;
    private String nut_passwort;

    private ArrayList<Kurs_Vorlesung_Stunden> kvs = new ArrayList<>();

    public String getNut_vorname() {
        return nut_vorname;
    }

    public void setNut_vorname(String nut_vorname) {
        this.nut_vorname = nut_vorname;
    }

    public String getNut_nachname() {
        return nut_nachname;
    }

    public void setNut_nachname(String nut_nachname) {
        this.nut_nachname = nut_nachname;
    }

    public String getNut_email() {
        return nut_email;
    }

    public void setNut_email(String nut_email) {
        this.nut_email = nut_email;
    }

    public String getNut_anrede() {
        return nut_anrede;
    }

    public void setNut_anrede(String nut_anrede) {
        this.nut_anrede = nut_anrede;
    }

    public String getNut_titel() {
        return nut_titel;
    }

    public void setNut_titel(String nut_titel) {
        this.nut_titel = nut_titel;
    }

    public String getNut_passwort() {
        return nut_passwort;
    }

    public void setNut_passwort(String nut_passwort) {
        this.nut_passwort = nut_passwort;
    }

    public ArrayList<Kurs_Vorlesung_Stunden> getKvs() {
        return kvs;
    }

    public void setKvs(ArrayList<Kurs_Vorlesung_Stunden> kvs) {
        this.kvs = kvs;
    }
}
