package dhbw.server.services;

public class Vorlesung_Namen {

    private Integer vvn_id;
    private String vor_name;

    public Vorlesung_Namen() {}

    public Vorlesung_Namen(Integer vvn_id, String vor_name) {
        this.vvn_id = vvn_id;
        this.vor_name = vor_name;
    }

    public Integer getVvn_id() {
        return vvn_id;
    }

    public void setVvn_id(Integer vvn_id) {
        this.vvn_id = vvn_id;
    }

    public String getVor_name() {
        return vor_name;
    }

    public void setVor_name(String vor_name) {
        this.vor_name = vor_name;
    }
}
