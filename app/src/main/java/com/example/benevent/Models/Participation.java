package com.example.benevent.Models;

public class Participation {
    int idevent;
    int iduser;
    int participate;
    int status;
    String startdate;
    String enddate;

    public Participation(int idevent, int iduser, int participate, int status) {
        this.idevent = idevent;
        this.iduser = iduser;
        this.participate = participate;
        this.status = status;
    }

    public Participation(int idevent, int iduser) {
        this.idevent = idevent;
        this.iduser = iduser;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public int getIdev() {
        return idevent;
    }

    public int getIdu() {
        return iduser;
    }

    public int isStatus() {
        return status;
    }
}
