package com.example.benevent.Models;

import java.util.Date;

public class Post {
    int idpo;
    String message;
    Date date;
    int idu;
    int idas;
    int idev;
    String eventname;
    String assoname;

    public Post(int idpo, String message, Date date, int idas, int idev, String eventname, String assoname) {
        this.idpo = idpo;
        this.message = message;
        this.date = date;
        this.idas = idas;
        this.idev = idev;
        this.eventname = eventname;
        this.assoname = assoname;
    }

    public Post(int idpo, int idu, String message, Date date, int idev, String eventname, String assoname) {
        this.idpo = idpo;
        this.message = message;
        this.date = date;
        this.idu = idu;
        this.idev = idev;
        this.eventname = eventname;
        this.assoname = assoname;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getAssoname() {
        return assoname;
    }

    public void setAssoname(String assoname) {
        this.assoname = assoname;
    }

    public int getIdpo() {
        return idpo;
    }

    public void setIdpo(int idpo) {
        this.idpo = idpo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    public int getIdas() {
        return idas;
    }

    public void setIdas(int idas) {
        this.idas = idas;
    }

    public int getIdev() {
        return idev;
    }

    public void setIdev(int idev) {
        this.idev = idev;
    }
}
