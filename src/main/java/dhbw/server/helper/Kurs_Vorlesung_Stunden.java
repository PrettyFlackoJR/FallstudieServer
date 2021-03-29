package dhbw.server.helper;

public class Kurs_Vorlesung_Stunden {
    Integer kursId;
    Integer vorlesungId;
    double Stunden;

    public Kurs_Vorlesung_Stunden(Integer kursId, Integer vorlesungId, double stunden) {
        this.kursId = kursId;
        this.vorlesungId = vorlesungId;
        Stunden = stunden;
    }

    public Integer getKursId() {
        return kursId;
    }

    public void setKursId(Integer kursId) {
        this.kursId = kursId;
    }

    public Integer getVorlesungId() {
        return vorlesungId;
    }

    public void setVorlesungId(Integer vorlesungId) {
        this.vorlesungId = vorlesungId;
    }

    public double getStunden() {
        return Stunden;
    }

    public void setStunden(double stunden) {
        Stunden = stunden;
    }
}
