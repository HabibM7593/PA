package com.example.benevent.Models;

import java.util.Date;

public class Post {
    int idpo;
    String message;
    Date date;
    int idu;
    int idas;
    int idev;

    public Post(int idpo, String message, Date date, int idas, int idev) {
        this.idpo = idpo;
        this.message = message;
        this.date = date;
        this.idas = idas;
        this.idev = idev;
    }

    public Post(int idpo, int idu, String message, Date date, int idev) {
        this.idpo = idpo;
        this.message = message;
        this.date = date;
        this.idu = idu;
        this.idev = idev;
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
