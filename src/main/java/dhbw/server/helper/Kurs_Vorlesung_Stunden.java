package dhbw.server.helper;

public class Kurs_Vorlesung_Stunden {
    private Integer kurs_id;
    private Integer vvn_vor_id;
    private Double stnd;

    public Kurs_Vorlesung_Stunden(Integer kurs_id, Integer vorlesungs_Id, Double stnd) {
        this.kurs_id = kurs_id;
        this.vvn_vor_id = vorlesungs_Id;
        this.stnd = stnd;
    }

    public Integer getVvn_vor_id() {
        return vvn_vor_id;
    }

    public void setVvn_vor_id(Integer vvn_vol_id) {
        this.vvn_vor_id = vvn_vol_id;
    }

    public Integer getVvn_kurs_id() {
        return kurs_id;
    }

    public void setVvn_kurs_id(Integer vvn_kurs_id) {
        this.kurs_id = vvn_kurs_id;
    }

    public Integer getKvn_kurs_id() {
        return kurs_id;
    }

    public void setKvn_kurs_id(Integer kvn_kurs_id) {
        this.kurs_id = kvn_kurs_id;
    }

    public Double getVvn_stnd() {
        return stnd;
    }

    public void setVvn_stnd(Double vvn_stnd) {
        this.stnd = vvn_stnd;
    }

}
