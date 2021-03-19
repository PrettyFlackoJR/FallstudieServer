package dhbw.server.jsonForCalendar;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Calendar {

    private String initialView;
    private String initialDate;
    private HeaderToolbar headerToolbar;
    private ArrayList<Event> arrayList = new ArrayList<>();

    public Calendar() {}

    public Calendar(String initialView, String initialDate, HeaderToolbar headerToolbar, ArrayList<Event> arrayList) {
        this.initialView = initialView;
        this.initialDate = initialDate;
        this.headerToolbar = headerToolbar;
        this.arrayList = arrayList;
    }

    public String getInitialView() {
        return initialView;
    }

    public void setInitialView(String initialView) {
        this.initialView = initialView;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public HeaderToolbar getHeaderToolbar() {
        return headerToolbar;
    }

    public void setHeaderToolbar(HeaderToolbar headerToolbar) {
        this.headerToolbar = headerToolbar;
    }

    @JsonProperty("events")
    public ArrayList<Event> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Event> arrayList) {
        this.arrayList = arrayList;
    }
}
