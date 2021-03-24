package dhbw.server.jsonForCalendar;

public class Event {

    private String title;
    private String start;
    private String end;
    private Integer ter_id;

    public Event() {}

    public Event(String title, String start, String end, Integer ter_id) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.ter_id = ter_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getTer_id() {
        return ter_id;
    }

    public void setTer_id(Integer ter_id) {
        this.ter_id = ter_id;
    }
}
