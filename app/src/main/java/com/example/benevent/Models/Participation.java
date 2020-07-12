package com.example.benevent.Models;

public class Participation {
    int idev;
    int idu;
    int participate;
    int status;

    public Participation(int idev, int idu, int participate, int status) {
        this.idev = idev;
        this.idu = idu;
        this.participate = participate;
        this.status = status;
    }

    public Participation(int idev, int idu) {
        this.idev = idev;
        this.idu = idu;
    }

    public int getIdev() {
        return idev;
    }

    public void setIdev(int idev) {
        this.idev = idev;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
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
