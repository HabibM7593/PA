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

    public Participation(int idevent, int iduser, int participate, int status, String startdate, String enddate) {
        this.idevent = idevent;
        this.iduser = iduser;
        this.participate = participate;
        this.status = status;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    public String getStartdate() {
        return startdate;
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

    public void setIdev(int idevent) {
        this.idevent = idevent;
    }

    public int getIdu() {
        return iduser;
    }

    public void setIdu(int iduser) {
        this.iduser = iduser;
    }

    public int isParticipate() {
        return participate;
    }

    public void setParticipate(int participate) {
        this.participate = participate;
    }

    public int isStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
