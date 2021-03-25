package dhbw.server.entities;

public class Kurs_Namen {

    private Integer kvn_id;
    private String kurs_name;

    public Integer getKvn_id() {
        return kvn_id;
    }

    public void setKvn_id(Integer kvn_id) {
        this.kvn_id = kvn_id;
    }

    public String getKurs_name() {
        return kurs_name;
    }

    public void setKurs_name(String kurs_name) {
        this.kurs_name = kurs_name;
    }

    public Kurs_Namen(Integer kvn_id, String kurs_name) {
        this.kvn_id = kvn_id;
        this.kurs_name = kurs_name;
    }
}
