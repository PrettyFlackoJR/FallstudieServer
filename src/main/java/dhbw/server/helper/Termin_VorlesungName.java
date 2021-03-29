package dhbw.server.helper;

import dhbw.server.entities.Termin;

public class Termin_VorlesungName {

    private Termin termin;
    private String name;

    public Termin_VorlesungName() {
    }

    public Termin_VorlesungName(Termin termin, String name) {
        this.termin = termin;
        this.name = name;
    }

    public Termin getTermin() {
        return termin;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
