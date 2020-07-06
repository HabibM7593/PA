package com.example.benevent.Models;
public class Feedback {
    int idu;
    String title;
    String content;
    int idty;
    String plateform;

    public Feedback(String title, String content, int idty,String plateform, int idu) {
        this.title = title;
        this.content = content;
        this.idty = idty;
        this.plateform = plateform;
        this.idu = idu;
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
