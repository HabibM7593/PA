package com.example.benevent.Models;

public class Participation {
    int idev;
    int idu;
    boolean participate;
    boolean status;

    public Participation(int idev, int idu, boolean participate, boolean status) {
        this.idev = idev;
        this.idu = idu;
        this.participate = participate;
        this.status = status;
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

    public boolean isParticipate() {
        return participate;
    }

    public void setParticipate(boolean participate) {
        this.participate = participate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
