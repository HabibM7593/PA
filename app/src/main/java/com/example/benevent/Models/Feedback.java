package com.example.benevent.Models;

public class Feedback {
    int idu;
    String title;
    String content;
    String date;
    String status;
    int idty;
    String plateform;

    public Feedback(int idu, String title, String content, String date, String status, int idty, String plateform) {
        this.idu = idu;
        this.title = title;
        this.content = content;
        this.date = date;
        this.status = status;
        this.idty = idty;
        this.plateform = plateform;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    public String getPlateform() {
        return plateform;
    }

    public void setPlateform(String plateform) {
        this.plateform = plateform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIdty() {
        return idty;
    }

    public void setIdty(int idty) {
        this.idty = idty;
    }
}
