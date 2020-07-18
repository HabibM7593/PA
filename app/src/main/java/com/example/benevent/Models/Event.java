package com.example.benevent.Models;

import java.util.Date;

public class Event {
    int idevent;
    String name;
    String description;
    Date startdate;
    Date enddate;
    String location;
    int maxBenevole;
    int idassociation;
    int fakeevent;

    public Event() {

    }

    public int getFakeevent() {
        return fakeevent;
    }

    public int getIdev() {
        return idevent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartdate() {
        return startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public String getLocation() {
        return location;
    }

    public int getMaxbenevole() { return maxBenevole; }

    public int getIdas() {
        return idassociation;
    }

}
